package ru.otus.atmDepartment.exceptions;

/**
 * @author Sergei Viacheslaev
 */
public class CassetteOutOfAmountException extends Exception {
    public CassetteOutOfAmountException(String message) {
        super(message);
    }
}
