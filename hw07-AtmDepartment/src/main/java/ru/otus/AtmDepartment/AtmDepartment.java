package ru.otus.AtmDepartment;

import ru.otus.AtmDepartment.Cassette.CassettesStorage;
import ru.otus.AtmDepartment.bankomats.ATM;
import ru.otus.AtmDepartment.bankomats.BankomatSber;
import ru.otus.AtmDepartment.bankomats.MinimumBanknotesWithdrawStrategy;
import ru.otus.AtmDepartment.bankomats.WithdrawStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class AtmDepartment {
    private List<ATM> atmList = new ArrayList<>();
    private final int DEFAULT_ATM_NUMBER = 5;
    private final WithdrawStrategy DEFAULT_WITHDRAW_STRATEGY = new MinimumBanknotesWithdrawStrategy();

    public AtmDepartment() {
        for (int i = 0; i < DEFAULT_ATM_NUMBER; i++) {
            BankomatSber bankomatSber = new BankomatSber(String.format("SberATM#%d",i+1),DEFAULT_WITHDRAW_STRATEGY);
            atmList.add(bankomatSber);
        }
    }

    public void addAtm(ATM atm) {
        atmList.add(atm);
    }

    public List<ATM> getAtmList() {
        return atmList;
    }

    public ATM getAtm(String atmID) {
        for (ATM atm : atmList) {
            if (atm.getAtmID().equals(atmID)) {
                return atm;
            }

        }
        return null;
    }



}
