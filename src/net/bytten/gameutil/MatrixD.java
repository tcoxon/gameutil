package net.bytten.gameutil;

public strictfp class MatrixD extends Array2D<Double> {
	private static final long serialVersionUID = 1L;

	public MatrixD(int cols, int rows) {
        super(cols, rows);
        for (int x = 0; x < cols; x++)
            for (int y = 0; y < rows; ++y)
                set(x,y, 0.0);
    }
    
    public void add(MatrixD other) {
        assert getCols() == other.getCols() && getRows() == other.getRows();
        for (int x = 0; x < getCols(); x++)
            for (int y = 0; y < getRows(); ++y)
                set(x,y, get(x,y) + other.get(x,y));
    }
    
    public void multiply(double m) {
        for (int x = 0; x < getCols(); x++)
            for (int y = 0; y < getRows(); ++y)
                set(x,y, get(x,y) * m);
    }

}
