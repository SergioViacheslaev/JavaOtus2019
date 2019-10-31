package ru.otus.sequencenumbers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sergei Viacheslaev
 */
public class NumbersSequence {
    private final int DECREMENT_START_VALUE = 9;

    private final Thread thread1;
    private final Thread thread2;

    private AtomicInteger counter1 = new AtomicInteger(1);
    private AtomicInteger counter2 = new AtomicInteger(1);

    //Отвечает за направление счета, если true - считаем назад
    private boolean decrementFlag = false;


    public NumbersSequence() {
        this.thread1 = new Thread(() -> action(counter1));
        this.thread2 = new Thread(() -> action(counter2));

        this.thread1.setName("Поток #1");
        this.thread2.setName("Поток #2");
    }

    public void startSequenceThreads() {
        thread1.start();
        thread2.start();
    }


    private synchronized void action(AtomicInteger counter) {
        while (true) {

            //Если вышли за пределы значения '10',
            if (checkCountersValuesOutOfRange()) {
                counter1.set(DECREMENT_START_VALUE);
                counter2.set(DECREMENT_START_VALUE);
                decrementFlag = true;
            }

            //Если дошли до '1', то начинаем считать снова вверх
            if (counter.get() == 1) {
                decrementFlag = false;
            }


            System.out.printf("%s %d%n", Thread.currentThread().getName(),
                    decrementFlag ? counter.getAndDecrement() : counter.getAndIncrement());


            sleep(500);

            //Если кто-то ждет, то будем потоки
            if (isThreadsStateWAITING()) {
                notifyAll();
            }


            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    private void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private boolean checkCountersValuesOutOfRange() {
        return counter1.get() == 11 && counter2.get() == 11;
    }

    private boolean isThreadsStateWAITING() {
        return thread1.getState().equals(Thread.State.WAITING) || thread2.getState().equals(Thread.State.WAITING);
    }
}
