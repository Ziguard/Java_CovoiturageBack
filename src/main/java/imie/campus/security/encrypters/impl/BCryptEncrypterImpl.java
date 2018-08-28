package imie.campus.security.encrypters.impl;

import imie.campus.security.encrypters.SaltEncrypter;
import org.mindrot.jbcrypt.BCrypt;

/**
 * An encrypter implementation that use BCrypt algorithm to hash passwords.
 * @author Fabien
 */
public class BCryptEncrypterImpl implements SaltEncrypter<String, String> {

    /**
     * Number of logarithms rounds used for generating hash.
     * Be careful ! The larger is the value, the longer is the generation.
     */
    private final static int BCRYPT_SALT_LOGROUNDS = 10;

    @Override
    public boolean supports(Class<?> passwordType) {
        return passwordType.isAssignableFrom(String.class);
    }

    @Override
    public boolean compare(String given, String hashed) {
        try {
            return BCrypt.checkpw(given, hashed);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, generateSalt());
    }


    @Override
    public String generateSalt() {
        return BCrypt.gensalt(BCRYPT_SALT_LOGROUNDS);
    }

    @Override
    public String hash(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public String getName() {
        return "BCRYPT";
    }
}
