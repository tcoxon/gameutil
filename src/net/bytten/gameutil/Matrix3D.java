package net.bytten.gameutil;

import java.util.*;

public class Matrix3D {

    public static final Matrix3D ZERO = new Matrix3D(0, 0, 0,
                                                     0, 0, 0,
                                                     0, 0, 0);
    public static final Matrix3D IDENTITY = new Matrix3D(1, 0, 0,
                                                         0, 1, 0,
                                                         0, 0, 1);

    private static final int NUM_ELEMS = 9;
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;

    private double[] elements;

    public Matrix3D(double a, double b, double c, double d, double e, double f, double g, double h, double i) {
        this(new double[]{a, b, c, d, e, f, g, h, i});
    }

    private Matrix3D(double[] elements) {
        assert elements.length == NUM_ELEMS;
        this.elements = elements;
    }

    public Matrix3D() {
        this(ZERO.elements);
    }

    public Matrix3D(Matrix3D other) {
        this(Arrays.copyOf(other.elements, NUM_ELEMS));
    }

    public double get(int i) {
        if (i >= NUM_ELEMS || i < 0)
            throw new IllegalArgumentException(Integer.toString(i));
        return elements[i];
    }

    public double get(int x, int y) {
        return get(x + WIDTH * y);
    }

    public Matrix3D set(int i, double value) {
        Matrix3D result = new Matrix3D(this);
        result.elements[i] = value;
        return result;
    }

    public Matrix3D set(int x, int y, double value) {
        return set(x + WIDTH * y, value);
    }

    public Matrix3D add(Matrix3D other) {
        Matrix3D result = new Matrix3D(this);
        for (int i = 0; i < NUM_ELEMS; ++i) {
            result.elements[i] = get(i) + other.get(i);
        }
        return result;
    }

    public Matrix3D multiply(double m) {
        Matrix3D result = new Matrix3D(this);
        for (int i = 0; i < NUM_ELEMS; ++i) {
            result.elements[i] = get(i) * m;
        }
        return result;
    }

    public Matrix3D multiply(Matrix3D other) {
        Matrix3D result = new Matrix3D(this);
        for (int x = 0; x < WIDTH; ++x) {
            for (int y = 0; y < HEIGHT; ++y) {
                double sum = 0.0;
                for (int k = 0; k < WIDTH; ++k) {
                    sum += result.get(x, k) * other.get(k, y);
                }
                result.elements[x + y * WIDTH] = sum;
            }
        }
        return result;
    }

    public Vec2D multiply(Vec2D vec) {
        // vec treated as 2D homogenous coordinates (x, y, w) where w = 1
        // x' and y' are returned as a new Vec2D and w' is discarded.
        double x = vec.x, y = vec.y, w = 1;
        return new Vec2D(
            x * get(0, 0) + y * get(0, 1) + w * get(0, 2),
            x * get(1, 0) + y * get(1, 1) + w * get(1, 2));
    }

    public static Matrix3D rotate2D(double radians) {
        // Clockwise rotation
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);
        return new Matrix3D(cos, sin, 0, -sin, cos, 0, 0, 0, 1);
    }

    public static Matrix3D scale2D(double scaleX, double scaleY) {
        return new Matrix3D(scaleX, 0, 0, 0, scaleY, 0, 0, 0, 1);
    }

    public static Matrix3D scale2D(double scale) {
        return scale2D(scale, scale);
    }

    public static Matrix3D translate2D(double dx, double dy) {
        return new Matrix3D(1, 0, 0,
                            0, 1, 0,
                            dx, dy, 1);
    }

    public static Matrix3D translate2D(Vec2D delta) {
        return translate2D(delta.x, delta.y);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Matrix3D)) return false;
        return Arrays.equals(elements, ((Matrix3D)other).elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements) ^ 720357230;
    }

    @Override
    public String toString() {
        Object[] args = new Object[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            args[i] = elements[i];
        }
        return String.format("Matrix3D(%s, %s, %s, %s, %s, %s, %s, %s, %s)", args);
    }

}
