package cache;

import com.cache.Cache;
import com.cache.FirstInFirstOutCache;
import com.cache.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static com.cache.enums.EvictionPoliciesEnum.FIFO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirstInFirstOutCacheTest {

    @Test
    void firstInFirstOutCacheTest_Initialization() {
        Cache<Integer, String> fifoCache = new FirstInFirstOutCache<>(10);
        assertEquals(FIFO, fifoCache.getEvictionPolicy());
        assertEquals(10, fifoCache.getStorageCapacity());
    }

    @Test
    void firstInFirstOutCacheTest() {
        Cache<String, Integer> fifoCache =  new FirstInFirstOutCache<>(3);
        fifoCache.put("a", 1);
        fifoCache.put("b", 2);
        fifoCache.put("c", 3);
        fifoCache.get("b");
        fifoCache.get("c");
        fifoCache.get("c");
        fifoCache.get("b");
        fifoCache.get("a");
        fifoCache.put("d", 4);
        assertThrows(NotFoundException.class, () -> fifoCache.get("b"));
        assertEquals(Integer.valueOf(4), fifoCache.get("d"));
        fifoCache.get("a");
        fifoCache.get("d");
        fifoCache.get("d");
        fifoCache.put("e", 5);
        assertThrows(NotFoundException.class, () -> fifoCache.get("c"));
        assertEquals(Integer.valueOf(5), fifoCache.get("e"));
    }

}
