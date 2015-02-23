package net.bytten.gameutil;

import java.io.*;
import java.util.*;

public class Matrix<T> implements Serializable, Iterable<T>, Collection<T> {

    private static final long serialVersionUID = 1L;
    
    private ArrayList<ArrayList<T>> elements;
    private int cols;
    private int rows;
    
    public Matrix(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        init();
    }
    
    private void init() {
        elements = new ArrayList<ArrayList<T>>(cols);
        for (int x = 0; x < cols; ++x) {
            ArrayList<T> col = new ArrayList<T>(rows);
            elements.add(col);
            for (int y = 0; y < rows; ++y) {
                col.add(null);
            }
        }
    }
    
    private static<S> void setLen(ArrayList<S> l, int len) {
        assert len >= 0;
        if (l.size() > len) {
            while (l.size() > len) {
                l.remove(len);
            }
        } else if (l.size() < len){
            while (l.size() < len) {
                l.add(null);
            }
        }
    }
    
    public void setCols(int cols) {
        setLen(elements, cols);
        this.cols = cols;
    }
    
    public void setRows(int rows) {
        for (ArrayList<T> col: elements) {
            setLen(col, rows);
        }
        this.rows = rows;
    }
    
    public void clear() {
        init();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
    
    public T get(int x, int y) {
        return elements.get(x).get(y);
    }
    
    public T getSafe(int x, int y) {
        if (x < 0) x = 0;
        if (x >= getCols()) x = getCols()-1;
        if (y < 0) y = 0;
        if (y >= getRows()) y = getRows()-1;
        return get(x,y);
    }
    
    public void set(int x, int y, T v) {
        elements.get(x).set(y, v);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int x = 0, y = 0;
            
            @Override
            public boolean hasNext() {
                return y < getRows();
            }

            @Override
            public T next() {
                T result = get(x,y);
                ++x;
                if (x >= getCols()) {
                    x = 0;
                    ++y;
                }
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
    }

    // XXX Many of these methods are untested and probably won't work!
    
    @Override
    public boolean add(T arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object arg0) {
        for (int x = 0; x < getCols(); ++x)
            for (int y = 0; y < getRows(); ++y) {
                if (get(x,y).equals(arg0))
                    return true;
            }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        for (Object o: arg0) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return getCols() != 0 && getRows() != 0;
    }

    @Override
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return getCols() * getRows();
    }

    @Override
    public Object[] toArray() {
        return toArray(new Object[0]);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] arg0) {
        Object[] result = new Object[size()];
        int i = 0;
        for (int x = 0; x < getCols(); ++x)
            for (int y = 0; y < getRows(); ++x) {
                result[i++] = get(x,y);
            }
        return (T1[]) result;
    }
    
}
