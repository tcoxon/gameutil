package net.bytten.gameutil;

public enum Direction {

    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    O(0, 0),
    
    ;
    
    public static final Direction[] COMPASS_DIRECTIONS = new Direction[] {
        N, E, S, W};
    
    public final int x, y;
    
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    private static Vector2d nVec = new Vector2d(0, -1),
        eVec = new Vector2d(1, 0),
        sVec = new Vector2d(0, 1),
        wVec = new Vector2d(-1, 0),
        oVec = new Vector2d(0, 0);
    
    public Vector2d toVector2d() {
        switch (this) {
        case N: return nVec;
        case E: return eVec;
        case S: return sVec;
        case W: return wVec;
        case O: return oVec;
        default:
            assert false;
            return null;
        }
    }
    
    public Direction nextClockwise() {
        switch (this) {
        case N:
            return E;
        case E:
            return S;
        case S:
            return W;
        case W:
            return N;
        default:
            return O;
        }
    }
    
    public Direction nextAnticlockwise() {
        return nextClockwise().opposite();
    }
    
    public Direction opposite() {
        switch (this) {
        case N: return S;
        case E: return W;
        case S: return N;
        case W: return E;
        default:
            return O;
        }
    }
    
}
