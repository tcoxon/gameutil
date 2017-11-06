package net.bytten.gameutil;

import java.util.*;

public class Vec2IMap<T> extends LinkedHashMap<Vec2I, T> {
    
    public Vec2IMap() {
    }
    
    public Vec2IMap(Map<? extends Vec2I, ? extends T> m) {
        super(m);
    }

    public T get(int x, int y) {
        return get(new Vec2I(x,y));
    }
	
	public void put(int x, int y, T value) {
		put(new Vec2I(x, y), value);
	}
	
	public void remove(int x, int y) {
		remove(new Vec2I(x, y));
	}
	
	public boolean containsKey(int x, int y) {
		return containsKey(new Vec2I(x, y));
	}
    
}
