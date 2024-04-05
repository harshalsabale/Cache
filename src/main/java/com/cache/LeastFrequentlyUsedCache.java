package com.cache;

import static com.cache.enums.EvictionPoliciesEnum.LFU;

public class LeastFrequentlyUsedCache<K,V> extends Cache<K,V> {

    public LeastFrequentlyUsedCache(int capacity) {
        super(capacity, LFU);
    }
}
