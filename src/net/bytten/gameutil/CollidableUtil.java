package net.bytten.gameutil;

import java.util.*;

public class CollidableUtil {

    public static Collidable2D scale(Collidable2D shape, double m) {
        if (shape instanceof Rect2D) {
            return ((Rect2D)shape).scale(m);
        } else {
            assert shape instanceof Poly2D;
            return ((Poly2D)shape).scale(m);
        }
    }

    public static Collidable2D translate(Collidable2D shape, Vec2D offset) {
        if (shape instanceof Rect2D) {
            return ((Rect2D)shape).translate(offset);
        } else {
            assert shape instanceof Poly2D;
            return ((Poly2D)shape).translate(offset);
        }
    }

}
