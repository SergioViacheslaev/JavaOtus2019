import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw08Json.JsonObjectWriter;
import ru.otus.hw08Json.testObjects.PetHouse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sergei Viacheslaev
 */
public class JsonObjectWriterTest {
    private JsonObjectWriter jsonObjectWriter;
    private Gson gson;
    private PetHouse petHouse;

    @BeforeEach
    void init() {
        jsonObjectWriter = new JsonObjectWriter();
        gson = new Gson();
        petHouse = new PetHouse();
    }

    @Test
    void gsonAndMyJsonStringEqualsTest() {
        String myJsonString = jsonObjectWriter.toJsonString(petHouse);
        String gsonJsonString = gson.toJson(petHouse);

        assertEquals(myJsonString, gsonJsonString);
    }

    @Test
    void objectFromMyJsonEqualsTest() {
        String myJsonString = jsonObjectWriter.toJsonString(petHouse);
        PetHouse petHousefromJson = gson.fromJson(myJsonString, PetHouse.class);

        assertEquals(petHousefromJson, petHouse);
    }


}
