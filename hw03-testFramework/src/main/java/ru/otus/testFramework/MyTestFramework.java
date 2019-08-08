package ru.otus.testFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.testFramework.annotations.After;
import ru.otus.testFramework.annotations.Before;
import ru.otus.testFramework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


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
    public static boolean runTests(String testsClassName) {
        Class<?> testsClazz = ReflectionHelper.getClassObject(testsClassName);
        Method[] methods = ReflectionHelper.getAllDeclaredMethods(testsClassName);

        List<Method> beforeMethods = ReflectionHelper.getAnnotatedMethodsList(Before.class, methods);
        List<Method> afterMethods = ReflectionHelper.getAnnotatedMethodsList(After.class, methods);
        List<Method> testMethods = ReflectionHelper.getAnnotatedMethodsList(Test.class, methods);


        //Sort lists only annotated with @before,@after by parameter order
        sortMethodListByOrderAnnotation(beforeMethods, afterMethods);


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

            //Get instance of Tests class
            Object testsClazzObject = null;
            try {
                testsClazzObject = testsClazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Running @Before and @Test methods
            try {
                log.info("Run test #[{}]", testNumber++);


                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(testsClazzObject);
                }

                testMethod.invoke(testsClazzObject);


            } catch (Exception e) {
                log.error("Test failure ", e);
                continue;
            }
            //If @Before or @Test fails, anyway @After methods will be executed
            finally {
                for (Method afterMethod : afterMethods) {
                    try {
                        afterMethod.invoke(testsClazzObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            testsPassedCounter++;
        }

        /**
         * TEST is successfully passed if only: ALL @AFTER @BEFORE @TEST Methods finished whith no exceptions !
         */
        log.info("Total tests passed: {}", testsPassedCounter);
        log.info("Total tests failed: {}", testMethods.size() - testsPassedCounter);

        return testMethods.size() == testsPassedCounter;


    }


    //Utility method only for sorting beforeMethods and afterMethods lists
    private static void sortMethodListByOrderAnnotation(List<Method> beforeMethods, List<Method> afterMethods) {
        beforeMethods.sort((o1, o2) -> {
            int order1 = ReflectionHelper.getAnnotationBefore(o1).order();
            int order2 = ReflectionHelper.getAnnotationBefore(o2).order();

            return Integer.compare(order1, order2);


        });

        afterMethods.sort((o1, o2) -> {
            int order1 = ReflectionHelper.getAnnotationAfter(o1).order();
            int order2 = ReflectionHelper.getAnnotationAfter(o2).order();

            return Integer.compare(order1, order2);


        });
    }


}
