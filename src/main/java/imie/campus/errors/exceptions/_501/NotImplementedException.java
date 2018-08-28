package imie.campus.errors.exceptions._501;

import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends RestException {

    private static final String EXCEPTION_MESSAGE = "This feature is not supported / allowed for this resource.";

    public NotImplementedException() {
        super(EXCEPTION_MESSAGE);
    }

}
