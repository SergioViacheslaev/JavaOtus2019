package ru.otus.ATM;

import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class ATM {
    private static ATM ourInstance = new ATM();


    //Хранилище кассет внутри банкомата
    private final Map<Integer, Cassette> cassetesStorage = new TreeMap<>(Comparator.reverseOrder());

    private ATM() {
        initializeCassetesStorage(FaceValue.values());
    }


    public static ATM getInstance() {
        return ourInstance;
    }


    public void receiveBanknotes(FaceValue faceValue, int amount) {


        Cassette cassette = cassetesStorage.get(faceValue.getIntValue());
        cassette.setBanknotesAmount(cassette.banknotesAmount + amount);
        printReceivedMoney(faceValue, amount);

    }

    private void printReceivedMoney(FaceValue faceValue, int amount) {

        System.out.printf("Банкомат принял %d купюр номиналом %s.%n", amount, faceValue);
    }


    //Снять деньги, выдать крупными
    public void withdrawMoney(int withdrawAmount) {
        if (withdrawAmount > getCurrentAtmMoneyAmount()) {
            System.out.println("В банкомате недостаточно средств !");
            return;
        }

        Map<FaceValue, Integer> withdrawnBanknotes = new TreeMap<>();

        int sum = withdrawAmount;


        for (Cassette cassette : cassetesStorage.values()) {
            //Временно сохраняем изначальное кол-во банкнот
            cassette.savebanknotesAmount();

            FaceValue faceValue = cassette.getCassetteFaceValue();

            while (sum > 0 && faceValue.getIntValue() <= sum && cassette.hasBanknotes()) {
                Integer num = withdrawnBanknotes.getOrDefault(faceValue, 0);
                withdrawnBanknotes.put(faceValue, num + 1);
                sum -= faceValue.getIntValue();
                cassette.decrementAmount();
            }
            if (sum == 0) break;

        }


        //В случае невозможности выдачи
        if (sum > 0) {
            //Восстановливаем состояние кол-ва банкнот
            restorebanknotesAmount();
            System.out.printf("Сумма %d не может быть выдана !%n", withdrawAmount);
            return;
        }


        System.out.printf("Успешно выдано %d денежных средств, купюрами: %n", withdrawAmount);
        withdrawnBanknotes.forEach((k, v) -> System.out.printf("Номинал %s Количество %d%n", k, v));


    }


    //Ячейка-кассета для банкнот
    private class Cassette {

        private int banknotesAmount; //Количество банкнот
        private FaceValue cassetteFaceValue; //Номинал банкнот внутри ячейки
        private int temp_banknotesAmount;

        public Cassette(FaceValue banknoteFaceValue, int banknotesAmount) {
            this.banknotesAmount = banknotesAmount;
            this.cassetteFaceValue = banknoteFaceValue;
        }

        public int getBanknotesAmount() {
            return banknotesAmount;
        }

        public FaceValue getCassetteFaceValue() {
            return cassetteFaceValue;
        }

        public void setBanknotesAmount(int banknotesAmount) {
            this.banknotesAmount = banknotesAmount;
        }

        //Количество денег в кассете
        public int currentMoneyAmount() {
            return banknotesAmount * cassetteFaceValue.getIntValue();
        }

        //Проверка остатка
        boolean hasBanknotes() {
            return getBanknotesAmount() > 0;
        }

        //Уменьшение остатка
        public void decrementAmount() {
            if (banknotesAmount > 0) {
                banknotesAmount--;
            }
        }

        //Временное сохранение кол-ва банкнот
        public void savebanknotesAmount() {
            temp_banknotesAmount = banknotesAmount;
        }


    }

    //Восстановление состояния кассет
    private void restorebanknotesAmount() {
        for (Cassette cassette : cassetesStorage.values()) {
            cassette.banknotesAmount = cassette.temp_banknotesAmount;
        }
    }


    //Начальная генерация объектов-кассет для всех Номиналов
    private void initializeCassetesStorage(FaceValue... faceValues) {
        for (FaceValue faceValue : faceValues) {
            Cassette cassette = new Cassette(faceValue, 0);
            cassetesStorage.put(faceValue.getIntValue(), cassette);
        }

    }

    //Получить общее количество денег в банкомате
    private int getCurrentAtmMoneyAmount() {

        return cassetesStorage.values().stream().mapToInt(Cassette::currentMoneyAmount).sum();

    }


    public void showAtmMoneyBalance() {
        System.out.printf("Текущий остаток наличных денег в банкомате: %d.%n", getCurrentAtmMoneyAmount());
        cassetesStorage.forEach((k, v) -> System.out.printf("Номинал '%d' Количество %d.%n", k, v.getBanknotesAmount()));


    }


}
