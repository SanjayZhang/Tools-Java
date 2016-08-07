package com.sanjay.cache;

import java.util.Map;

/**
 * Created by zsj_09@hotmail.com on 2015/12/1.
 */
public class LFUCache<K, V> {

    private final CapFixedLinkedHashMap<K, V> mMap;

    private int hitCount;
    private int missCount;

    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else {
            //true: least-recently accessed
            this.mMap = new CapFixedLinkedHashMap<K, V>(capacity, 0.75f, true);
        }
    }


    public void resize(int capacity) {
        synchronized (this) {
            this.mMap.setMaxSize(capacity);
        }
        trimToSize(capacity);
    }

    public V get(K key) {
        V mapValue;
        synchronized (this) {
            mapValue = mMap.get(key);
            if (mapValue != null) {
                hitCount++;
                return mapValue;
            }
            missCount++;
        }

        //get default V
        V createValue = create(key);
        if (createValue == null) {
            return null;
        }

        synchronized (this) {
            mapValue = mMap.put(key, createValue);
        }

        return mapValue;
    }

    public V put(K key, V value) {
        V previous;
        synchronized (this) {
            previous = mMap.put(key, value);
        }
        return previous;
    }

    private void trimToSize(int maxSize) {
        while (true) {
            synchronized (this) {
                if (mMap.size() <= maxSize || mMap.isEmpty()) {
                    break;
                }

                Map.Entry<K, V> toEvict = mMap.entrySet().iterator().next();
                if (toEvict == null) {
                    break;
                }

                mMap.remove(toEvict.getKey());
            }
        }
    }

    public V remove(K key) {
        V previous;
        synchronized (this) {
            previous = mMap.remove(key);
        }
        return previous;
    }

    private V create(K key) {
        return null;
    }


    public final int getHitCount() {
        synchronized (this) {
            return hitCount;
        }
    }

    public final String toString() {
        synchronized (this) {
            int accesses = this.hitCount + this.missCount;
            int hitPercent = accesses == 0 ? 0 : 100 * this.hitCount / accesses;
            return String.format("LFUCache:maxsize=%d,hits=%d,misses=%d,hitRate=%d%%",
                    this.mMap.size(), this.hitCount, this.missCount, hitPercent);
        }
    }
}
