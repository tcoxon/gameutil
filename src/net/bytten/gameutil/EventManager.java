package net.bytten.gameutil;

import java.util.*;

public class EventManager<Listener, Args> {

    public static interface Invoker<Listener, Args> {
        public void invoke(Listener listener, Args args);
    }

    private Invoker<Listener, Args> defaultInvoker;
    private List<Listener> listeners = new ArrayList<Listener>();

    public EventManager() {
    }

    public EventManager(Invoker<Listener, Args> defaultInvoker) {
        this.defaultInvoker = defaultInvoker;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void fire(Args args) {
        fire(defaultInvoker, args);
    }

    public void fire(Invoker<Listener, Args> invoker) {
        fire(invoker, null);
    }

    public void fire(Invoker<Listener, Args> invoker, Args args) {
        for (Listener l : listeners) {
            invoker.invoke(l, args);
        }
    }
}
