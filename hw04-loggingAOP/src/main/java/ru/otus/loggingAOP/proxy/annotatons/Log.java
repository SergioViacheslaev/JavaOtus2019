package ru.otus.loggingAOP.proxy.annotatons;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * @author Sergei Viacheslaev
 * Marker annotation for ProxyHandler
 */


@Target(METHOD)
@Retention(RUNTIME)
public @interface Log {
}

