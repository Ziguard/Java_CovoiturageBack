package imie.campus.errors.exceptions._400;

import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception used to hide Spring unmarshalling exception and to
 *   throw a more generic error message for HTTP request reading problems.
 * (HTTP 400)
 * @author Fabien
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnreadableRequestException extends RestException {
    private static final String EXCEPTION_MESSAGE = "Le format de la requête JSON envoyé n'est pas correct.";
    public UnreadableRequestException() {
        super(EXCEPTION_MESSAGE);
    }
}
