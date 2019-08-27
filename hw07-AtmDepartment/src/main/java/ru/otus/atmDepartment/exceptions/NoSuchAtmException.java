package ru.otus.atmDepartment.exceptions;

/**
 * @author Sergei Viacheslaev
 */
public class NoSuchAtmException extends Exception {

    public NoSuchAtmException(String atmID) {
        System.err.printf("Банкомата с  ID = %s не существует !%n", atmID);

    }
}
