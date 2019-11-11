package ru.otus.hw08Json;

import com.google.gson.Gson;
import ru.otus.hw08Json.testObjects.PetHouse;
import ru.otus.hw08Json.testObjects.User;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    public static void main(String[] args) {
        //Тестовый объект
        PetHouse petHouse = new PetHouse();


        //Проверяем как выводит Gson
        Gson gson = new Gson();
        System.out.println(gson.toJson(petHouse));

        String userJsonString =  gson.toJson(new User("Alex","Fox",33));
        System.out.println(userJsonString);

        User objectUser= gson.fromJson(userJsonString,User.class);

        System.out.println(objectUser);




/*

//        Object object = gson.fromJson("{firstName:aa, lastName:bb, age:123}",)

        //Проверяем наш вывод
        JsonObjectWriter jsonObjectWriter = new JsonObjectWriter();
        String jsonPethouse = jsonObjectWriter.toJsonString(petHouse);
        System.out.println(jsonPethouse);
*/



    }
}
