package imie.campus.security.encrypters.impl;

import imie.campus.security.encrypters.Encrypter;

/**
 * A encrypter that just transmit password in clear.
 * Use with caution !
 *   This encryper implementation class is not a proper encrypter.
 *   It just treats password in clear, as is, and MUST be only use for tests or debugging purposes.
 * @author Fabien
 */
public class PlaintextEncrypter implements Encrypter<Object> {

    public PlaintextEncrypter() {

    }

    @Override
    public boolean supports(Class<?> passwordType) {
        return true;
    }

    @Override
    public boolean compare(Object given, Object hashed) {
        return given.equals(hashed);
    }

    @Override
    public Object hash(Object password) {
        return password;
    }

    @Override
    public String getName() {
        return "PLAINTEXT";
    }
}
