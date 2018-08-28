package imie.campus.errors.exceptions._409;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import imie.campus.errors.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RestException {

    private static final String EXCEPTION_MESSAGE = "An identical resource already exists in the database.";

    private Class<?> entityClass;

    private Serializable identifier;

    public ConflictException() {
        super(EXCEPTION_MESSAGE);
    }

    public ConflictException(Class<?> entityClass, Serializable identifier) {
        this();
        this.entityClass = entityClass;
        this.identifier = identifier;
    }

    public ConflictException(String message) {
        super(message);
    }

    @JsonIgnore
    public Class<?> entityClass() {
        return entityClass;
    }

    @JsonGetter("entity.type")
    public String getEntityClass() { return entityClass.getSimpleName(); }

    @JsonGetter("entity.id")
    public Serializable getIdentifier() {
        return identifier;
    }

}
