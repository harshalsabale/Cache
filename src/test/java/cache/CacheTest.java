package cache;

import com.cache.Cache;
import com.cache.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;


import static com.cache.enums.EvictionPoliciesEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheTest {

    @Test
    void cacheTest_DefaultPolicyInitialization() {
        Cache<Integer, String> cache = new Cache<>(10);
        assertEquals(LRU, cache.getEvictionPolicy());
        assertEquals(10, cache.getStorageCapacity());
    }

    @Test
    void cacheTest_RandomReplacementPolicyInitialization() {
        Cache<Integer, String> cache = new Cache<>(10, RR);
        assertEquals(RR, cache.getEvictionPolicy());
        assertEquals(10, cache.getStorageCapacity());
    }

    @Test
    void cacheTest_FirstInFirstOutPolicyInitialization() {
        Cache<Integer, String> cache = new Cache<>(10, FIFO);
        assertEquals(FIFO, cache.getEvictionPolicy());
        assertEquals(10, cache.getStorageCapacity());
    }

    @Test
    void putMethodTest_IllegalArgumentException() {
        Cache<Integer, String> cache = new Cache<>(10);
        assertThrows(IllegalArgumentException.class, () -> cache.put(null, "a"));
        assertThrows(IllegalArgumentException.class, () -> cache.put(1, null));
        assertThrows(IllegalArgumentException.class, () -> cache.put(null, null));
    }

    @Test
    void getMethodTest_IllegalArgumentException() {
        Cache<Integer, String> cache = new Cache<>(10);
        assertThrows(IllegalArgumentException.class, () -> cache.get(null));
    }

    @Test
    void LRUPolicyTest() {
        Cache<String, Integer> cache =  new Cache<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.get("a");
        cache.get("b");
        cache.get("a");
        cache.put("d", 4);
        assertThrows(NotFoundException.class, () -> cache.get("b"));
        assertEquals(Integer.valueOf(4), cache.get("d"));
        cache.get("a");
        cache.get("d");
        cache.get("d");
        cache.put("e", 5);
        assertThrows(NotFoundException.class, () -> cache.get("a"));
        assertEquals(Integer.valueOf(5), cache.get("e"));
    }

    @Test
    void RandomReplacementPolicyTest() {
        Cache<String, Integer> cache =  new Cache<>(3, RR);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.get("a");
        cache.get("b");
        cache.get("a");
        cache.put("d", 4);
        assertEquals(Integer.valueOf(4), cache.get("d"));
    }

    @Test
    void FIFOPolicyTest() {
        Cache<String, Integer> cache =  new Cache<>(3, FIFO);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.get("b");
        cache.get("c");
        cache.get("c");
        cache.get("b");
        cache.get("a");
        cache.put("d", 4);
        assertThrows(NotFoundException.class, () -> cache.get("b"));
        assertEquals(Integer.valueOf(4), cache.get("d"));
        cache.get("a");
        cache.get("d");
        cache.get("d");
        cache.put("e", 5);
        assertThrows(NotFoundException.class, () -> cache.get("c"));
        assertEquals(Integer.valueOf(5), cache.get("e"));
    }
}
