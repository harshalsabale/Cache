package com.cache.eviction.policies;

import com.cache.eviction.EvictionPolicy;
import com.cache.storage.Storage;

import java.util.*;

public class LeastFrequentlyUsedReplacementPolicy<K,V> implements EvictionPolicy<K> {

    private final Storage<K,V> storage;
    private final Map<Integer, Set<K>> frequencyMap;
    private final Map<K, Integer> frequencyMapByKey;
    private int minFrequency;

    public LeastFrequentlyUsedReplacementPolicy(final Storage<K,V> storage) {
        this.storage = storage;
        this.frequencyMap = new HashMap<>();
        this.frequencyMapByKey = new HashMap<>();
        this.minFrequency = 1;
    }

    /**
     *
     */
    @Override
    public void evict() {
        Set<K> minFreqKeySet = frequencyMap.get(minFrequency);
        if(minFreqKeySet != null && !minFreqKeySet.isEmpty()) {
            K keyToBeEvicted = minFreqKeySet.iterator().next();
            storage.remove(keyToBeEvicted);
            minFreqKeySet.remove(keyToBeEvicted);
            frequencyMapByKey.remove(keyToBeEvicted);
            if(!minFreqKeySet.isEmpty()) {
                frequencyMap.put(minFrequency, minFreqKeySet);
            } else {
                frequencyMap.remove(minFrequency);
            }
            if(!frequencyMap.isEmpty()) {
                minFrequency = Collections.min(frequencyMap.keySet());
            }
        }
    }

    /**
     * @param key
     */
    @Override
    public void markKeyAsAccessed(K key) {
        Integer currentFrequency = frequencyMapByKey.getOrDefault(key, 0);
        frequencyMap.computeIfPresent(currentFrequency, (k,v)-> {
            v.remove(key);
            return v.isEmpty() ? null : v;
        });
        currentFrequency++;
        frequencyMap.computeIfAbsent(currentFrequency, k -> new LinkedHashSet<>()).add(key);

        if (currentFrequency-1 == minFrequency && frequencyMap.get(minFrequency) == null) {
            minFrequency++;
        } else if(currentFrequency-1 < minFrequency) {
            minFrequency = currentFrequency;
        }
        frequencyMapByKey.put(key, currentFrequency);
    }
}
