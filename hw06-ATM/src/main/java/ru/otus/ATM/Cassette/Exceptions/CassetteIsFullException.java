package ru.otus.ATM.Cassette.Exceptions;

/**
 * @author Sergei Viacheslaev
 */
public class CassetteIsFullException extends Exception {
    public CassetteIsFullException(String message) {
        super(message);
    }
}
