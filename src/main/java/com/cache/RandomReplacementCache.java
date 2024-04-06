package com.cache;

import static com.cache.enums.EvictionPoliciesEnum.RR;

public class RandomReplacementCache<K,V> extends Cache<K,V> {

    public RandomReplacementCache(int capacity) {
        super(capacity, RR);
    }

}
