package ru.otus.mycachetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.api.model.User;
import ru.otus.cachehw.cache.impl.MyCache;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Sergei Viacheslaev
 */
public class MyCacheTests {
    private MyCache<String, User> entityCache;

    @BeforeEach
    void init() {
        entityCache = new MyCache<>();
        fillEntityCache();
    }


    @Test
    @DisplayName("Кэш должен быть очищен после каждого GC")
    void cacheShouldBePurgedAfterGC() throws InterruptedException {
        System.gc();
        Thread.sleep(200);

        int cacheSize = 0;
        for (int id = 0; id < 100; id++) {
            User userFromCache = entityCache.get(String.valueOf(id));
            if (userFromCache != null) {
                cacheSize++;
            }
        }

        assertThat(cacheSize).isEqualTo(0);

    }


    private void fillEntityCache() {
        for (int id = 0; id < 100; id++) {
            entityCache.put(String.valueOf(id), new User("User #" + id, new Random().nextInt(99)));
        }
    }


}
