package ru.otus.atmDepartment.atmStates;

import ru.otus.atmDepartment.bankomats.ATM;
import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.withdrawStrategies.WithdrawStrategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Sergei Viacheslaev
 */
public class AtmStartState {
    private byte[] atmStartState;

    public AtmStartState(ATM atm) {

        try (ByteArrayOutputStream writeBuffer = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(writeBuffer);) {
            outputStream.writeObject(atm);
            this.atmStartState = writeBuffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getAtmStartState() {
        return atmStartState;
    }
}
