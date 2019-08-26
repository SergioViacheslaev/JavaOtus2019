package ru.otus.atmDepartment.bankomats;


import ru.otus.atmDepartment.atmStates.AtmStartState;
import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.cassette.exceptions.CassetteIsFullException;
import ru.otus.atmDepartment.cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;
import ru.otus.atmDepartment.withdrawStrategies.MinimumBanknotesWithdrawStrategy;
import ru.otus.atmDepartment.withdrawStrategies.WithdrawStrategy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class BankomatSber implements ATM, Serializable {
    private CassettesStorage cassettes_storage = new CassettesStorage();
    private transient final String atmID;
    private WithdrawStrategy withdrawStrategy;
    private transient AtmStartState atmStartState;

    public BankomatSber(String atmID) {
        this.atmID = atmID;
        this.withdrawStrategy = new MinimumBanknotesWithdrawStrategy();
        this.atmStartState = new AtmStartState(this);
    }

    public BankomatSber(String atmID, WithdrawStrategy withdrawStrategy) {
        this.atmID = atmID;
        this.withdrawStrategy = withdrawStrategy;
        setAtmStartState();

    }

    @Override
    public void setAtmStartState() {
        atmStartState = new AtmStartState(this);
    }

    @Override
    public void restoreAtmToStartState() {
        byte[] buffer = atmStartState.getAtmStartState();
        try (ByteArrayInputStream readBuffer = new ByteArrayInputStream(buffer);
             ObjectInputStream inputStream = new ObjectInputStream(readBuffer);) {
            BankomatSber sberStartState = (BankomatSber) inputStream.readObject();
            cassettes_storage = sberStartState.cassettes_storage;
            withdrawStrategy = sberStartState.withdrawStrategy;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAtmID() {
        return atmID;
    }

    @Override
    public void setWithdrawStrategy(WithdrawStrategy withdrawStrategy) {
        this.withdrawStrategy = withdrawStrategy;
    }

    @Override
    public CassettesStorage getCassettesStorage() {
        return cassettes_storage;
    }

    @Override
    public void receiveCash(FaceValue faceValue, int banknotesAmount) {
        try {
            cassettes_storage.receiveBanknotes(faceValue, banknotesAmount);
            System.out.printf("Банкомат успешно принял сумму %d%n", banknotesAmount * faceValue.getIntValue());
        } catch (CassetteIsFullException | CassetteOutOfAmountException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<FaceValue, Integer> giveCash(int cashAmount) {


        try {
            return withdrawStrategy.withdrawCash(cassettes_storage, cashAmount);
        } catch (CassetteOutOfAmountException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    @Override
    public void printCassetteStorage() {
        cassettes_storage.getCassetteStorage().values().forEach(System.out::println);
    }

    @Override
    public void showCurrentAtmCashBalance() {
        System.out.printf("Текущий баланс наличных денег в банкомате %s: %d%n", atmID, getAtmCashBalance());
    }

    @Override
    public int getAtmCashBalance() {
        return cassettes_storage.getCassetesStorageBalance();
    }

    @Override
    public String toString() {
        return atmID;
    }


}