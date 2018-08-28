package imie.campus.errors.exceptions._401;

import imie.campus.errors.exceptions.RestException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * A generic exception inherited by all authentication relative exceptions.
 * (HTTP 401)
 * @author Fabien
 */
@ResponseStatus(UNAUTHORIZED)
public abstract class AuthenticationException extends RestException {
    protected AuthenticationException(String message) {
        super(message);
    }
}
