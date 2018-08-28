package imie.campus.errors.exceptions._401;

/**
 * An exception thrown when the credentials are not valid :
 *   empty login or empty password.
 * @author Fabien
 */
public class InvalidCredentialsException extends AuthenticationException {
    static final String EXCEPTION_MESSAGE = "Please enter the username AND the password.";

    public InvalidCredentialsException() {
        super(EXCEPTION_MESSAGE);
    }
}
