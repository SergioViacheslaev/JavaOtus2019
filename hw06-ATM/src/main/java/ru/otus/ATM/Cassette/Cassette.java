package ru.otus.ATM.Cassette;

import ru.otus.ATM.Cassette.exceptions.CassetteIsFullException;
import ru.otus.ATM.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.ATM.FaceValue;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public interface Cassette {
    void receiveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException,
            CassetteOutOfAmountException;

    Map<FaceValue, Integer> giveBanknotes(int cashAmount) throws CassetteOutOfAmountException;

}
