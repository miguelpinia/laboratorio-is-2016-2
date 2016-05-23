package mycontroller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mydao.DAOException;
import mydao.DAOFactory;
import mydao.UserSessionDAO;
import mymodel.UserSession;
import mymodel.User;

/**
 * The UserSession filter.
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/03/user-session-filter.html
 */
public class UserSessionFilter implements Filter {

    // Constants ----------------------------------------------------------------------------------

    private static final String MANAGED_BEAN_NAME = "userSession";
    private static final String COOKIE_NAME = "UserSessionFilter.cookieId";
    private static final int COOKIE_MAX_AGE = 31536000; // 60*60*24*365 seconds; 1 year.

    // Vars ---------------------------------------------------------------------------------------

    private UserSessionDAO userSessionDAO;

    // Actions ------------------------------------------------------------------------------------

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) {
        // Just do your DAO thing. You can make the databaseName a context init-param as well.
        // Also see the DAO tutorial.
        DAOFactory daoFactory = DAOFactory.getInstance("databaseName");
        userSessionDAO = daoFactory.getUserSessionDAO();
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        // Check PathInfo.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String pathInfo = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (pathInfo.startsWith("/inc")) {
            // This is not necessary, but it might be useful if you want to skip include
            // files for example. You can put include files (subviews, images, css, js)
            // in one folder, called "/inc". If those include files are loaded, then
            // continue the filter chain and abort this filter, because it is usually not
            // necessary to lookup for any UserSession then. Or, if the url-pattern in the
            // web.xml is specific enough, then this if-block can just be removed.
            chain.doFilter(request, response);
            return;
        }

        // Get UserSession from HttpSession.
        HttpSession httpSession = httpRequest.getSession();
        UserSession userSession = (UserSession) httpSession.getAttribute(MANAGED_BEAN_NAME);

        if (userSession == null) {

            // No UserSession found in HttpSession; lookup ID in cookie.
            String cookieId = getCookieValue(httpRequest, COOKIE_NAME);

            if (cookieId != null) {

                // ID found in cookie. Lookup UserSession by cookie ID in database.
                // Do your "SELECT * FROM UserSession WHERE CookieID" thing.
                try {
                    userSession = userSessionDAO.find(cookieId);
                    // This can be null. If this is null, then the session is deleted
                    // from DB meanwhile or the cookie is just fake (hackers!).
                } catch (DAOException e) {
                    // Do your exception handling thing.
                    setErrorMessage("Loading UserSession failed.", e);
                }
            }

            if (userSession == null) {

                // No ID found in cookie, or no UserSession found in DB.
                // Create new UserSession.
                // Do your "INSERT INTO UserSession VALUES values" thing.
                cookieId = UUID.randomUUID().toString();
                userSession = new UserSession(cookieId);
                try {
                    userSessionDAO.save(userSession);
                } catch (DAOException e) {
                    // Do your exception handling thing.
                    setErrorMessage("Creating UserSession failed.", e);
                }

                // Put ID in cookie.
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                setCookieValue(httpResponse, COOKIE_NAME, cookieId, COOKIE_MAX_AGE);
            }

            // Set UserSession in current HttpSession.
            httpSession.setAttribute(MANAGED_BEAN_NAME, userSession);
        }

        // Add hit and update UserSession.
        // Do your "UPDATE UserSession SET values WHERE CookieID" thing.
        userSession.addHit();
        try {
            userSessionDAO.save(userSession);
        } catch (DAOException e) {
            // UserSession might be deleted from DB meanwhile.
            // Reset current UserSession and re-filter.
            httpSession.setAttribute(MANAGED_BEAN_NAME, null);
            doFilter(request, response, chain);
            return;
        }

        // Continue filtering.
        chain.doFilter(request, response);
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // Apparently there's nothing to destroy?
    }

    // Helpers (may be refactored to some utility class) ------------------------------------------

    /**
     * Retrieve the cookie value from the given servlet request based on the given
     * cookie name.
     * @param request The HttpServletRequest to be used.
     * @param name The cookie name to retrieve the value for.
     * @return The cookie value associated with the given cookie name.
     */

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Set the cookie value in the given servlet response based on the given cookie
     * name and expiration interval.
     * @param response The HttpServletResponse to be used.
     * @param name The cookie name to associate the cookie value with.
     * @param value The actual cookie value to be set in the given servlet response.
     * @param maxAge The expiration interval in seconds. If this is set to 0,
     * then the cookie will immediately expire.
     */
    public static void setCookieValue(
        HttpServletResponse response, String name, String value, int maxAge)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}
