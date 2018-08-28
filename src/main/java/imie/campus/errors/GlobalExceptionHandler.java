package imie.campus.errors;

import imie.campus.errors.exceptions.RestException;
import imie.campus.errors.exceptions.ValidationException;
import imie.campus.errors.exceptions._400.UnreadableRequestException;
import imie.campus.errors.exceptions._401.AuthenticationFailedException;
import imie.campus.errors.exceptions._500.TechnicalException;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static imie.campus.utils.commons.GeneralUtils.booleanValue;
import static imie.campus.utils.commons.GeneralUtils.getStackTraceAsString;

/**
 * Global exception handlers for all the WebService controllers.
 * @author Fabien
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private final AuthenticationService authService;

    @Value("${campus.debugging.technicals}")
    private Boolean debug;

    @Autowired
    public GlobalExceptionHandler(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * Handles HttpMessageNotReadableException (Spring / Jackson)
     *   throws when the unmarshalling process fails.
     * @param e The underlying caught exception
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestException>
    malformedJson(HttpMessageNotReadableException e) {
        logger.error("An error occurred during reading a user request : {}", e.toString());
        logger.debug(getStackTraceAsString(e));

        return buildResponse(new UnreadableRequestException());
    }

    /**
     * Handles MethodArgumentNotValidException (JSR-303)
     *   throws when the provided object validation had failed.
     * @param e The underlying caught exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestException>
    validationException(MethodArgumentNotValidException e) {
        return buildResponse(new ValidationException(e));
    }

    /**
     * Handles all RestException, which wraps all the WebService errors.
     * @param e The underlying caught exception
     * @return The error representation
     */
    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestException>
    restServiceExceptions(RestException e) {
        return buildResponse(e);
    }

    /**
     * Handles all others exceptions, logs and wraps them
     *   in a TechnicalException error message.
     * @param req The client HTTP request
     * @param ex The underlying caught exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestException>
    otherExceptions(HttpServletRequest req, Exception ex) {
        TechnicalException technical = new TechnicalException(ex, getUsername(req), booleanValue(debug));
        logger.error(technical.getTechnicalMessage(), ex);

        return buildResponse(technical);
    }

    private String getUsername(HttpServletRequest request) {
        try {
            UserTemplate<?> user = authService.checkAuthentication(request);
            return (user != null) ? user.getUsername() : null;
        } catch (AuthenticationFailedException ex) {
            return null;
        }
    }

    private static <E extends RestException> ResponseEntity<E> buildResponse(E exception) {
        return ResponseEntity.status(exception.status()).body(exception);
    }
}
