package ru.otus.atmDepartment.cassette;


import ru.otus.atmDepartment.cassette.exceptions.CassetteIsFullException;
import ru.otus.atmDepartment.cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;

/**
 * @author Sergei Viacheslaev
 */
public interface Cassette {
    void receiveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException,
            CassetteOutOfAmountException;

    // Map<FaceValue, Integer> giveBanknotes(int cashAmount) throws CassetteOutOfAmountException;

}
