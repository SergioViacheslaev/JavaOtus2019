package ru.otus.ATMtests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.ATM.ATM;
import ru.otus.ATM.FaceValue;

/**
 * @author Sergei Viacheslaev
 */
public class AtmTest {
        private ATM atm;


    @BeforeEach
    void init() {
        atm = ATM.getInstance();
        System.out.println("before");
        //Заносим деньги в банкомат
        atm.receiveBanknotes(FaceValue.FIVE_THOUSAND, 10);
        atm.receiveBanknotes(FaceValue.ONE_THOUSAND, 5);
        atm.receiveBanknotes(FaceValue.ONE_HUNDRED, 100);
        atm.receiveBanknotes(FaceValue.FIVE_HUNDRED, 10);
        atm.receiveBanknotes(FaceValue.THREE_THOUSAND, 10);
    }

/* @Test
    void withdrawMoneyTest() {
   //  int AtmMoneyBalanceAfterWithrdraw = 50_000;
     atm.withdrawMoney(50_000);

     assertEquals(50_000,atm.getCurrentAtmMoneyAmount(),"withdrawMoneyTest finished");
 }*/


}
