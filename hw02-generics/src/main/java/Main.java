import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        //Testing myList
        List<Integer> myList = new DIYarrayList<>();


        //Filling list with 100 random elements
        for (int i = 0; i < 100; i++)
            myList.add(new Random().nextInt(101));
        System.out.printf("Initial list:\n%s\n\n", myList);


        // Test1. Add elements to list
        System.out.println("Result of Test1...");
        Collections.addAll(myList, 666, 777, 888, 999, 1000, 2020, 3030, 4040, 5050, 6060, 7070, 8080, 9090);
        System.out.println(myList);
        System.out.println("End of Test1\n");

        // Test2. Copy from one list to another
        System.out.println("Result of Test2...");
        List<Integer> sourceArrayList = new ArrayList<>();

        //Filling test source list
        for (int i = 100; i <= 130; i++)
            sourceArrayList.add(i);

        Collections.copy(myList, sourceArrayList);
        System.out.println(myList);
        System.out.println("End of Test2\n");


        // Test3. Sorting list
        System.out.println("Result of Test3...");
        Collections.sort(myList);

        System.out.println(myList);
        System.out.println("End of Test3\n");

    }
}
