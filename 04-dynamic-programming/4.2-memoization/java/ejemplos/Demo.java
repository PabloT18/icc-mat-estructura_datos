import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static long memo(int n,Map<Integer,Long> cache) {
        if (cache.containsKey(n)) return cache.get(n);
        long value=n<2?n:memo(n-1,cache)+memo(n-2,cache);
        cache.put(n,value); return value;
    }
    static long fibonacci(int n,Map<Integer,Long> cache) {
        if (n<0 || n>30) throw new IllegalArgumentException("n fuera de rango");
        return memo(n,cache);
    }
    public static void main(String[] args) {
        Map<Integer,Long> cache=new HashMap<>();
        for (int n:new int[]{10,10,5}) {
            long value=fibonacci(n,cache); check(value==(n==5?5:55));
            System.out.println(n+":"+value+":"+cache.size());
        }
        check(fibonacci(0,new HashMap<>())==0);
        try { fibonacci(-1,cache); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { }
    }
}
