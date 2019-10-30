package ru.otus.sequencenumbers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sergei Viacheslaev
 */
public class NumbersSequence {
    private final Thread thread1;
    private final Thread thread2;

    private AtomicInteger counter1 = new AtomicInteger(1);
    private AtomicInteger counter2 = new AtomicInteger(1);

    private volatile boolean decrementFlag = false;

    public NumbersSequence() {
        this.thread1 = new Thread(() -> {
            action(counter1);

        });

        this.thread2 = new Thread(() -> {
            action(counter2);

        });
    }

    public static void main(String[] args) {

        NumbersSequence numbersSequence = new NumbersSequence();

        numbersSequence.startSequenceThreads();


    }

    public void startSequenceThreads() {
        thread1.start();
        thread2.start();
    }


    public synchronized void action(AtomicInteger counter) {
        while (true) {
            int currentCounter = counter.get();

            if (currentCounter == 1) {
                decrementFlag = false;
            }

            if (decrementFlag == true) {
                System.out.printf(" %d",counter.getAndDecrement());

            } else {
                System.out.printf(" %d",counter.getAndIncrement());
            }


            if (counter1.get() == 11 && counter2.get() == 11) {
                counter1.set(9);
                counter2.set(9);
                decrementFlag = true;
            }

//            System.out.println("counter1 = " + counter1.get());
//            System.out.println("counter2 = " + counter2.get());


            sleep(500);


            if (thread1.getState().equals(Thread.State.WAITING) || thread2.getState().equals(Thread.State.WAITING)) {
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
}
