package com.cache;

import static com.cache.enums.EvictionPoliciesEnum.LRU;

public class LeastRecentlyUsedCache<K,V> extends Cache<K,V>{

    public LeastRecentlyUsedCache(int capacity) {
        super(capacity, LRU);
    }
}
