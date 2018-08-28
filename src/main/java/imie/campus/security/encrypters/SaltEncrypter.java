package imie.campus.security.encrypters;

/**
 * The contract that define the behaviour of an encrypter that supports salt addition.
 * The salt is a technic that allows to enforce the password hash with a password-independant
 * fingerprint.
 * @param <P> The type of the object that wraps and represents the password
 * @param <S> The type of the object that wraps and represents the salt
 * @see Encrypter
 * @author Fabien
 */
public interface SaltEncrypter<P, S> extends Encrypter<P> {
    /**
     * Generate a salt.
     * @return The generated salt
     */
    S generateSalt();

    /**
     * Compute the hash for a given password, with a salt.
     * @param password The password to hash
     * @param salt The salt to add to the generated hash
     * @return The password hash
     */
    P hash(P password, S salt);
}
