package ru.otus.atmDepartment.withdrawStrategies;

import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public interface WithdrawStrategy {
    Map<FaceValue, Integer> withdrawCash(CassettesStorage cassettesStorage, int cashAmount) throws CassetteOutOfAmountException;

}
