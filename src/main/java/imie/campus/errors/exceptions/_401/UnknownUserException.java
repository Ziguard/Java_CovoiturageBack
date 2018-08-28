package imie.campus.errors.exceptions._401;

/**
 * An exception thrown when the security subsystem cannot find a matching
 *   user in the database to the provided username.
 * @author Fabien
 */
public class UnknownUserException extends AuthenticationException {
    static final String EXCEPTION_MESSAGE = "Unknown user. " +
            "Please check your credentials.";

    public UnknownUserException() {
        super(EXCEPTION_MESSAGE);
    }
}
