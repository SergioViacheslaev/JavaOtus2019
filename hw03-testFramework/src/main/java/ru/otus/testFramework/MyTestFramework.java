package ru.otus.testFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.testFramework.annotations.After;
import ru.otus.testFramework.annotations.Before;
import ru.otus.testFramework.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
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
            Object testsClazzObject = ReflectionHelper.instantiate(testsClazz);


            //Running @Before and @Test methods
            try {
                log.info("Run test #[{}]", testNumber++);


                runBeforeMethods(beforeMethods, testsClazzObject);

                testMethod.invoke(testsClazzObject);


            } catch (Exception e) {
                log.error("Test failure ", e);
                continue;
            }
            //If @Before or @Test fails, anyway @After methods will be executed
            finally {
                runAfterMethods(afterMethods, testsClazzObject);
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

    private static void runAfterMethods(List<Method> afterMethods, Object testsClazzObject) {
        for (Method afterMethod : afterMethods) {
            try {
                afterMethod.invoke(testsClazzObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void runBeforeMethods(List<Method> beforeMethods, Object testsClazzObject) {
        for (Method beforeMethod : beforeMethods) {
            try {
                beforeMethod.invoke(testsClazzObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    //Utility method only for sorting beforeMethods and afterMethods lists
    private static void sortMethodListByOrderAnnotation(List<Method> beforeMethods, List<Method> afterMethods) {

        beforeMethods.sort(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                int order1 = o1.getAnnotation(Before.class).order();
                int order2 = o2.getAnnotation(Before.class).order();

                return Integer.compare(order1, order2);
            }
        });


        afterMethods.sort((o1, o2) -> {
            int order1 = o1.getAnnotation(After.class).order();
            int order2 = o2.getAnnotation(After.class).order();

            return Integer.compare(order1, order2);
        });

    }


}
