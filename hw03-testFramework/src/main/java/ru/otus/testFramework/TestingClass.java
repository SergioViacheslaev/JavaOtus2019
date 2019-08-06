package ru.otus.testFramework;


import ru.otus.testFramework.annotations.After;
import ru.otus.testFramework.annotations.Before;
import ru.otus.testFramework.annotations.Test;

/**
 * Simple class for testing annotated methods.
 */

public class TestingClass {

    @Before
    public void doBefore() {
        System.out.println("Running before @test");
    }

    @After
    public void doAfter() {
        System.out.println("Running after @test");
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
        throw new RuntimeException("Failure!");
    }



}
