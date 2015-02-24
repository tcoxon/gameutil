package net.bytten.gameutil;

import java.io.Serializable;
import java.util.*;

public class Rect2I implements Serializable {
    
    public final int x, y, w, h;

    public Rect2I(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public static Rect2I fromExtremes(int x, int y, int right, int bottom) {
        return new Rect2I(x, y, right-x, bottom-y);
    }
    
    public static Rect2I fromExtremes(Vec2I min, Vec2I max) {
        return fromExtremes(min.x, min.y, max.x, max.y);
    }
    
    public static Rect2I fromExtremesInclusive(Vec2I min, Vec2I max) {
        return fromExtremes(min, max.add(1,1));
    }
    
    public int left() {
        return x;
    }
    public int top() {
        return y;
    }
    public int width() {
        return w;
    }
    public int height() {
        return h;
    }
    // Bottom and right form an exclusive range.
    public int bottom() {
        return y+h;
    }
    public int right() {
        return x+w;
    }
    
    public Vec2I topLeft() {
        return getOffset();
    }
    
    // Note that this is *exclusive*
    public Vec2I bottomRight() {
        return getOffset().add(getSize());
    }
    
    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x+w && y >= this.y && y < this.y+h;
    }
    
    public boolean contains(Vec2I pos) {
        return contains(pos.x, pos.y);
    }
    
    public Vec2I getOffset() {
        return new Vec2I(x,y);
    }
    
    public Vec2I getSize() {
        return new Vec2I(w,h);
    }
    
    public static Rect2I boundingBox(Set<Vec2I> xyset) {
        if (xyset.size() == 0) return new Rect2I(0,0,0,0);
        int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE,
            maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
        for (net.bytten.gameutil.Vec2I xy: xyset) {
            if (xy.x < minx) minx = xy.x;
            if (xy.x > maxx) maxx = xy.x;
            if (xy.y < miny) miny = xy.y;
            if (xy.y > maxy) maxy = xy.y;
        }
        assert minx <= maxx && miny <= maxy;
        return new Rect2I(minx, miny, maxx-minx+1, maxy-miny+1);
    }

}
