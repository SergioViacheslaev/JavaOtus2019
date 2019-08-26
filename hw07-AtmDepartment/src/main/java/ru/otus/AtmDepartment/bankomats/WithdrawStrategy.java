package ru.otus.AtmDepartment.bankomats;

import ru.otus.AtmDepartment.Cassette.CassettesStorage;
import ru.otus.AtmDepartment.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.AtmDepartment.FaceValue;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public interface WithdrawStrategy {
    Map<FaceValue, Integer> withdrawCash(CassettesStorage cassettesStorage, int cashAmount) throws CassetteOutOfAmountException;
}
