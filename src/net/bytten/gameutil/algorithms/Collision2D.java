package net.bytten.gameutil.algorithms;

import java.util.*;
import net.bytten.gameutil.*;

public class Collision2D {

    // Returns null if there is no collision, otherwise it returns the
    // minimum translation vector to move a away from b.
    public static Vec2D collide(Collidable2D a, Collidable2D b) {
        Vec2D smallestAxis = null;
        double smallestOverlap = Double.MAX_VALUE;

        List<LineSegment> edges = new ArrayList<LineSegment>();
        edges.addAll(a.edges());
        edges.addAll(b.edges());
        for (LineSegment line: edges) {
            Vec2D axis = line.normal();
            Vec2D p1 = project(axis, a);
            Vec2D p2 = project(axis, b);
            Double overlap = optOverlap(p1, p2);
            if (overlap == null)
                return null;
            if (overlap < smallestOverlap) {
                smallestOverlap = overlap;
                smallestAxis = axis;
            }
        }
        assert smallestAxis != null;
        Vec2D mtv = smallestAxis.multiply(smallestOverlap);
        Vec2D a2b = b.center().subtract(a.center());
        if (mtv.dot(a2b) > 0)
            mtv = mtv.multiply(-1);
        return mtv;
    }

    private static Double optOverlap(Vec2D a, Vec2D b) {
        // Compute length that two 1-D line segments overlap by
        if (a.x < b.x) {
            if (a.y > b.x) {
                return a.y - b.x;
            }
        } else if (a.x < b.y) {
            return b.y - a.x;
        }
        return null;
    }

    private static Vec2D project(Vec2D axis, Collidable2D shape) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Vec2D vertex: shape.vertices()) {
            double point = vertex.dot(axis);
            if (point < min) min = point;
            if (point > max) max = point;
        }
        return new Vec2D(min, max);
    }

}
