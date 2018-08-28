package imie.campus.errors.exceptions._403;

import imie.campus.errors.exceptions.RestException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * An exception thrown in case of unauthorized access to a restricted resource.
 * (HTTP 403)
 * @author Fabien
 */
@ResponseStatus(FORBIDDEN)
public class ForbiddenAccessException extends RestException {
    static final String EXCEPTION_MESSAGE = "Access to this resource is forbidden.";

    public ForbiddenAccessException() {
        super(EXCEPTION_MESSAGE);
    }
}
