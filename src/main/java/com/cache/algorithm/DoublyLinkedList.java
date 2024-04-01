package com.cache.algorithm;

public class DoublyLinkedList<K> {

    Node<K> startNode;
    Node<K> endNode;

    public DoublyLinkedList() {
        startNode = new Node<>(null);
        endNode = new Node<>(null);
        startNode.next = endNode;
        endNode.prev = startNode;
    }

    public Node<K> addKeyAtLast(K key) {
        Node<K> newNode = new Node<>(key);
        addNodeAtLast(newNode);
        return newNode;
    }

    public void addNodeAtLast(Node<K> node) {
        Node<K> tailPrev = endNode.prev;
        tailPrev.next = node;
        node.next = endNode;
        endNode.prev = node;
        node.prev = tailPrev;
    }

    public void detach(Node<K> node) {
        if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public boolean isEmpty() {
        return startNode.next == endNode;
    }

    public Node<K> getFirstNode() {
        return isEmpty() ? null : startNode.next;
    }

}
