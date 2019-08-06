package ru.otus.testFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.testFramework.annotations.After;
import ru.otus.testFramework.annotations.Before;
import ru.otus.testFramework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class MyTestFramework {
    private static Logger log = LoggerFactory.getLogger(MyTestFramework.class);

    public static void runTestTasks(String testTasksClassName) throws Exception {
        log.info("Searching tests in class: '{}'", testTasksClassName);
        if (runTests(testTasksClassName)) {
            log.info("Testing task finished successfully.");
        } else {
            log.info("Some tests failed !\n");
        }
    }

    //Search and run annotated methods from class file
    public static boolean runTests(String testsClassName) throws Exception {
        Class<?> testsClazz = Class.forName(testsClassName);

        //Get all methods from file
        Method[] methods = testsClazz.getDeclaredMethods();

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        //For each method check annotations and add to specified list above
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof Before) {
                    beforeMethods.add(method);
                    break;
                } else if (annotation instanceof After) {
                    afterMethods.add(method);
                    break;
                } else if (annotation instanceof Test) {
                    testMethods.add(method);
                    break;
                }
            }
        }


        log.info("Annotated @Test methods found: ");
        testMethods.stream().map(Method::getName).forEach(System.out::println);


        //If no @Test methods in class
        if (testMethods.isEmpty()) {
            log.info("[No @Test annotated methods to run in '{}'", testsClazz);
            return true;
        }


        /** Running @Before -> @Test -> @After methods
         *  If one test failes, another continues...
         */
        log.info("Running tests in class: '{}'", testsClazz);
        int testsPassedCounter = 0;
        int testNumber = 1;
        for (Method testMethod : testMethods) {

            try {
                log.info("Run test #[{}]", testNumber++);

                Object testsClazzObject = testsClazz.getDeclaredConstructor().newInstance();

                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(testsClazzObject);
                }

                testMethod.invoke(testsClazzObject);

                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(testsClazzObject);
                }
            } catch (Exception e) {
                log.error("Test failure ", e);
                continue;
            }

            testsPassedCounter++;
        }

        log.info("Total tests passed: {}", testsPassedCounter);
        log.info("Total tests failed: {}", testMethods.size() - testsPassedCounter);

        return (testMethods.size() == testsPassedCounter) ? true : false;


    }


}
