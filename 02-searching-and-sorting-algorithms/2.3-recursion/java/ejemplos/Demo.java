import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static long factorial(int n) {
        if (n<0 || n>20) throw new IllegalArgumentException("n debe estar entre 0 y 20");
        return recursive(n);
    }
    static long recursive(int n) { return n==0 ? 1 : n*recursive(n-1); }
    static long iterative(int n) {
        if (n<0 || n>20) throw new IllegalArgumentException("n fuera de rango");
        long value=1;
        for (int i=1;i<=n;i++) value*=i;
        return value;
    }
    public static void main(String[] args) {
        for (int n : new int[]{0,3,5,20}) {
            check(factorial(n)==iterative(n));
            System.out.println(n+":"+factorial(n));
        }
        for (int n : new int[]{-1,21}) {
            try { factorial(n); throw new AssertionError(); }
            catch (IllegalArgumentException expected) { }
        }
    }
}
