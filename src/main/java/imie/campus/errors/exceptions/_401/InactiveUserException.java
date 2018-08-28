package imie.campus.errors.exceptions._401;

/**
 * An exception thrown when the user that attempt to login has not been activated
 * yet by a moderator.
 * @author Fabien
 */
public class InactiveUserException extends AuthenticationException {
    static final String EXCEPTION_MESSAGE = "This user is not active, please contact a moderator.";

    public InactiveUserException() {
        super(EXCEPTION_MESSAGE);
    }
}