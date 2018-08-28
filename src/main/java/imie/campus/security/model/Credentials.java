package imie.campus.security.model;

/**
 * A bean that contains user credentials sent during login.
 * @author Fabien
 */
public class Credentials {
    /**
     * The username
     */
    private String username;

    /**
     * The user password
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
