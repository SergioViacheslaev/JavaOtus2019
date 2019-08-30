package ru.otus.atmDepartment.atmStates;


import ru.otus.atmDepartment.bankomats.ATM;
import java.io.*;
import java.util.Optional;

/**
 * Начальное состояние банкомата,
 * Хранится в объекте.
 *
 * @author Sergei Viacheslaev
 */
public class AtmStartState {
    private ATM savedCopy;

    public AtmStartState(ATM atm) {

        savedCopy = createDeepAtmCopy(atm).orElseThrow(() -> new RuntimeException("Не могу создать копию ATM"));


    }

    public ATM getSavedStartState() {

        return createDeepAtmCopy(savedCopy).orElseThrow(() -> new RuntimeException("Не могу создать копию ATM"));

    }

    private Optional<ATM> createDeepAtmCopy(ATM atm) {
        ATM deepAtmCopy = null;

        try (ByteArrayOutputStream writeBuffer = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(writeBuffer);) {

            outputStream.writeObject(atm);
            byte[] savedStartState = writeBuffer.toByteArray();


            try (ByteArrayInputStream readBuffer = new ByteArrayInputStream(savedStartState);
                 ObjectInputStream inputStream = new ObjectInputStream(readBuffer);) {

                deepAtmCopy = (ATM) inputStream.readObject();


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(deepAtmCopy);

    }
}
