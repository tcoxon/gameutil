package net.bytten.gameutil;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SoftValueHashMap<K, V> implements Map<K, V> {

    // HashMap implementation where all values are stored in SoftReferences.
    // This is particularly useful for implementing caches because values will
    // be kept in memory as long as possible, but are guaranteed to removed
    // before the JVM runs out of memory.
    
    private class SoftValue extends SoftReference<V> {

        public final K key;
        
        public SoftValue(K key, V referent) {
            super(referent);
            this.key = key;
        }
        
    }
    
    private Map<K,SoftValue> map;
    private ReferenceQueue<V> queue;
    
    public SoftValueHashMap() {
        map = new HashMap<K,SoftValue>();
        queue = new ReferenceQueue<V>();
    }

    public SoftValueHashMap(Map<K,V> other) {
        this();
        putAll(other);
    }
    
    @SuppressWarnings("unchecked")
    private void cleanUp() {
        SoftValue ref;
        while ((ref = (SoftValue)queue.poll()) != null) {
            map.remove(ref.key);
        }
    }

    @Override
    public int size() {
        cleanUp();
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        cleanUp();
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        cleanUp();
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public V get(Object key) {
        cleanUp();
        SoftValue result = map.get(key);
        if (result == null)
            return null;
        return result.get();
    }

    @Override
    public V put(K key, V value) {
        cleanUp();
        map.put(key, new SoftValue(key, value));
        return value;
    }

    @Override
    public V remove(Object key) {
        cleanUp();
        SoftValue result = map.remove(key);
        if (result != null)
            return result.get();
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        cleanUp();
        for (Map.Entry<? extends K, ? extends V> entry: m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        cleanUp();
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        cleanUp();
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        cleanUp();
        List<V> values = new ArrayList<V>(map.size());
        for (SoftValue value: map.values()) {
            values.add(value.get());
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        cleanUp();
        Set<Map.Entry<K, V>> result = new HashSet<Map.Entry<K, V>>();
        for (K key: keySet()) {
            V value = get(key);
            result.add(new AbstractMap.SimpleImmutableEntry<K, V>(key, value));
        }
        return Collections.unmodifiableSet(result);
    }

}
