package imie.campus.errors.exceptions._405;

import imie.campus.errors.exceptions.RestException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@ResponseStatus(METHOD_NOT_ALLOWED)
public class RemoveNotAllowed extends RestException {
    private static final String EXCEPTION_MESSAGE = "The removing of this resource is not supported.";

    public RemoveNotAllowed() {
        super(EXCEPTION_MESSAGE);
    }
}
