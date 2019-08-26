package ru.otus.AtmDepartment.Cassette;


import ru.otus.AtmDepartment.Cassette.exceptions.CassetteIsFullException;
import ru.otus.AtmDepartment.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.AtmDepartment.FaceValue;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public interface Cassette {
    void receiveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException,
            CassetteOutOfAmountException;

    Map<FaceValue, Integer> giveBanknotes(int cashAmount) throws CassetteOutOfAmountException;

}
