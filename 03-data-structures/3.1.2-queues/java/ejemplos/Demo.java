import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static class Queue {
        final Integer[] data; int head=0, size=0;
        Queue(int capacity) {
            if (capacity<=0) throw new IllegalArgumentException("capacidad");
            data=new Integer[capacity];
        }
        void add(int value) {
            if (size==data.length) throw new IllegalStateException("llena");
            data[(head+size)%data.length]=value; size++;
        }
        int remove() {
            if (size==0) throw new NoSuchElementException("vacía");
            int value=data[head]; data[head]=null;
            head=(head+1)%data.length; size--; return value;
        }
    }
    public static void main(String[] args) {
        Queue q=new Queue(3); q.add(10); q.add(20); q.add(30);
        try { q.add(99); throw new AssertionError(); }
        catch (IllegalStateException expected) { }
        check(q.size==3); System.out.println(q.remove()); q.add(40);
        for (int expected : new int[]{20,30,40}) {
            int value=q.remove(); check(value==expected); System.out.println(value);
        }
        try { q.remove(); throw new AssertionError(); }
        catch (NoSuchElementException expected) { }
        try { new Queue(0); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { }
    }
}
