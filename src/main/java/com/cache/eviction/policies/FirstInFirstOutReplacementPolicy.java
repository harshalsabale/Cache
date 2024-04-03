package com.cache.eviction.policies;

import com.cache.eviction.EvictionPolicy;
import com.cache.storage.Storage;

import java.util.LinkedList;
import java.util.Queue;

public class FirstInFirstOutReplacementPolicy<K,V> implements EvictionPolicy<K> {

    private final Storage<K,V> storage;
    private Queue<K> queue;

    public FirstInFirstOutReplacementPolicy(final Storage<K, V> storage) {
        this.storage = storage;
        this.queue = new LinkedList<>();
    }

    /**
     *
     */
    @Override
    public void evict() {
        K key = queue.poll();
        storage.remove(key);
    }

    /**
     * @param key
     */
    @Override
    public void markKeyAsAccessed(K key) {
        if(!queue.contains(key)) {
            queue.offer(key);
        }
    }

}
