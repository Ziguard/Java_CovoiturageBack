package imie.campus.errors.exceptions._401;

/**
 * An exception thrown when the provided password does not match with user's one
 * stored in the database.
 * @author Fabien
 */
public class InvalidPasswordException extends AuthenticationException {
    static final String EXCEPTION_MESSAGE = "Incorrect password.";

    public InvalidPasswordException() {
        super(EXCEPTION_MESSAGE);
    }
}