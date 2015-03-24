package net.bytten.gameutil;

import java.util.*;

public class RandUtil {
    
    static public<T> T uniformChoice(Random rand, List<T> options) {
        return options.get(rand.nextInt(options.size()));
    }
    
    static public<T> T uniformChoice(Random rand, T[] options) {
        return uniformChoice(rand, Arrays.asList(options));
    }
    
    static public<T> T uniformChoice(Random rand, Collection<T> options) {
        return uniformChoice(rand, new ArrayList<T>(options));
    }

    static public<T> T choice(Random rand, List<Pair<Double,T>> options) {
        if (options.size() == 1)
            return options.get(0).second;
        
        double total = 0;
        for (Pair<Double,T> option: options) {
            total += option.first;
        }
        double choice = rand.nextDouble() * total;
        for (Pair<Double,T> option: options) {
            choice -= option.first;
            if (choice < 0.0)
                return option.second;
        }
        return null;
    }
    
    static public<T> T choice(Random rand, Selection<T> selection) {
        return choice(rand, selection.getOptions());
    }

    static public Random deterministicObj(String id) {
        Random r = new Random(id.hashCode());
        r.nextBoolean(); // Generally, the second result is better ¯\_(ツ)_/¯
        return r;
    }
    
    static public double deterministic(String id) {
        // Used to return a deterministic double from 0.0 to 1.0 for a
        // GameObject with the given ID.
        return deterministicObj(id).nextDouble();
    }
    
    static public Random sub(Random r) {
        // Used in the overworld to implement a hierarchical seed to avoid
        // early changes causing massive cascading changes elsewhere in the map.
        return new Random(r.nextLong());
    }
    
}
