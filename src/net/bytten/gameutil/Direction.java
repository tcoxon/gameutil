package net.bytten.gameutil;

public enum Direction {

    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    O(0, 0),
    
    ;
    
    public static final Direction[] CARDINALS = new Direction[]{N, E, S, W},
                                    CARDINALS_WITH_O = new Direction[]{N, E, S, W, O},
                                    LEFT_RIGHT = new Direction[]{W, E},
                                    LEFT_RIGHT_WITH_O = new Direction[]{W, E, O};
    
    public final int x, y;
    private final Coords coords;
    private final Vector2d vector;
    
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
        this.coords = new Coords(x,y);
        this.vector = new Vector2d(x,y);
    }
    
    public Vector2d toVector2d() {
        return vector;
    }
    
    public Coords toCoords() {
        return coords;
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
