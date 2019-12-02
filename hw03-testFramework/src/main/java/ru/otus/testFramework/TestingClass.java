package ru.otus.testFramework;


import ru.otus.testFramework.annotations.After;
import ru.otus.testFramework.annotations.Before;
import ru.otus.testFramework.annotations.Test;

/**
 * Simple class for testing annotated methods.
 */
public class TestingClass {

    @Before(order = 1)
    public void doBefore1() {
        System.out.println("Running before1 @test");
    }

    @Before(order = 3)
    public void simpleBeforeMethod() {
        System.out.println("Running before3 @test");
    }


    @Before(order = 2)
    public void doBefore2() {
        System.out.println("Running before2 @test");
//        throw new RuntimeException("Вылетел метод Before !!");
    }


    @After(order = 3)
    public void doAfter1() {
        System.out.println("Running after3 @test");
    }

    @After(order = 2)
    public void doAfter2() {
        System.out.println("Running after2 @test");
    }

    @After(order = 1)
    public void simpleAfterMethod() {
        System.out.println("Running after1 @test");
    }



    @Test
    public void doTest1() {
        System.out.println("Test doTest1()  is running...");
    }

    @Test
    public void doTest2() {
        System.out.println("Test doTest2() is running...");
    }


    //Testing if test methods fails
    @Test
    public void doTest3() {
        System.out.println("Test doTest3() is running...");
//         throw new RuntimeException("Failure!");
    }


}
