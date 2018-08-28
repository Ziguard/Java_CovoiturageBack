package imie.campus.security.model;

/**
 * Describes the structure of login response objects.
 * @param <P> The type of the response getPayload
 * @author Fabien
 */
public interface LoginResponse<P> {

    /**
     * Returns the connection token.
     * @return The token
     */
    String getToken();

    /**
     * Returns the expiration date.
     * @return The expiration date in timestamp
     */
    long getExpiration();

    /**
     * The getUsername of the just logged in user.
     * @return The username
     */
    String getUsername();

    /**
     * The response payload.
     * @return An object
     */
    P getPayload();

    /**
     * Attach a new payload to the response.
     * @param payload The payload
     */
    void setPayload(P payload);
}
