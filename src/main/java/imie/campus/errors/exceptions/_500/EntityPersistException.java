package imie.campus.errors.exceptions._500;

public class EntityPersistException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "An error has occurred during persisting an entity in the database.";

    public EntityPersistException(Throwable cause) {
        super(EXCEPTION_MESSAGE + " : " + cause.getMessage(), cause);
    }
}
