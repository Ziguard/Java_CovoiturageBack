package imie.campus.errors.exceptions._501;

import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotModifiableException extends RestException {

    private static final String EXCEPTION_MESSAGE = "This resource could not be modified.";

    public NotModifiableException() {
        super(EXCEPTION_MESSAGE);
    }

    public NotModifiableException(Class<?> entityClass) {
        super(EXCEPTION_MESSAGE + " - entity class : " + entityClass.getSimpleName());
    }

}
