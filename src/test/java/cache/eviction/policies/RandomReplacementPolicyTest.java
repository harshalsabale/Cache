package cache.eviction.policies;

import com.cache.eviction.policies.RandomReplacementPolicy;
import com.cache.exceptions.NotFoundException;
import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomReplacementPolicyTest {
    private RandomReplacementPolicy<String, Integer> randomReplacementPolicy;
    Storage<String, Integer> storage;

    void setUpWithData() {
        storage = new HashMapBasedStorage<>(3);
        storage.add("a", 1);
        storage.add("b", 2);
        storage.add("c", 3);
        randomReplacementPolicy = new RandomReplacementPolicy<>(storage);
    }

    void setUpWithoutData() {
        storage = new HashMapBasedStorage<>(3);
        randomReplacementPolicy = new RandomReplacementPolicy<>(storage);
    }

    @Test
    void evictMethodTest_success() {
        setUpWithData();
        randomReplacementPolicy.evict();
        assertEquals(Integer.valueOf(2), storage.getAllKeys().size());
    }

    @Test
    void markKeyAsAccessedMethodTest_throwsUnsupportedOperationException() {
        setUpWithData();
        assertThrows(UnsupportedOperationException.class, () -> randomReplacementPolicy.markKeyAsAccessed("a"));
    }

    @Test
    void evictMethodTest_withNoKeys() {
        setUpWithoutData();
        assertThrows( NotFoundException.class, randomReplacementPolicy::evict);
    }

}
