package ru.otus.ATM;


import ru.otus.ATM.Cassette.AtmCassette;
import ru.otus.ATM.Cassette.Cassette;
import ru.otus.ATM.WithdrawStrategies.LargeCashWithdawStrategy;
import ru.otus.ATM.bankomats.ATM;
import ru.otus.ATM.bankomats.BankomatSber;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {


        ATM bankomatSber = new BankomatSber();
        bankomatSber.setCashWithdrawStrategy(new LargeCashWithdawStrategy(bankomatSber));

        bankomatSber.showCurrentAtmCashBalance();
        bankomatSber.printCassetteStorage();

        bankomatSber.receiveCash(FaceValue.ONE_THOUSAND, 100);

        bankomatSber.showCurrentAtmCashBalance();
        bankomatSber.printCassetteStorage();


    }
}

