package net.bytten.gameutil;

import java.util.*;

public class Rect2dI {
    
    public final int x, y, w, h;

    public Rect2dI(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public int bottom() {
        return y+h;
    }
    public int right() {
        return x+w;
    }
    
    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x+w && y >= this.y && y < this.y+h;
    }
    
    public Coords getOffset() {
        return new Coords(x,y);
    }
    
    public Coords getSize() {
        return new Coords(w,h);
    }
    
    
    public static Rect2dI boundingBox(Set<Coords> xyset) {
        if (xyset.size() == 0) return new Rect2dI(0,0,0,0);
        int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE,
            maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
        for (net.bytten.gameutil.Coords xy: xyset) {
            if (xy.x < minx) minx = xy.x;
            if (xy.x > maxx) maxx = xy.x;
            if (xy.y < miny) miny = xy.y;
            if (xy.y > maxy) maxy = xy.y;
        }
        assert minx <= maxx && miny <= maxy;
        return new Rect2dI(minx, miny, maxx-minx+1, maxy-miny+1);
    }

}
