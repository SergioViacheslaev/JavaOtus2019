package ru.otus.ATM;



/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {
        ATM atm = ATM.getInstance();

        //Заносим деньги в банкомат
        atm.receiveBanknotes(FaceValue.FIVE_THOUSAND, 10);
        atm.receiveBanknotes(FaceValue.ONE_THOUSAND, 5);
        atm.receiveBanknotes(FaceValue.ONE_HUNDRED, 100);
        atm.receiveBanknotes(FaceValue.FIVE_HUNDRED, 10);
        atm.receiveBanknotes(FaceValue.THREE_THOUSAND, 10);

        //Смотрим статус- сколько сейчас в банкомате
        atm.showAtmMoneyBalance();


        //Снимаем деньги и выдаем крупными
        atm.withdrawMoney(2500);
        atm.showAtmMoneyBalance();


        //Не сможем снять
        atm.withdrawMoney(7677);
        atm.showAtmMoneyBalance();






    }
}
}
