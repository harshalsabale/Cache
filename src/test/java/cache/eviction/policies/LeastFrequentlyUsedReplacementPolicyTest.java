package cache.eviction.policies;

import com.cache.eviction.policies.LeastFrequentlyUsedReplacementPolicy;
import com.cache.exceptions.NotFoundException;
import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeastFrequentlyUsedReplacementPolicyTest {

    private LeastFrequentlyUsedReplacementPolicy<String, Integer> leastFrequentlyUsedReplacementPolicy;
    private Storage<String, Integer> storage;

    void setUpWithData() {
        storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        leastFrequentlyUsedReplacementPolicy = new LeastFrequentlyUsedReplacementPolicy<>(storage);
    }

    void setUpWithoutData() {
        storage = new HashMapBasedStorage<>(3);
        leastFrequentlyUsedReplacementPolicy = new LeastFrequentlyUsedReplacementPolicy<>(storage);
    }

    @Test
    void evictMethodTest_success() {
        setUpWithData();
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
    }

    @Test
    void evictMethodTest_whenTwoKeyAccessed() {
        setUpWithData();
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("a"));
    }

    @Test
    void evictMethodTest_whenSameKeyAccessedTwice() {
        setUpWithData();
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertThrows(NotFoundException.class, () -> storage.get("c"));
    }

    @Test
    void evictMethodTest_whenAllKeyAccessed_1() {
        setUpWithData();
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("b");
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("b"));
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(1), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("a"));
    }


    @Test
    void evictMethodTest_whenAllKeyAccessed_2() {
        setUpWithData();
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("c");
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("b");
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("b"));
        leastFrequentlyUsedReplacementPolicy.markKeyAsAccessed("a");
        leastFrequentlyUsedReplacementPolicy.evict();
        assertEquals(Integer.valueOf(1), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(1), storage.get("a"));
        assertThrows(NotFoundException.class, () -> storage.get("c"));
    }

}
