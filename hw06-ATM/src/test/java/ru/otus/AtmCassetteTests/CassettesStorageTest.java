package ru.otus.AtmCassetteTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.ATM.Cassette.CassettesStorage;
import ru.otus.ATM.Cassette.exceptions.CassetteIsFullException;
import ru.otus.ATM.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.ATM.FaceValue;

/**
 * @author Sergei Viacheslaev
 */
public class CassettesStorageTest {
    private CassettesStorage cassettesStorage;


    @BeforeEach
    void init() {
        //480_000 в хранилище
        cassettesStorage = new CassettesStorage();

    }

    @Test
    void recieveBanknotesThrowsFullCassetteException() {
        assertThrows(CassetteIsFullException.class,()->{
            cassettesStorage.receiveBanknotes(FaceValue.ONE_THOUSAND,1000);
        });
    }


    @Test
    void giveBanknotesThrowsExceptionTest() {
        assertThrows(CassetteOutOfAmountException.class,()->{
           cassettesStorage.giveBanknotes(500_000);
        });
    }




}
