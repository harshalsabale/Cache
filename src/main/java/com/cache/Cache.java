package com.cache;

import com.cache.eviction.EvictionPolicy;
import com.cache.eviction.policies.LeastRecentlyUsedReplacementPolicy;
import com.cache.exceptions.StorageFullException;
import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;

public class Cache<K,V> {

    private final Storage<K,V> storage;
    private final EvictionPolicy<K> evictionPolicy;
    private final int storageCapacity;

    public Cache(final int capacity) {
        this.storage =  new HashMapBasedStorage<>(capacity);
        this.evictionPolicy = new LeastRecentlyUsedReplacementPolicy<>(storage);
        this.storageCapacity = capacity;
    }

    public void put(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException("Key and/or value can't be null");
        }
        try {
            storage.add(key, value);
        } catch (StorageFullException e) {
            evictionPolicy.evict();
            put(key, value);
        }
    }

    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }
        V value = storage.get(key);
        try {
            evictionPolicy.markKeyAsAccessed(key);
        } catch (UnsupportedOperationException e) {
            return value;
        }
        return value;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }


}
