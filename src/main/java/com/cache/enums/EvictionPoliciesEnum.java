package com.cache.enums;

public enum EvictionPoliciesEnum {

    LRU("Removes Least Recently Used key"),
    RR("Removes random Key");

    private final String description;
    EvictionPoliciesEnum(String description) {
        this.description = description;
    }
}
