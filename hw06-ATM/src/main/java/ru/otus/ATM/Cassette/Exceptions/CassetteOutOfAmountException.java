package ru.otus.ATM.Cassette;

/**
 * @author Sergei Viacheslaev
 */
public class CassetteOutOfAmountException extends Exception {
    public CassetteOutOfAmountException(String message) {
        super(message);
    }
}
