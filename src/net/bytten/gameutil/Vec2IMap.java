package net.bytten.gameutil;

import java.util.TreeMap;

public class Vec2IMap<V> extends TreeMap<Vec2I, V> {
	private static final long serialVersionUID = 1L;

	public V get(int x, int y) {
        return get(new Vec2I(x,y));
    }
    
}
