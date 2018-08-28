package imie.campus.errors.exceptions._500;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import imie.campus.errors.exceptions.RestException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static imie.campus.utils.commons.GeneralUtils.findLastExceptionInHierarchy;
import static imie.campus.utils.commons.GeneralUtils.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"cause", "suppressed", "localizedMessage", "stackTrace"})
@ResponseStatus(INTERNAL_SERVER_ERROR)
public class TechnicalException extends RestException {
    private static final String EXCEPTION_MESSAGE = "A technical error has occured. " +
            "Please contact the service administrator.";

    private final String uuid = UUID.randomUUID().toString();
    private final String username;
    private boolean includeCause;


    public TechnicalException(Throwable cause, String username, boolean includeCause) {
        super(EXCEPTION_MESSAGE, cause);
        this.username = username;
        this.includeCause = includeCause;
    }

    public String getUuid() {
        return uuid;
    }

    @JsonGetter("cause.message")
    public String getCauseMessage() {
        return (includeCause) ?
                findLastExceptionInHierarchy(getCause()).getLocalizedMessage() : null;
    }

    @JsonGetter("cause.type")
    public String getCauseType() {
        return (includeCause) ?
                findLastExceptionInHierarchy(getCause()).getClass().getSimpleName() : null;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getTechnicalMessage() {
        String username = (getUsername() != null) ? getUsername() : "<unknown>";
        return format("A technical exception occurred.\nUuid :\t{}\nUser :\t{}",
                uuid, username);
    }
}
