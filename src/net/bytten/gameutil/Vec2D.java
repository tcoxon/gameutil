package net.bytten.gameutil;

import java.io.*;

public class Vec2D implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public final double x, y;
    
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vec2D(Vec2I xy) {
        this(xy.x, xy.y);
    }

    public Vec2D add(Direction d) {
        return add(d.x, d.y);
    }
    
    public Vec2D add(Vec2D other) {
        return add(other.x, other.y);
    }
    
    public Vec2D add(double dx, double dy) {
        return new Vec2D(x + dx, y + dy);
    }
    
    public Vec2D subtract(Vec2D other) {
        return new Vec2D(x - other.x, y - other.y);
    }
    
    public Vec2D multiply(double m) {
        return new Vec2D(x * m, y * m);
    }
    
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    public Vec2D unit() {
        double mag = magnitude();
        return new Vec2D(x/mag, y/mag);
    }
    
    public Direction nearestCardinalDirection() {
        double absx = Math.abs(x),
               absy = Math.abs(y);
        if (absx > absy) {
            if (x < 0) {
                return Direction.W;
            } else {
                return Direction.E;
            }
        } else if (absy > absx) {
            if (y < 0) {
                return Direction.N;
            } else {
                return Direction.S;
            }
        } else if (absy == 0) {
            return Direction.O;
        } else {
            if (x < 0) {
                return Direction.W;
            } else {
                return Direction.E;
            }
        }
    }
    
    public double squareDistanceTo(Vec2D other) {
        double dx = x - other.x,
            dy = y - other.y;
        return dx*dx + dy*dy;
    }
    
    public double distanceTo(Vec2D other) {
        return Math.sqrt(squareDistanceTo(other));
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Vec2D) {
            Vec2D ov = (Vec2D)other;
            return x == ov.x && y == ov.y;
        }
        return super.equals(other);
    }
    
    @Override
    public String toString() {
        return "Vec2D("+Double.toString(x)+", "+Double.toString(y)+")";
    }

    public double dot(Vec2D v) {
        return x*v.x + y*v.y;
    }
    
    public Vec2I floor() {
        return new Vec2I((int)Math.floor(x), (int)Math.floor(y));
    }
    
    public Vec2I ceil() {
        return new Vec2I((int)Math.ceil(x), (int)Math.ceil(y));
    }
    
}
