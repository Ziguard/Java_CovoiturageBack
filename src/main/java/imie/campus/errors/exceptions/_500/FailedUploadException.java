package imie.campus.errors.exceptions._500;

import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static imie.campus.utils.commons.GeneralUtils.format;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FailedUploadException extends RestException {
    private static final String EXCEPTION_MESSAGE = "The file {} could not be uploaded to the server.";

    public FailedUploadException(String filename, Throwable cause) {
        super(format(EXCEPTION_MESSAGE, filename), cause);
    }
}
