package cache;

import com.cache.Cache;
import com.cache.RandomReplacementCache;
import org.junit.jupiter.api.Test;

import static com.cache.enums.EvictionPoliciesEnum.RR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomReplacementCacheTest {

    @Test
    void randomReplacementCacheTest_Initialization() {
        Cache<Integer, String> rrCache = new RandomReplacementCache<>(10);
        assertEquals(RR, rrCache.getEvictionPolicy());
        assertEquals(10, rrCache.getStorageCapacity());
    }

    @Test
    void randomReplacementCacheTest() {
        Cache<String, Integer> rrCache =  new RandomReplacementCache<>(3);
        rrCache.put("a", 1);
        rrCache.put("b", 2);
        rrCache.put("c", 3);
        rrCache.get("a");
        rrCache.get("b");
        rrCache.get("a");
        rrCache.put("d", 4);
        assertEquals(Integer.valueOf(4), rrCache.get("d"));
    }


}
