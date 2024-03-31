package com.cache.storage;

import java.util.List;
import java.util.Set;

public interface Storage<K,V> {

    void add(K key,V value);

    V get(K key);

    void remove(K key);

    List<K> getAllKeys();
}
