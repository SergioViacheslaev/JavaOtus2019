package ru.otus.ATMtests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.ATM.FaceValue;
import ru.otus.ATM.bankomats.ATM;
import ru.otus.ATM.bankomats.BankomatSber;


/**
 * @author Sergei Viacheslaev
 */
class AtmTest {

    private ATM atm;

    @BeforeEach
    void init() {
        atm = new BankomatSber();
        atm.receiveCash(FaceValue.ONE_THOUSAND, 100);
        atm.receiveCash(FaceValue.FIVE_THOUSAND, 10);
        atm.receiveCash(FaceValue.THREE_THOUSAND, 10);
        atm.receiveCash(FaceValue.FIVE_HUNDRED, 100);
        atm.receiveCash(FaceValue.ONE_HUNDRED, 100);
    }

    @Test
    void giveCashSuccessTest() {
        atm.giveCash(9500);
        assertEquals(230_500, atm.getAtmCashBalance());
    }

    @Test
    void giveCashFailureTest() {
        atm.giveCash(8750);
        assertEquals(240_000, atm.getAtmCashBalance());
    }

    @Test
    void receiveCashSuccessTest() {
        atm.receiveCash(FaceValue.ONE_THOUSAND,100);
        assertEquals(340_000,atm.getAtmCashBalance());
    }

    @Test
    void receiveCashFailureTest() {
        atm.receiveCash(FaceValue.ONE_THOUSAND,0);
        assertEquals(240_000,atm.getAtmCashBalance());
    }





}
