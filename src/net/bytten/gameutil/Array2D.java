package net.bytten.gameutil;

import java.io.*;
import java.util.*;

public class Array2D<T> implements Serializable, Iterable<T>, Collection<T> {

    private static final long serialVersionUID = 1L;
    
    private ArrayList<T> elements;
    private int cols;
    private int rows;
    
    public Array2D(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        init();
    }
    
    private void init() {
        elements = new ArrayList<T>(cols * rows);
        for (int i = 0; i < cols * rows; ++i) {
            elements.add(null);
        }
    }
    
    public void clear() {
        elements.clear();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
    
    private int index(int x, int y) {
        return y * cols + x;
    }
    
    public T get(int x, int y) {
        return elements.get(index(x,y));
    }
    
    public T getClamped(int x, int y) {
        if (x < 0) x = 0;
        if (x >= getCols()) x = getCols()-1;
        if (y < 0) y = 0;
        if (y >= getRows()) y = getRows()-1;
        return get(x,y);
    }
    
    public void set(int x, int y, T v) {
        elements.set(index(x,y), v);
    }

    public void fill(T value) {
        for (int x = 0; x < cols; ++x)
        for (int y = 0; y < rows; ++y)
            set(x, y, value);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

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
        return elements.contains(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return elements.containsAll(arg0);
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
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
        return elements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] arg0) {
        return elements.toArray(arg0);
    }
    
}
