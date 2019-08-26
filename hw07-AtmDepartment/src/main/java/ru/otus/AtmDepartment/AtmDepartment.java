package ru.otus.AtmDepartment;

import ru.otus.AtmDepartment.bankomats.ATM;
import ru.otus.AtmDepartment.bankomats.BankomatSber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class AtmDepartment {
    private List<ATM> atmList = new ArrayList<>();
    private final int DEFAULT_ATM_NUMBER = 5;

    public AtmDepartment() {
        for (int i = 0; i < DEFAULT_ATM_NUMBER; i++) {
            atmList.add(new BankomatSber(String.format("SberATM#%d", i + 1)));
        }
    }

    public void addAtm(ATM atm) {
        atmList.add(atm);
    }

    public List<ATM> getAtmList() {
        return atmList;
    }
}
