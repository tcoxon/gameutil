package net.bytten.gameutil;

import java.io.*;

public class Vector2d implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public final double x, y;
    
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2d(Coords xy) {
        this(xy.x, xy.y);
    }

    public Vector2d add(Vector2d other) {
        return add(other.x, other.y);
    }
    public Vector2d add(double dx, double dy) {
        return new Vector2d(x + dx, y + dy);
    }
    
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }
    
    public Vector2d multiply(double m) {
        return new Vector2d(x * m, y * m);
    }
    
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    public Vector2d direction() {
        double mag = magnitude();
        return new Vector2d(x/mag, y/mag);
    }
    
    public Direction nearestCompassDirection() {
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
    
    public double squareDistanceTo(Vector2d other) {
        double dx = x - other.x,
            dy = y - other.y;
        return dx*dx + dy*dy;
    }
    
    public double distanceTo(Vector2d other) {
        return Math.sqrt(squareDistanceTo(other));
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector2d && !(other instanceof Rect2d)) {
            Vector2d ov = (Vector2d)other;
            return x == ov.x && y == ov.y;
        }
        return super.equals(other);
    }
    
    @Override
    public String toString() {
        return "Vector2d("+Double.toString(x)+", "+Double.toString(y)+")";
    }

    public double dot(Vector2d v) {
        return x*v.x + y*v.y;
    }
    
    public Coords floor() {
        return new Coords((int)Math.floor(x), (int)Math.floor(y));
    }
}
