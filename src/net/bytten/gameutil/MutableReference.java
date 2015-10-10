package net.bytten.gameutil;

public class MutableReference<T> {
    // For when you want to change a reference from within a closure. Silly
    // example:
    //  public<T> T foobar() {
    //    final MutableReference<T> result = new MutableReference<T>(null);
    //    wrapAndLogErrors(new Runnable() {
    //      public void run() {
    //        result.set(unsafeOperation());
    //      }
    //    });
    //    return result.get();
    //  }

    private T value;

    public MutableReference(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

}
