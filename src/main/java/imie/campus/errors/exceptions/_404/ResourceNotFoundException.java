package imie.campus.errors.exceptions._404;

import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

import static imie.campus.utils.commons.GeneralUtils.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RestException {

    private static final String EXCEPTION_MESSAGE = "Requested resource has not been found.";
    private static final String EXCEPTION_MESSAGE_DETAIL = "The resource {} does not exist.";

    public ResourceNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    public ResourceNotFoundException(Serializable identifier) {
        super(format(EXCEPTION_MESSAGE_DETAIL, identifier.toString()));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
