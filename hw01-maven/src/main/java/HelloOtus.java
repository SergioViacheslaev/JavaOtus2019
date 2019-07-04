import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;


import java.util.ArrayList;
import java.util.List;

/**
 * Examples.
 * To start the application:
 * mvn package
 * java -cp ./target/L01-maven.jar ru.otus.l011.Main //java.lang.NoClassDefFoundError: com/google/common/collect/Lists
 * java -jar ./target/L01-maven.jar
 * java -cp ./target/L01-maven.jar;{home_dir}\.m2\repository\com\google\guava\guava\23.0\guava-23.0.jar ru.otus.l011.Main
 *
 * To unzip the jar:
 * 7z x -oJAR ./target/L01-maven.jar
 * unzip -d JAR ./target/L01-maven.jar
 * unzip -l target/L01-maven.jar
 *
 *
 * To build:
 * mvn package
 * mvn clean compile
 * mvn assembly:single
 * mvn clean compile assembly:single
 */

public class HelloOtus {
    public static void main(String[] args) {
        System.out.println("Hello World from Java " + System.getProperty("java.version"));

        // Testing Guava Immutable list
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(100 * i);
        }

        List<Integer> immutableList = ImmutableList.copyOf(list);
        System.out.println(immutableList);
        list.add(777);
        System.out.println(immutableList);



        String testString = "";
        System.out.println(Strings.isNullOrEmpty(testString));
        testString = null;
        System.out.println(Strings.isNullOrEmpty(testString));



    }
}
