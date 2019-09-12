package ru.otus.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marker annotation for dbexecutor.class methods
 *
 * @author Sergei Viacheslaev
 */

@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface Id {
}
