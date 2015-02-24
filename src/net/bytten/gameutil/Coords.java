package net.bytten.gameutil;

import java.io.Serializable;

public class Coords implements Comparable<Coords>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public final int x, y;
    
    /**
     * Create coordinates at the given X and Y position.
     * 
     * @param x the position along the left-right dimension
     * @param y the position along the top-bottom dimension
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the coordinates of the next space in the given direction
     * 
     * @param d the direction
     */
    public Coords add(Direction d) {
        return add(d.x,d.y);
    }
    
    public Coords add(Coords xy) {
        return new Coords(x + xy.x, y + xy.y);
    }
    
    public Coords add(int dx, int dy) {
        return new Coords(x + dx, y + dy);
    }
    
    public Coords subtract(Coords other) {
        return new Coords(x-other.x, y-other.y);
    }
    
    public Coords multiply(int m) {
        return new Coords(m*x, m*y);
    }
    
    public double magnitude() {
        return toVector2d().magnitude();
    }
    
    public Vector2d unit() {
        return toVector2d().unit();
    }
    
    public Direction nearestCardinalDirection() {
        return toVector2d().nearestCardinalDirection();
    }
    
    public double squareDistanceTo(Coords other) {
        return toVector2d().squareDistanceTo(other.toVector2d());
    }
    
    public double distanceTo(Coords other) {
        return toVector2d().distanceTo(other.toVector2d());
    }
    
    @Override
    public boolean equals(Object other) {
         if (other instanceof Coords) {
             Coords o = (Coords)other;
             return this.x == o.x && this.y == o.y;
         } else {
             return super.equals(other);
         }
    }

    @Override
    public int compareTo(Coords other) {
        int d = this.x - other.x;
        if (d == 0) {
            d = this.y - other.y;
        }
        return d;
    }
    
    /**
     * Determines whether this Coords and another Coords are next to each other.
     * 
     * @param other the other Coords
     * @return whether they are adjacent
     */
    public boolean isAdjacent(Coords other) {
        int dx = Math.abs(x - other.x),
            dy = Math.abs(y - other.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    /**
     * Gets the direction from this Coords to another Coords.
     * 
     * @param other the other Coords
     * @return the direction the other Coords is in
     * @throws AssertionError if the direction to the other Coords cannot be
     *                          described with compass directions, e.g. if it's
     *                          diagonal
     */
    public Direction getDirectionTo(Coords other) {
        int dx = x - other.x,
            dy = y - other.y;
        assert dx == 0 || dy == 0;
        if (dx < 0) return Direction.E;
        if (dx > 0) return Direction.W;
        if (dy < 0) return Direction.S;
        assert dy > 0;
        return Direction.N;
    }
    
    public double distance(Coords other) {
        int dx = x - other.x,
            dy = y - other.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    public Vector2d toVector2d() {
        return new Vector2d(this);
    }
    
    public String toString() {
        return x+","+y;
    }
    
    public int dot(Coords other) {
        return x * other.x + y * other.y;
    }
}
