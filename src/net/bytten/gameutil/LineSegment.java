package net.bytten.gameutil;

import java.io.*;

public class LineSegment implements Serializable {

    private Vec2D start, end;

    public LineSegment(Vec2D start, Vec2D end) {
        this.start = start;
        this.end = end;
    }

    public LineSegment(LineSegment other) {
        this(other.start, other.end);
    }

    public Vec2D start() {
        return start;
    }

    public Vec2D end() {
        return end;
    }

    public LineSegment setStart(Vec2D start) {
        return new LineSegment(start, end);
    }

    public LineSegment setEnd(Vec2D end) {
        return new LineSegment(start, end);
    }

    public Vec2D direction() {
        return end.subtract(start).unit();
    }

    public Vec2D normal() {
       return direction().normal();
    }

}
