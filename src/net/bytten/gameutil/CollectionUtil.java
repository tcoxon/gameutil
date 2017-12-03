package net.bytten.gameutil;

import java.util.*;

public class CollectionUtil {
    
    public static<T> List<T> singleton(T value) {
        List<T> list = new ArrayList<T>();
        list.add(value);
        return list;
    }

    public static<T> List<T> filtered(List<T> values, Predicate<T> p) {
        List<T> result = new ArrayList<T>();
        for (T value: values) {
            if (p.query(value))
                result.add(value);
        }
        return result;
    }
    
    public static<R,A> List<R> mapped(List<A> values, UnaryFunction<R,A> f) {
        List<R> result = new ArrayList<R>();
        for (A value: values) {
            result.add(f.run(value));
        }
        return result;
    }

    public static<T> T getOne(Collection<T> from) {
        return from.iterator().next();
    }

    public static<T extends Comparable<T>> Set<T> intersectionTree(Set<T> a, Set<T> b) {
        Set<T> result = new TreeSet<T>(a);
        result.retainAll(b);
        return result;
    }

    public static<T> Set<T> intersectionHash(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        result.retainAll(b);
        return result;
    }

    public static Vec2ISet intersectionVec2I(Set<Vec2I> a, Set<Vec2I> b) {
        Vec2ISet result = new Vec2ISet(a);
        result.retainAll(b);
        return result;
    }

    public static<T extends Comparable<T>> Set<T> differenceTree(Set<T> a, Set<T> b) {
        Set<T> result = new TreeSet<T>(a);
        result.removeAll(b);
        return result;
    }

    public static<T> Set<T> differenceHash(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        result.removeAll(b);
        return result;
    }
    
    public static Vec2ISet differenceVec2I(Set<Vec2I> a, Set<Vec2I> b) {
        Vec2ISet result = new Vec2ISet(a);
        result.removeAll(b);
        return result;
    }
    
    public static<T> List<T> reversed(List<T> list) {
        List<T> result = new ArrayList<T>(list);
        Collections.reverse(result);
        return result;
    }
    
    public static<T extends Comparable<? super T>> List<T> sorted(Collection<T> values) {
        List<T> result = new ArrayList<T>(values);
        Collections.sort(result);
        return result;
    }

    public static<T> List<T> sorted(Collection<T> values, Comparator<T> cmp) {
        List<T> result = new ArrayList<T>(values);
        Collections.sort(result, cmp);
        return result;
    }
}
