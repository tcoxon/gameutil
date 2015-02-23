package net.bytten.gameutil;

import java.io.*;
import java.util.*;

public class SerialClone {
    // Deep clone objects via Java's Serialization.

    public static <T> T clone(T x) {
        try {
            return cloneEx(x);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T cloneEx(T x) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        CloneOutput cout = new CloneOutput(bout);
        cout.writeObject(x);
        
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        CloneInput cin = new CloneInput(bin, cout);
        
        @SuppressWarnings("unchecked")
        T clone = (T)cin.readObject();
        
        cin.close();
        return clone;
    }
    
    private static class CloneOutput extends ObjectOutputStream {
        Queue<Class<?>> classQueue = new LinkedList<Class<?>>();
        
        CloneOutput(OutputStream out) throws IOException {
            super(out);
        }
        
        @Override
        protected void annotateClass(Class<?> c) {
            classQueue.add(c);
        }
        
        @Override
        protected void annotateProxyClass(Class<?> c) {
            classQueue.add(c);
        }
    }
    
    private static class CloneInput extends ObjectInputStream {
        private final CloneOutput output;
        
        CloneInput(InputStream in, CloneOutput output) throws IOException {
            super(in);
            this.output = output;
        }
        
        @Override
        protected Class<?> resolveClass(ObjectStreamClass osc)
                throws IOException, ClassNotFoundException {
            Class<?> c = output.classQueue.poll();
            String expected = osc.getName();
            String found = (c == null) ? null : c.getName();
            if (!expected.equals(found)) {
                throw new InvalidClassException("Classes desynchronized: "+
                        "found " + found + " when expecting " + expected);
            }
            return c;
        }
        
        @Override
        protected Class<?> resolveProxyClass(String[] interfaceNames)
                throws IOException, ClassNotFoundException {
            return output.classQueue.poll();
        }
    }
    
}
