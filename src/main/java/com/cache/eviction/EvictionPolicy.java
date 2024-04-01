package com.cache.eviction;

public interface EvictionPolicy<K> {

    void evict();

    void markKeyAsAccessed(K key);

}
