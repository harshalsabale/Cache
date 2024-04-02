package com.cache.eviction.policies;

import com.cache.eviction.EvictionPolicy;
import com.cache.storage.Storage;

import java.util.List;
import java.util.Random;

public class RandomReplacementPolicy<K,V> implements EvictionPolicy<K> {

    private final Storage<K,V> storage;

    private final Random random = new Random();

    public RandomReplacementPolicy(final Storage<K,V> storage) {
        this.storage = storage;
    }

    /**
     * @return
     */
    @Override
    public void evict() {
        List<K> keySet = storage.getAllKeys();
        if(keySet != null && !keySet.isEmpty()) {
            long keyNumber = random.nextLong(keySet.size());
            storage.remove(keySet.get(Math.toIntExact(keyNumber)));
        }
    }

    /**
     * @param key
     */
    @Override
    public void markKeyAsAccessed(K key) {
        throw new UnsupportedOperationException("Does not support this method");
    }
}
