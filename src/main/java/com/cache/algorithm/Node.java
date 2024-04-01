package com.cache.algorithm;

public class Node<K> {
    K key;
    Node<K> prev;
    Node<K> next;

    public Node(K key) {
        this.key = key;
        this.next = null;
        this.prev = null;
    }

    public K getKey() {
        return key;
    }
}
