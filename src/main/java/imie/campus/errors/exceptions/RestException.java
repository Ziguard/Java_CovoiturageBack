package imie.campus.errors.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * A generic exception for all the Rest WebService.
 * @author Fabien
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"suppressed", "localizedMessage", "stackTrace"})
public abstract class RestException extends RuntimeException {

    private final Instant instant = Instant.now();
    private final LocalDateTime now = LocalDateTime.now();

    /**
     * Constructs a RestException with a messag.
     * @param message The exception message
     */
    protected RestException(String message) {
        super(message);
    }

    /**
     * Constructs a RestException with a message and a cause.
     * @param message The exception message
     * @param cause The root cause of the exception
     */
    protected RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getType() {
        return getClass().getSimpleName();
    }

    public long getTimestamp() {
        return instant.getEpochSecond();
    }

    public String getDatetime() {
        return ISO_DATE_TIME.format(now);
    }

    public String getStatus() {
        return status().getReasonPhrase();
    }

    public int getCode() {
        return status().value();
    }

    @JsonIgnore
    public HttpStatus status() {
        final ResponseStatus annotation = findAnnotation(getClass(), ResponseStatus.class);
        return annotation != null ? annotation.value() : INTERNAL_SERVER_ERROR;
    }
}
