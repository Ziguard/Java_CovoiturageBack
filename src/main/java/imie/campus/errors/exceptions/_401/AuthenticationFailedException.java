package imie.campus.errors.exceptions._401;

public class AuthenticationFailedException extends AuthenticationException {

    public static final String R_EXPIRED_SESSION = "Expired session.";
    public static final String R_INVALID_TOKEN = "Missing, expired or invalid connection token.";
    public static final String R_MISSING_AUTHORIZATION = "Missing or invalid header 'Authorization'.";

    public final static String EXCEPTION_MESSAGE = "You're not logged in on the Web service.";
    private final String reason;

    public AuthenticationFailedException(String reason) {
        super(EXCEPTION_MESSAGE);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
