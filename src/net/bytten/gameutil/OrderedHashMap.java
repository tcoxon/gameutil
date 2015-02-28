package net.bytten.gameutil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderedHashMap<K, V> implements Map<K, V> {
    
    // A wrapper around HashMap that preserves the order you added the entries
    // in when iterating over the map.

    private HashMap<K,V> map = new HashMap<K,V>();
    private List<Map.Entry<K,V>> entryList = new ArrayList<Map.Entry<K,V>>();
    private List<K> keyList = new ArrayList<K>();
    private List<V> valueList = new ArrayList<V>();
    
    public OrderedHashMap() {
    }
    
    public OrderedHashMap(Map<? extends K, ? extends V> other) {
        putAll(other);
    }
    
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        remove(key);
        keyList.add(key);
        valueList.add(value);
        entryList.add(new AbstractMap.SimpleImmutableEntry<K, V>(key, value));
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        int index = keyList.indexOf(key);
        if (index != -1) {
            keyList.remove(index);
            valueList.remove(index);
            entryList.remove(index);
        }
        return map.remove(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m instanceof OrderedHashMap) {
            for (Map.Entry<K,V> entry: ((OrderedHashMap<K,V>)m).entryList()) {
                put(entry.getKey(), entry.getValue());
            }
        } else {
            for (Map.Entry<? extends K,? extends V> entry: m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void clear() {
        map.clear();
        entryList.clear();
        keyList.clear();
        valueList.clear();
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableList(valueList);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
    }
    
    public List<K> keyList() {
        return Collections.unmodifiableList(keyList);
    }

    public List<V> valueList() {
        return Collections.unmodifiableList(valueList);
    }

    public List<Map.Entry<K, V>> entryList() {
        return Collections.unmodifiableList(entryList);
    }
    
}
