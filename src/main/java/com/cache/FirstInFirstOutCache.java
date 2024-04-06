package com.cache;

import static com.cache.enums.EvictionPoliciesEnum.FIFO;

public class FirstInFirstOutCache<K,V> extends Cache<K,V> {

    public FirstInFirstOutCache(int capacity) {
        super(capacity, FIFO);
    }
}
