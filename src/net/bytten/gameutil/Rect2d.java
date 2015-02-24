package net.bytten.gameutil;

import java.io.Serializable;

public class Rect2d implements Serializable {
    
    public final double x, y, w, h;
    
    public Rect2d(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public double top() {
        return y;
    }
    
    public double left() {
        return x;
    }
    
    public double right() {
        return x+w;
    }
    
    public double bottom() {
        return y+h;
    }
    
    public Vec2D topLeft() {
        return new Vec2D(left(), top());
    }
    public Vec2D topRight() {
        return new Vec2D(right(), top());
    }
    public Vec2D bottomLeft() {
        return new Vec2D(left(), bottom());
    }
    public Vec2D bottomRight() {
        return new Vec2D(right(), bottom());
    }
    public Vec2D midPoint() {
        return new Vec2D(x + w/2, y + h/2);
    }
    
    public Vec2D size() {
        return new Vec2D(w,h);
    }
    
    public Vec2D halfSize() {
        return new Vec2D(w/2, h/2);
    }
    
    public boolean overlaps(Rect2d other) {
        Vec2D mid = midPoint(),
            omid = other.midPoint();
        Vec2D half = halfSize(),
            ohalf = other.halfSize();
        return Math.abs(mid.x - omid.x) < half.x + ohalf.x &&
            Math.abs(mid.y - omid.y) < half.y + ohalf.y;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Rect2d) {
            Rect2d or = (Rect2d)other;
            return x == or.x && y == or.y && w == or.w && h == or.h;
        }
        return super.equals(other);
    }
    
    @Override
    public String toString() {
        return "Rect2d("+Double.toString(x)+", "+Double.toString(y)+", "+
            Double.toString(w)+", "+Double.toString(h)+")";
    }

    public Rect2d scale(double m) {
        return new Rect2d(x*m, y*m, w*m, h*m);
    }
    
    public Rect2d translate(Vec2D other) {
        return translate(other.x, other.y);
    }
    
    public Rect2d translate(double dx, double dy) {
        return new Rect2d(x + dx, y + dy, w, h);
    }
    
    public boolean contains(Vec2D pos) {
        return pos.x >= x && pos.y >= y && pos.x < x+w && pos.y < y+h;
    }
}
