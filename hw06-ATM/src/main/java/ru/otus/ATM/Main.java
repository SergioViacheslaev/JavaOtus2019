package ru.otus.ATM;


import ru.otus.ATM.WithdrawStrategies.largeCashWithdawStrategy;
import ru.otus.ATM.bankomats.ATM;
import ru.otus.ATM.bankomats.BankomatSber;

/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {

        ATM bankomatSber = new BankomatSber();
        bankomatSber.setCashWithdrawStrategy(new largeCashWithdawStrategy(bankomatSber));



        bankomatSber.receiveCash(FaceValue.ONE_THOUSAND,100);
        bankomatSber.receiveCash(FaceValue.FIVE_THOUSAND,10);
        bankomatSber.receiveCash(FaceValue.THREE_THOUSAND,10);
        bankomatSber.receiveCash(FaceValue.FIVE_HUNDRED,100);
        bankomatSber.receiveCash(FaceValue.ONE_HUNDRED,100);


        bankomatSber.showCurrentAtmCashBalance();

        bankomatSber.giveCash(9500);

        bankomatSber.showCurrentAtmCashBalance();

        bankomatSber.giveCash(2655);

        bankomatSber.showCurrentAtmCashBalance();



    }
}

