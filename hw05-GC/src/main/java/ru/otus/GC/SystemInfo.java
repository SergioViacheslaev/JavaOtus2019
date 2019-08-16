package ru.otus.GC;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Shows information about OS and JVM memory
 *
 * @author Sergei Viacheslaev
 */
public class SystemInfo {
    private static final int BYTES_IN_ONE_MBYTE = 1_048_576;


    public static void showSystemInfo() {
        System.out.println("------------SYSTEM INFORMATION------------");
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
        /* Total number of processors or cores available to the JVM */
        System.out.println("Available processors (cores): " +
                Runtime.getRuntime().availableProcessors());


        System.out.println();

        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        System.out.printf("Total RAM (GB): %.2f%n", memorySize / BYTES_IN_ONE_MBYTE / 1024d);
        /* Total amount of free memory available to the JVM */
        showFreeMemory();

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory() / BYTES_IN_ONE_MBYTE;
        /* Maximum amount of memory the JVM will attempt to use */
        System.out.println("Maximum memory (Mbytes): " +
                (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

        /* Total memory currently available to the JVM */
        System.out.println("Total memory available to JVM (Mbytes): " +
                Runtime.getRuntime().totalMemory() / BYTES_IN_ONE_MBYTE);
        System.out.println();

        showCurrentGarbageCollector();

        System.out.println("------------------------------------------");



    }

    public static void showFreeMemory() {
        int freeMemoryInMbytes = (int) (Runtime.getRuntime().freeMemory() / BYTES_IN_ONE_MBYTE);
        if (freeMemoryInMbytes <= 1) {
            System.out.println("Free memory (Kbytes): " + Runtime.getRuntime().freeMemory() / 1024);
            return;
        }
        System.out.println("Free memory (Mbytes): " +
                (int) (Runtime.getRuntime().freeMemory() / BYTES_IN_ONE_MBYTE));
    }

    public static void showCurrentGarbageCollector() {
        System.out.println("These Garbage collectors are currently running: ");
        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {

            System.out.println(gcMxBean.getName());
          //  System.out.println(gcMxBean.getObjectName());
        }
    }

}


