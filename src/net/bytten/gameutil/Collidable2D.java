package net.bytten.gameutil;

import java.util.*;

public interface Collidable2D {

    public List<LineSegment> edges();
    public List<Vec2D> vertices();
    public Vec2D center();

}
