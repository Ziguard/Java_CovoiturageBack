package imie.campus.errors.exceptions._400;

import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectFileException extends RestException {
    public IncorrectFileException(String message) {
        super(message);
    }
}
