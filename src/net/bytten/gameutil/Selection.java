package net.bytten.gameutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Selection<T> {
    // Handy type wrapping the RandUtil.choice parameter type, suitable for
    // serializing and/or reading from JSON.
    
    private List<Pair<Double,T>> options;
    
    public Selection() {
        options = new ArrayList<Pair<Double,T>>();
    }
    
    public Selection(List<Pair<Double,T>> options) {
        this();
        this.options.addAll(options);
    }
    
    public Selection(T singletonOption) {
        this();
        add(1.0, singletonOption);
    }
    
    public void add(double weight, T option) {
        options.add(new Pair<Double,T>(weight, option));
    }
    
    public void add(Pair<Double,T> option) {
        options.add(option);
    }
    
    public List<Pair<Double,T>> getOptions() {
        return Collections.unmodifiableList(options);
    }
    
    public Selection<T> filtered(final Predicate<T> p) {
        return new Selection<T>(CollectionUtil.filtered(getOptions(), new Predicate<Pair<Double,T>>() {
            public boolean query(Pair<Double, T> value) {
                return p.query(value.second);
            }
        }));
    }

    public int size() {
        return options.size();
    }
}
