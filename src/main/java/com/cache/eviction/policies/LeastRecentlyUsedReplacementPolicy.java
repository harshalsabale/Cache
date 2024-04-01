package com.cache.eviction.policies;

import com.cache.algorithm.DoublyLinkedList;
import com.cache.algorithm.Node;
import com.cache.eviction.EvictionPolicy;
import com.cache.exceptions.NotFoundException;
import com.cache.storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class LeastRecentlyUsedReplacementPolicy<K,V> implements EvictionPolicy<K> {

    private final Storage<K,V> storage;
    private final DoublyLinkedList<K> doublyLinkedList;
    private final Map<K, Node<K>> mapper;

    public LeastRecentlyUsedReplacementPolicy(final Storage<K,V> storage) {
        this.storage = storage;
        this.doublyLinkedList = new DoublyLinkedList<>();
        this.mapper = new HashMap<>();
    }

    /**
     *
     */
    @Override
    public void evict() {
        Node<K> firstKey = doublyLinkedList.getFirstNode();
        if(firstKey == null) {
            throw new NotFoundException("Key Not Found!");
        }
        doublyLinkedList.detach(firstKey);
        storage.remove(firstKey.getKey());
    }

    /**
     * @param key
     */
    @Override
    public void markKeyAsAccessed(K key) {
        if(key == null) {
            throw new NotFoundException("Key cannot be null!!!");
        }
        if(mapper.containsKey(key)) {
            doublyLinkedList.detach(mapper.get(key));
            doublyLinkedList.addNodeAtLast(mapper.get(key));
        } else {
            Node<K> node = doublyLinkedList.addKeyAtLast(key);
            mapper.put(key, node);
        }
    }
}
