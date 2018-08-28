package imie.campus.security.configuration;

import imie.campus.security.encrypters.Encrypter;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Defines the behaviour of a security configuration provider.
 * @author Fabien
 */
public interface Configuration {
    /**
     * Defines the algorithm signature to use to generate token.
     * @return The signature algorithm
     */
    SignatureAlgorithm signatureAlgorithm();

    /**
     * Defines the secret passphrase to use to generate token.
     * @return The secret passphrase
     */
    String secretKey();

    /**
     * Defines the token time-to-leave.
     * @return the time to leave value in seconds
     */
    long timeToLeave();

    /**
     * Returns the encrypter implementation to use to encrypt password.
     * @return An Encrypter instance
     */
    Encrypter encrypter();
}
