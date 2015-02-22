package net.bytten.gameutil.algorithms;

import java.util.Set;
import java.util.Stack;

import net.bytten.gameutil.Coords;
import net.bytten.gameutil.Rect2dI;

public class MaximalRectangle {
    
    // Maximal Rectangle algorithm based on this article:
    // http://www.drdobbs.com/database/the-maximal-rectangle-problem/184410529
    
    final Coords offs;
    final int[][] map;
    final int[] cache;
    final int M, N;

    public MaximalRectangle(Set<Coords> ones, Rect2dI boundingBox) {
        offs = boundingBox.topLeft();
        N = boundingBox.width();
        M = boundingBox.height();
        map = new int[N][M];
        for (Coords one: ones) {
            map[one.x-offs.x][one.y-offs.y] = 1;
        }
        cache = new int[M+1];
    }
    
    private void updateCache(int x) {
        for (int y = 0; y < M; ++y) {
            if (map[x][y] != 0)
                cache[y] = cache[y]+1;
            else
                cache[y] = 0;
        }
    }
    
    public Rect2dI solve() {
        Coords bestLL = null, bestUR = null;
        int bestArea = -1;
        
        Stack<Coords> stack = new Stack<Coords>();
        
        for (int x = N-1; x >= 0; --x) {
            updateCache(x);
            int width = 0; // width of widest opened rectangle
            for (int y = 0; y <= M; ++y) {
                if (cache[y] > width) { // Opening new rectangle(s)?
                    stack.push(new Coords(y, width));
                    width = cache[y];
                }
                if (cache[y] < width) {
                    int y0,w0;
                    while (true) {
                        Coords y0w0 = stack.pop();
                        y0 = y0w0.x; w0 = y0w0.y;
                        if (width * (y-y0) > bestArea) {
                            bestLL = new Coords(x, y0);
                            bestUR = new Coords(x+width-1, y-1);
                            bestArea = width * (y-y0);
                        }
                        width = w0;
                        if (cache[y] >= width)
                            break;
                    }
                    width = cache[y];
                    if (width != 0) // Popped an active "opening"?
                        stack.push(new Coords(y0, w0));
                }
            }
        }
        assert bestLL != null && bestUR != null && bestArea >= 0;
        return Rect2dI.fromExtremesInclusive(bestLL.add(offs), bestUR.add(offs));
    }

}
