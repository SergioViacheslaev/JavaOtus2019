import com.google.common.collect.ImmutableList;


import java.util.ArrayList;
import java.util.List;

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




    }
}
