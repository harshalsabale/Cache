package cache;

import com.cache.Cache;
import com.cache.LeastRecentlyUsedCache;
import com.cache.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import static com.cache.enums.EvictionPoliciesEnum.LRU;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeastRecentlyUsedCacheTest {

    @Test
    void leastRecentlyUsedCacheTest_Initialization() {
        Cache<Integer, String> lruCache = new LeastRecentlyUsedCache<>(10);
        assertEquals(LRU, lruCache.getEvictionPolicy());
        assertEquals(10, lruCache.getStorageCapacity());
    }

    @Test
    void LRUPolicyTest() {
        Cache<String, Integer> lruCache = new LeastRecentlyUsedCache<>(3);
        lruCache.put("a", 1);
        lruCache.put("b", 2);
        lruCache.put("c", 3);
        lruCache.get("a");
        lruCache.get("b");
        lruCache.get("a");
        lruCache.put("d", 4);
        assertThrows(NotFoundException.class, () -> lruCache.get("b"));
        assertEquals(Integer.valueOf(4), lruCache.get("d"));
        lruCache.get("a");
        lruCache.get("d");
        lruCache.get("d");
        lruCache.put("e", 5);
        assertThrows(NotFoundException.class, () -> lruCache.get("a"));
        assertEquals(Integer.valueOf(5), lruCache.get("e"));
    }


}
