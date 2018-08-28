package imie.campus.errors.exceptions._500;

import imie.campus.errors.exceptions.RestException;

public class UnassignableException extends RestException {
    private static final String EXCEPTION_MESSAGE = "Could not assign modifications to the actual persisted entity.";
    public UnassignableException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }
}
