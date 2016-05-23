package mymodel;

import java.util.Date;

public class UserSession {

    // Properties ---------------------------------------------------------------------------------

    private String cookieId;
    private User user;
    private Date creationDate;
    private Date lastVisit;
    private int hits;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Default constructor.
     */
    public UserSession() {
        // Keep it alive.
    }

    /**
     * Construct new usersession with given cookie ID.
     */
    public UserSession(String cookieId) {
        this.cookieId = cookieId;
        this.creationDate = new Date();
        this.lastVisit = new Date();
    }

    // Getters and setters ------------------------------------------------------------------------

    // Implement default getters and setters here the usual way.

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Add hit (pageview) to the UserSession. Not necessary, but nice for stats.
     */
    public void addHit() {
        this.hits++;
        this.lastVisit = new Date();
    }

    /**
     * A convenience method to check if User is logged in.
     */
    public boolean isLoggedIn() {
        return user != null;
    }

}
