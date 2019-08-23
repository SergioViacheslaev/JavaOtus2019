package ru.otus.ATMtests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.ATM.FaceValue;
import ru.otus.ATM.bankomats.ATM;
import ru.otus.ATM.bankomats.BankomatSber;

import java.util.Collections;
import java.util.Map;


/**
 * @author Sergei Viacheslaev
 */
class AtmTest {

    private ATM atm;


    @BeforeEach
    void init() {

        atm = new BankomatSber(); //480_000 в новом банкомате
    }

    @Test
    void receiveCashSuccessTest() {
        atm.receiveCash(FaceValue.ONE_THOUSAND, 100);
        assertEquals(580_000, atm.getAtmCashBalance());
    }


    @Test
    void giveCashSuccessTest() {
        atm.giveCash(9500);
        assertEquals(470_500, atm.getAtmCashBalance());
    }

    @Test
    void giveCashFailureTest() {
      Map<FaceValue,Integer> cashMap =  atm.giveCash(8750);
        assertEquals(480_000, atm.getAtmCashBalance());
        assertSame(Collections.emptyMap(),cashMap);
    }


}
