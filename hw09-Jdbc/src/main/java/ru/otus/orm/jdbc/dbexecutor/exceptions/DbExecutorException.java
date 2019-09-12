package ru.otus.orm.jdbc.dbexecutor.exceptions;

/**
 * @author Sergei Viacheslaev
 */
public class DbExecutorException extends RuntimeException {
    public DbExecutorException(String message) {
        super(message);
    }
    public DbExecutorException(Throwable cause) {
        super(cause);
    }
}
