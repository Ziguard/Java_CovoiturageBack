package imie.campus.utils.commons;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * An utility functions library for impl reflections operations.
 * @author Fabien
 */
public class ReflectionUtils {

    /**
     * Indicates whether an element has at least one of the annotations.
     * @param element The element to check
     * @param annotations A collection of annotation Class
     * @return true if the element has at least one of the annotation
     */
    public static boolean hasAnnotations(
            AnnotatedElement element,
            Collection<Class<? extends Annotation>> annotations)
    {
        return annotations.stream()
                .anyMatch(element::isAnnotationPresent);
    }

    /**
     * Indicates whether an element has at least one of the annotations.
     * @param element The element to check
     * @param annotations A sequence of annotation Class
     * @return true if the element has at least one of the annotation
     */
    @SafeVarargs
    public static boolean hasAnnotations(
            AnnotatedElement element,
            Class<? extends Annotation>... annotations)
    {
        return hasAnnotations(element, Arrays.asList(annotations));
    }

    /**
     * Indicates whether an element has the provided annotation.
     * @param element The element to check
     * @param annotation An annotation Class
     * @return true if the element has the annotation
     */
    public static boolean hasAnnotations(
            AnnotatedElement element,
            Class<? extends Annotation> annotation)
    {
        return hasAnnotations(element, Collections.singletonList(annotation));
    }

    /**
     * Try to resolve the generic type of a field.
     * @param field The field to resolve
     * @return A Class representing the resolved type
     */
    public static Class<?> resolvePropertyGenericType(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType())
                .getActualTypeArguments()[0];
    }

    /**
     * Indicates whether provided Class to test is Iterable.
     * @param testClass The class to test
     * @return true if testClass is an Iterable, false else
     */
    public static boolean isIterableClass(Class<?> testClass) {
        return Iterable.class.isAssignableFrom(testClass);
    }

    /**
     * Try to call a getter method on a target object.
     * @param target The object on which call the getter
     * @param getter The getter method object
     * @return The result of the getter call, or null if an error has occurred
     */
    public static Object callGetter(Object target, Method getter) {
        try {
            return getter.invoke(target);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    /**
     * Try to retrieve the field object associated with a PropertyDescriptor.
     * @param clazz The target class
     * @param property The property descriptor
     * @return The Field instance, or null if the field was not found
     */
    public static Field getPropertyField(Class<?> clazz, PropertyDescriptor property) {
        try {
            return clazz.getDeclaredField(property.getName());
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Indicates whether a property is annotated by at least one of the provided annotations.
     * @param clazz The class of the object containing the property
     * @param property The property
     * @param annotation The annotation
     * @return true if the property has at least one of the provided property
     */
    public static boolean hasPropertyAnnotation(
            Class<?> clazz,
            PropertyDescriptor property,
            Class<? extends Annotation> annotation) {
        return hasAnnotations(property.getReadMethod(), annotation) ||
                getPropertyField(clazz, property) != null &&
                        hasAnnotations(getPropertyField(clazz, property), annotation);
    }

    /**
     * Get the annotation of provided class from the property (or its associated field).
     * @param clazz The target class
     * @param property The property
     * @param annotation The annotation class to find
     * @param <A> The type of annotation
     * @return A reference to the annotation, or null if none was founds
     */
    public static <A extends Annotation> A getPropertyAnnotation(
            Class<?> clazz,
            PropertyDescriptor property,
            Class<A> annotation) {
        final Field field = getPropertyField(clazz, property);
        return
            (hasPropertyAnnotation(clazz, property, annotation)) ?
                (hasAnnotations(property.getReadMethod(), annotation)) ?
                        property.getReadMethod().getAnnotation(annotation) :
                        (field != null) ? field.getAnnotation(annotation) : null
                : null;
    }
}
