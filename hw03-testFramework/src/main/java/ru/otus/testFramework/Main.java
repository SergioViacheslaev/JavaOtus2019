package ru.otus.testFramework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(Main.class);

        try {
            MyTestFramework.runTestTasks(TestingClass.class.getName());
        } catch (Exception e) {
            log.info("Tests failed !");
            e.printStackTrace();
        }


    }
}
