package com.mskn.vaadinspringproject.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loglam kontrollerin yapıldığı yerdir.
 */
public class AppLogger {
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void info(Class<?> clazz, String message) {
        getLogger(clazz).info(message);
    }

    public static void warn(Class<?> clazz, String message) {
        getLogger(clazz).warn(message);
    }

    public static void error(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).error(message, throwable);
    }
}
