package ru.otus.hw08Json;

import com.google.gson.Gson;
import ru.otus.hw08Json.testObjects.PetHouse;

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

        //Проверяем наш вывод
        JsonObjectWriter jsonObjectWriter = new JsonObjectWriter();
        String jsonPethouse = jsonObjectWriter.toJsonString(petHouse);
        System.out.println(jsonPethouse);


    }
}
