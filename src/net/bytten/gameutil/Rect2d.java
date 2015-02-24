package net.bytten.gameutil;

public class Rect2d {
    
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
    
    public Vector2d topLeft() {
        return new Vector2d(left(), top());
    }
    public Vector2d topRight() {
        return new Vector2d(right(), top());
    }
    public Vector2d bottomLeft() {
        return new Vector2d(left(), bottom());
    }
    public Vector2d bottomRight() {
        return new Vector2d(right(), bottom());
    }
    public Vector2d midPoint() {
        return new Vector2d(x + w/2, y + h/2);
    }
    
    public Vector2d size() {
        return new Vector2d(w,h);
    }
    
    public Vector2d halfSize() {
        return new Vector2d(w/2, h/2);
    }
    
    public boolean overlaps(Rect2d other) {
        Vector2d mid = midPoint(),
            omid = other.midPoint();
        Vector2d half = halfSize(),
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
    
    public Rect2d translate(Vector2d other) {
        return translate(other.x, other.y);
    }
    
    public Rect2d translate(double dx, double dy) {
        return new Rect2d(x + dx, y + dy, w, h);
    }
    
    public boolean contains(Vector2d pos) {
        return pos.x >= x && pos.y >= y && pos.x < x+w && pos.y < y+h;
    }
}
