package imie.campus.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static imie.campus.utils.commons.GeneralUtils.format;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RestException {
    private static final String EXCEPTION_MESSAGE = "The validation of the entity {} had failed : following errors has been detected.";

    private final List<ValidationError> errors;

    private class ValidationError {
        private final String message;
        private final String field;
        private final Object rejectedValue;

        public ValidationError(FieldError error) {
            this.message = error.getDefaultMessage();
            this.rejectedValue = error.getRejectedValue();
            this.field = error.getField();
        }

        public String getMessage() {
            return message;
        }

        public String getField() {
            return field;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }
    }

    public ValidationException(MethodArgumentNotValidException e) {
        super(format(EXCEPTION_MESSAGE, e.getBindingResult().getTarget().getClass().getName()));
        this.errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .collect(Collectors.toList());
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

}
