package com.cache;

import com.cache.storage.HashMapBasedStorage;
import com.cache.storage.Storage;

public class Cache<K,V> {

    private final Storage<K,V> storage;
    private final int storageCapacity;

    public Cache(final int capacity) {
        this.storage =  new HashMapBasedStorage<>(capacity);
        this.storageCapacity = capacity;
    }

    public void put(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException("Key and/or value can't be null");
        }
        storage.add(key, value);
    }

    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }
        return storage.get(key);
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }


}
