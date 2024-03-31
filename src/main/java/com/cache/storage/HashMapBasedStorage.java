package com.cache.storage;

import com.cache.exceptions.NotFoundException;
import com.cache.exceptions.StorageFullException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapBasedStorage<K,V> implements Storage<K,V>{

    private final Map<K,V> hashMapStorage;
    private final int storageCapacity;


    public HashMapBasedStorage(final int capacity) {
        this.hashMapStorage = new HashMap<>();
        this.storageCapacity = capacity;
    }

    /**
     * This method store the key and value into the storage
     *
     * @param key
     * @param value
     */
    @Override
    public void add(K key, V value) {
        if(hashMapStorage.size() >= storageCapacity && !hashMapStorage.containsKey(key)) {
            throw new StorageFullException("Storage is full!");
        }
        hashMapStorage.put(key, value);
    }

    /**
     * This method retrieve the value from the storage using key
     *
     * @param key
     * @return V
     */
    @Override
    public V get(K key) {
        if(!hashMapStorage.containsKey(key)) throw new NotFoundException("Key Not Found!");
        return hashMapStorage.get(key);
    }

    /**
     * This method deletes the key and value from the storage
     *
     * @param key
     */
    @Override
    public void remove(K key) {
        if(!hashMapStorage.containsKey(key)) throw new NotFoundException("Key Not Found!");
        hashMapStorage.remove(key);
    }

    /**
     * This method retrieves all the keys from the storage
     *
     * @return Set<K>
     */
    @Override
    public List<K> getAllKeys() {
        if(hashMapStorage.isEmpty()) throw new NotFoundException("Keys Not Found!");
        return hashMapStorage.keySet().stream().toList();
    }

}
