package ru.otus.GC;

import java.util.*;

/**
 * Class can generate list of random passwords
 *
 *
 * @author Sergei Viacheslaev
 */
public class PasswordFactory {
    private static List<String> randomPasswords = new ArrayList<>();




    public static String generateRandomPassword() {

        String tempPassword = new String("1A34Bjc8Z0" + new Random().nextInt(10000));
        randomPasswords.add(tempPassword);
        return shuffleString(tempPassword);

    }

    private static String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }

    public static List<String> getRandomPasswords() {
        return randomPasswords;
    }
}
