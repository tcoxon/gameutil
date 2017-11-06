package net.bytten.gameutil;

import java.util.*;

public class Vec2ISet extends LinkedHashSet<Vec2I> {
    
    public Vec2ISet() {
    }
    
    public Vec2ISet(Collection<? extends Vec2I> c) {
        super(ensureOrderedCollection(c));
    }

    public boolean contains(int x, int y) {
        return contains(new Vec2I(x,y));
    }
    
    public void add(int x, int y) {
        add(new Vec2I(x, y));
    }
    
    public void remove(int x, int y) {
        remove(new Vec2I(x, y));
    }
    
    public Vec2I first() {
        for (Vec2I element: this) {
            return element;
        }
        throw new NoSuchElementException();
    }
    
    private static<T extends Vec2I> Collection<T> ensureOrderedCollection(Collection<T> c) {
        if (c instanceof SortedSet || c instanceof LinkedHashSet)
            return c;
        // No guaranteed order - so sort the collection:
        return new TreeSet<T>(c);
    }
}
