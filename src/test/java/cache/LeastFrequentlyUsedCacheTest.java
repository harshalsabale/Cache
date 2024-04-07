package cache;

import com.cache.Cache;
import com.cache.LeastFrequentlyUsedCache;
import com.cache.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static com.cache.enums.EvictionPoliciesEnum.LFU;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeastFrequentlyUsedCacheTest {

    @Test
    void leastFrequentlyUsedCacheTest_Initialization() {
        Cache<Integer, String> lfuCache = new LeastFrequentlyUsedCache<>(10);
        assertEquals(LFU, lfuCache.getEvictionPolicy());
        assertEquals(10, lfuCache.getStorageCapacity());
    }

    @Test
    void leastFrequentlyUsedCacheTest() {
        Cache<String, Integer> lfuCache =  new LeastFrequentlyUsedCache<>(3);
        lfuCache.put("a", 1);
        lfuCache.put("b", 2);
        lfuCache.put("c", 3);
        lfuCache.get("c");
        lfuCache.get("c");
        lfuCache.get("b");
        lfuCache.get("a");
        lfuCache.put("d", 4);
        assertThrows(NotFoundException.class, () -> lfuCache.get("b"));
        assertEquals(Integer.valueOf(4), lfuCache.get("d"));
        lfuCache.get("a");
        lfuCache.get("d");
        lfuCache.get("d");
        lfuCache.put("e", 5);
        assertThrows(NotFoundException.class, () -> lfuCache.get("c"));
        assertEquals(Integer.valueOf(5), lfuCache.get("e"));
    }
}
