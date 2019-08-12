package ru.otus.GC;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Sergei Viacheslaev
 */
public class MemoryTasks {
    private static long startTime;
    private static double consumeFactor;

    public static void consumeMemory(int objectsPerLoop) throws OutOfMemoryError {
        startTime = System.currentTimeMillis();
        List<String> tempList = new LinkedList<>();

        while (true) {

            for (int i = 0; i < objectsPerLoop; i++) {
                String tmpRandomString = new String("1234567890" + new Random().nextInt(10_000));
                tempList.add(tmpRandomString);


            }

            //Освобождаем память, очистив 20% tempLista
            int eraseLimit = (int) (objectsPerLoop * consumeFactor);
            for (int i = 0; i < eraseLimit; i++) {

                tempList.remove(0);
            }


            PasswordFactory.generateRandomPassword();


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            SystemInfo.showFreeMemory();
        }


    }


    public static void printResults() {
        long finishTime = System.currentTimeMillis();
        List<String> generatedPasswords = PasswordFactory.getRandomPasswords();
        System.out.println("\n------------------------------------------");
        System.out.printf("Random passwords generated: %d%n", generatedPasswords.size());
        System.out.printf("Time spent: %d seconds%n", (finishTime - startTime) / 1000);
        System.out.println("------------------------------------------");
    }

    public static double getConsumeFactor() {
        return consumeFactor;
    }

    /**
     * Влияет на очистку tempList и скорость приближения к outOfMemory
     * Если выставить 0.2 то очистится 20% списка, если 0.9 то 90%
     * @param consumeFactor
     */
    public static void setConsumeFactor(double consumeFactor) {
        MemoryTasks.consumeFactor = consumeFactor;
    }
}

