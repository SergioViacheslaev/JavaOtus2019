package ru.otus.atmDepartment.cassette;

import ru.otus.atmDepartment.FaceValue;
import ru.otus.atmDepartment.exceptions.CassetteIsFullException;
import ru.otus.atmDepartment.exceptions.CassetteOutOfAmountException;


/**
 * @author Sergei Viacheslaev
 */
public interface Cassette {
    void receiveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException,
            CassetteOutOfAmountException;
}
