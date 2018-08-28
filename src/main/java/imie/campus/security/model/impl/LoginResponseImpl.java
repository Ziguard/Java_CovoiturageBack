package imie.campus.security.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import imie.campus.security.model.LoginResponse;

/**
 * An object that contains all informations of the connection session
 *   to the front after a successful login.
 * @author Fabien
 */
public class LoginResponseImpl<P> implements LoginResponse<P> {

    private String token;

    private long expiration;

    private String username;

    private P payload;

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override @JsonIgnore
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public P getPayload() {
        return payload;
    }

    @Override
    public void setPayload(P payload) {
        this.payload = payload;
    }
}
