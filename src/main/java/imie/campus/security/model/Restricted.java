package imie.campus.security.model;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation marks controller class or action methods to attribute them roles and
 * restrict access to some resources.
 * @author Fabien
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Restricted {
    /**
     * A list of the roles required for a method/a controller.
     * If none role had been specified, the method will not be reachable by any user
     * If @Restricted is placed on a controller class, the roles will be applied to
     * all its action methods, except if one of them itself defined
     * another @Restricted annotation
     * @return A list of required role for this route
     */
    String[] value();
}
