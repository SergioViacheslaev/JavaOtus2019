package ru.otus.ATM.Cassette;

import ru.otus.ATM.FaceValue;

/**
 * @author Sergei Viacheslaev
 */
public interface Cassette {
    void receiveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException,
            CassetteOutOfAmountException;

    void giveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException,
            CassetteOutOfAmountException;

}
