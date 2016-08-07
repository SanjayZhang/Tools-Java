package com.sanjay.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zsj_09@hotmail.com on 2015/10/28.
 */
public class CapFixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private int maxSize;

    public CapFixedLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this.maxSize = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
