package ru.otus.GC;


/**
 * @author Sergei Viacheslaev
 */

/*      -Xms512m
        -Xmx512m
        -Xlog:gc*:file=/logs/gc-%p-%t.log
        -XX:+HeapDumpOnOutOfMemoryError
        -XX:HeapDumpPath=/logs/dump
        -XX:+UseG1GC


        -XX:+UseSerialGC
        -XX:+UseParallelGC
        -XX:+UseConcMarkSweepGC

        -XX:+PrintGCDetails property would print the details of how much memory is reclaimed in each generation.
        -Xlog:gc=debug:file=/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
        -XX:HeapDumpPath=/logs/dumpn

*/


public class Main {
    public static void main(String[] args) throws InterruptedException {

        SystemInfo.showSystemInfo();
        Thread.sleep(1000);


        GCMonitor.switchOnMonitoring();

        try {
            //These parameters are about 5.30 minutes work app
            MemoryTasks.setConsumeFactor(0.2d);
            MemoryTasks.consumeMemory(1500);
        } catch (OutOfMemoryError outOfMemoryError) {
            MemoryTasks.printResults();

            outOfMemoryError.printStackTrace();
        }


    }
}



