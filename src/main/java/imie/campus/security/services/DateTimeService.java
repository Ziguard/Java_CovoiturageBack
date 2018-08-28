package imie.campus.security.services;

import imie.campus.security.configuration.Configuration;

import java.util.Date;

/**
 * Defines behaviour for a service that is responsible for
 *   providing the now date and an expiration date calculated from a TTL.
 * This interface allows to reduce the coupling between the security layer
 * and a builtin Java implementation of Date.
 * @author Fabien
 */
public interface DateTimeService {
    /**
     * Returns the current Date
     * @return A java.util.Date instance
     */
    Date now();

    /**
     * Returns the expiration date of a token, according to the now() Date
     *   and the time-to-leave (TTL) in the configuration
     * @see Configuration#timeToLeave()
     * @return A java.util.Date instance
     */
    Date expiration();
}
