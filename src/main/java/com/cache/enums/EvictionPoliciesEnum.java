package com.cache.enums;

public enum EvictionPoliciesEnum {

    LRU("Removes Least Recently Used key"),
    RR("Removes random Key"),
    FIFO("Removes key on First-In-First-Out Basis"),
    LFU("Removes Least Frequently Used key");

    private final String description;
    EvictionPoliciesEnum(String description) {
        this.description = description;
    }
}
