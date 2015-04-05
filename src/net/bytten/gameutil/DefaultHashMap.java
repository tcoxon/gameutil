package net.bytten.gameutil;

import java.util.*;

public class DefaultHashMap<K,V> implements Map<K,V> {

    private Map<K,V> wrapped;
    private V defaultValue;
    
    public DefaultHashMap(Map<K,V> wrapped, V defaultValue) {
        this.wrapped = wrapped;
        this.defaultValue = defaultValue;
    }
    
    public V getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int size() {
        return wrapped.size();
    }

    public boolean isEmpty() {
        return wrapped.isEmpty();
    }

    public boolean containsKey(Object key) {
        return wrapped.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return wrapped.containsValue(value);
    }

    public V get(Object key) {
        if (containsKey(key)) {
            return wrapped.get(key);
        }
        return defaultValue;
    }

    public V put(K key, V value) {
        return wrapped.put(key, value);
    }

    public V remove(Object key) {
        return wrapped.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        wrapped.putAll(m);
    }

    public void clear() {
        wrapped.clear();
    }

    public Set<K> keySet() {
        return wrapped.keySet();
    }

    public Collection<V> values() {
        return wrapped.values();
    }

    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return wrapped.entrySet();
    }

    public boolean equals(Object o) {
        return wrapped.equals(o);
    }

    public int hashCode() {
        return wrapped.hashCode();
    }

}
