package cache.eviction.policies;

import com.cache.eviction.policies.LeastRecentlyUsedReplacementPolicy;
import com.cache.exceptions.NotFoundException;
import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeastRecentlyUsedReplacementPolicyTest {

    private LeastRecentlyUsedReplacementPolicy<String, Integer> leastRecentlyUsedReplacementPolicy;
    Storage<String, Integer> storage;

    void setUpWithData() {
        storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        leastRecentlyUsedReplacementPolicy = new LeastRecentlyUsedReplacementPolicy<>(storage);
    }

    void setUpWithoutData() {
        storage = new HashMapBasedStorage<>(3);
        leastRecentlyUsedReplacementPolicy = new LeastRecentlyUsedReplacementPolicy<>(storage);
    }

    @Test
    void evictMethodTest_success() {
        setUpWithData();
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastRecentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
    }

    @Test
    void evictMethodTest_whenTwoKeyAccessed() {
        setUpWithData();
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastRecentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("a"));
    }

    @Test
    void evictMethodTest_whenSameKeyAccessedTwice() {
        setUpWithData();
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastRecentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertThrows(NotFoundException.class, () -> storage.get("c"));
    }

    @Test
    void evictMethodTest_whenAllKeyAccessed() {
        setUpWithData();
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("b");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastRecentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastRecentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("b"));
        leastRecentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(1), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertThrows(NotFoundException.class, () -> storage.get("c"));
    }

    @Test
    void evictMethodTest_withNoKeys() {
        setUpWithoutData();
        assertThrows(NotFoundException.class, leastRecentlyUsedReplacementPolicy::evict);
    }
}
