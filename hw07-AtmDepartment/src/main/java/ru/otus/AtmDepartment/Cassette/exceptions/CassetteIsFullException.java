package ru.otus.AtmDepartment.Cassette.exceptions;

/**
 * @author Sergei Viacheslaev
 */
public class CassetteIsFullException extends Exception {
    public CassetteIsFullException(String message) {
        super(message);
    }
}
