package net.bytten.gameutil;

import java.io.Serializable;
import java.util.*;

public class Rect2I implements Serializable {

    private static final long serialVersionUID = 1L;
    
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
        return new Vec2I(x,y);
    }
    // Note that these are *exclusive*
    public Vec2I topRight() {
        return new Vec2I(right(), top());
    }
    public Vec2I bottomLeft() {
        return new Vec2I(left(), bottom());
    }
    public Vec2I bottomRight() {
        return topLeft().add(size());
    }
    
    public Vec2D midPoint() {
        return new Vec2D(x + w/2.0, y + h/2.0);
    }
    
    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x+w && y >= this.y && y < this.y+h;
    }
    
    public boolean contains(Vec2I pos) {
        return contains(pos.x, pos.y);
    }
    
    public Vec2I min() {
        return topLeft();
    }
    
    public Vec2I max() {
        return bottomRight();
    }
    
    public Vec2I size() {
        return new Vec2I(w,h);
    }
    
    public Vec2D halfSize() {
        return new Vec2D(w/2.0, h/2.0);
    }
    
    public boolean overlaps(Rect2I other) {
        Vec2D mid = midPoint(),
            omid = other.midPoint();
        Vec2D half = halfSize(),
            ohalf = other.halfSize();
        return Math.abs(mid.x - omid.x) < half.x + ohalf.x &&
            Math.abs(mid.y - omid.y) < half.y + ohalf.y;
    }
    
    public Rect2D toRect2D() {
        return new Rect2D(x, y, w, h);
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

    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Rect2I) {
            Rect2I or = (Rect2I)other;
            return x == or.x && y == or.y && w == or.w && h == or.h;
        }
        return super.equals(other);
    }

    @Override
    public String toString() {
        return "Rect2I("+Integer.toString(x)+", "+Integer.toString(y)+", "+
                Integer.toString(w)+", "+Integer.toString(h)+")";
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{ x, y, w, h });
    }
    
    public Rect2I scale(int m) {
        return new Rect2I(x*m, y*m, w*m, h*m);
    }
    
    public Rect2D scale(double m) {
        return new Rect2D(x*m, y*m, w*m, h*m);
    }

}
