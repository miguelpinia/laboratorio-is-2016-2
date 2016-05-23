package mycontroller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import mydao.DAOException;
import mydao.DAOFactory;
import mydao.UserDAO;
import mydao.UserSessionDAO;
import mymodel.UserSession;
import mymodel.User;

public class UserForm {

    // Vars ---------------------------------------------------------------------------------------

    // Just do your DAO thing. You can make the databaseName a context init-param as well.
    // Also see the DAO tutorial.
    private static DAOFactory daoFactory = DAOFactory.getInstance("databaseName");
    private static UserDAO userDAO = daoFactory.getUserDAO();
    private static UserSessionDAO userSessionDAO = daoFactory.getUserSessionDAO();

    // Properties --------------------------------------------------------------------------------

    private UserSession userSession;
    private String username;
    private String password;

    // Actions -----------------------------------------------------------------------------------

    public void login() {
        try {

            // Do your "SELECT * FROM User WHERE username AND password" thing.
            User user = userDAO.find(username, password);

            if (user != null) {

                // User found. Put the User in the UserSession.
                userSession.setUser(user);

                // Do your "UPDATE UserSession SET values WHERE CookieID" thing.
                try {
                    userSessionDAO.save(userSession);

                    // Do your succes handling thing.
                    setSuccesMessage("You are logged in successfully!");
                } catch (DAOException e) {
                    // Do your exception handling thing.
                    setErrorMessage("Updating UserSession failed.", e);
                }
            } else {
                // Do your error handling thing.
                setErrorMessage("Unknown username and/or invalid password.");
            }
        } catch (DAOException e) {
            // Do your exception handling thing.
            setErrorMessage("Loading User failed.", e);
        }
    }

    public void logout() {

        // Just null out the user.
        userSession.setUser(null);

        try {
            // Do your "UPDATE UserSession SET values WHERE CookieID" thing.
            userSessionDAO.save(userSession);

            // Do your succes handling thing.
            setSuccesMessage("You are logged out successfully!");
        } catch (DAOException e) {
            // Do your exception handling thing.
            setErrorMessage("Updating UserSession failed.", e);
        }
    }

    // Getters and setters ------------------------------------------------------------------------

    // Implement default getters and setters here the usual way.

}
