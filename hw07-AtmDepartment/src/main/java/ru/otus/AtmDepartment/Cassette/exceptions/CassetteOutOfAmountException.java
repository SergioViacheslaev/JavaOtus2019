package ru.otus.AtmDepartment.Cassette.exceptions;

/**
 * @author Sergei Viacheslaev
 */
public class CassetteOutOfAmountException extends Exception {
    public CassetteOutOfAmountException(String message) {
        super(message);
    }
}
