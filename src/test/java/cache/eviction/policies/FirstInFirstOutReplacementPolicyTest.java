package cache.eviction.policies;

import com.cache.eviction.policies.FirstInFirstOutReplacementPolicy;
import com.cache.exceptions.NotFoundException;
import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirstInFirstOutReplacementPolicyTest {
    private FirstInFirstOutReplacementPolicy<String, Integer> firstInFirstOutReplacementPolicy;
    Storage<String, Integer> storage;

    void setUpWithData() {
        storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        firstInFirstOutReplacementPolicy = new FirstInFirstOutReplacementPolicy<>(storage);
    }

    void setUpWithoutData() {
        storage = new HashMapBasedStorage<>(3);
        firstInFirstOutReplacementPolicy = new FirstInFirstOutReplacementPolicy<>(storage);
    }

    @Test
    void evictMethodTest_whenKeyAccessed() {
        setUpWithData();
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("a");
        firstInFirstOutReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
    }


    @Test
    void evictMethodTest_whenTwoKeyAccessed() {
        setUpWithData();
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("a");
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("c");
        firstInFirstOutReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
        assertEquals(Integer.valueOf(3), storage.get("c"));
        assertThrows(NotFoundException.class, () -> storage.get("a"));
    }

    @Test
    void evictMethodTest_whenSameKeyAccessed() {
        setUpWithData();
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("a");
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("a");
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("a");
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("c");
        firstInFirstOutReplacementPolicy.markKeyAsAccessed("a");
        firstInFirstOutReplacementPolicy.evict();
        firstInFirstOutReplacementPolicy.evict();
        assertEquals(Integer.valueOf(1), storage.getAllKeys().size());
        assertThrows(NotFoundException.class, () -> storage.get("a"));
        assertThrows(NotFoundException.class, () -> storage.get("c"));
    }

    @Test
    void evictMethodTest_withNoKeys() {
        setUpWithoutData();
        assertThrows(NotFoundException.class, firstInFirstOutReplacementPolicy::evict);
    }
}
