package net.bytten.gameutil;

import java.io.*;
import java.util.*;

public class Poly2D implements Serializable, Collidable2D {

    private List<Vec2D> vertices;
    private Vec2D center;

    public Poly2D(List<Vec2D> vertices) {
        this.vertices = vertices;

        Vec2D total = new Vec2D(0,0);
        for (Vec2D vertex: vertices) {
            total = total.add(vertex);
        }
        this.center = total.multiply(1.0/vertices.size());
    }

    @Override
    public List<LineSegment> edges() {
        List<LineSegment> result = new ArrayList<LineSegment>();
        if (vertices.size() < 2)
            return result;

        Vec2D prev = vertices.get(vertices.size()-1);
        for (Vec2D vertex: vertices) {
            result.add(new LineSegment(prev, vertex));
            prev = vertex;
        }
        return result;
    }

    @Override
    public List<Vec2D> vertices() {
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public Vec2D center() {
        return center;
    }

    public Poly2D translate(final Vec2D v) {
        return new Poly2D(CollectionUtil.mapped(vertices, new UnaryFunction<Vec2D, Vec2D>() {
            public Vec2D run(Vec2D arg) {
                return arg.add(v);
            }
        }));
    }
}
