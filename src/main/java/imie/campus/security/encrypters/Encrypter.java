package imie.campus.security.encrypters;

/**
 * The contract that defines the general behaviour of an encrypter,
 * use by the security layer to avoid storing password in clear in the persistence layer.
 * @param <P> The type of the object that wraps and represents the password
 * @author Fabien
 */
public interface Encrypter<P> {
    /**
     * Indicates whether the encrypter supports data of type give in argument.
     * @param passwordType The Class object representign the type of the password to encrypt
     * @return true if the encrypter support this password, else otherwise
     */
    boolean supports(Class<?> passwordType);

    /**
     * Compare a password with a candidate hash.
     * @param given The candidate password
     * @param hashed The candidate hash
     * @return true if both password and hash matches, false otherwise.
     */
    boolean compare(P given, P hashed);

    /**
     * Compute the hash for a given password.
     * @param password The password to hash
     * @return The password hash
     */
    P hash(P password);

    /**
     * Give the name of the underlying algorithm.
     * @return The algorithm name
     */
    String getName();
}
