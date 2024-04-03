package com.cache.eviction;

import com.cache.enums.EvictionPoliciesEnum;
import com.cache.eviction.policies.FirstInFirstOutReplacementPolicy;
import com.cache.eviction.policies.LeastFrequentlyUsedReplacementPolicy;
import com.cache.eviction.policies.LeastRecentlyUsedReplacementPolicy;
import com.cache.eviction.policies.RandomReplacementPolicy;
import com.cache.storage.Storage;


public class EvictionPolicyFactory<K,V>{

    public EvictionPolicy<K> select(final EvictionPoliciesEnum selectedPolicy, final Storage<K, V> storage) {
        return switch (selectedPolicy) {
            case RR -> new RandomReplacementPolicy<>(storage);
            case FIFO -> new FirstInFirstOutReplacementPolicy<>(storage);
            case LFU -> new LeastFrequentlyUsedReplacementPolicy<>(storage);
            default -> new LeastRecentlyUsedReplacementPolicy<>(storage);
        };
    }
}
