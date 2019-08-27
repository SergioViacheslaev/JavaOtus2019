package ru.otus.atmDepartment.atmStates;

import ru.otus.atmDepartment.bankomats.ATM;
import java.io.*;

/**
 * Начальное состояние банкомата,
 * Хранится в массиве.
 * Возвращается в виде объекта.
 *
 * @author Sergei Viacheslaev
 */
public class AtmStartState {
    private byte[] savedStartState;

    public AtmStartState(ATM atm) {

        try (ByteArrayOutputStream writeBuffer = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(writeBuffer);) {

            outputStream.writeObject(atm);
            savedStartState = writeBuffer.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ATM getSavedStartState() {
        ATM atm = null;
        try (ByteArrayInputStream readBuffer = new ByteArrayInputStream(savedStartState);
             ObjectInputStream inputStream = new ObjectInputStream(readBuffer);) {

            atm = (ATM) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }

        return atm;


    }
}
