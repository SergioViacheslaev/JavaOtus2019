package ru.otus.atmDepartment.visitors;

import ru.otus.atmDepartment.AtmDepartment;
import ru.otus.atmDepartment.bankomats.ATM;

/**
 * @author Sergei Viacheslaev
 */
public class TotalAtmsCashBalanceVisitor implements Visitor {
    @Override
    public void visit(AtmDepartment atmDepartment) {
        int totalAtmsCashBalance =  atmDepartment.getAtmList().stream().mapToInt(ATM::getAtmCashBalance).sum();
        atmDepartment.setTotalAtmsCashAmount(totalAtmsCashBalance);
    }


}
