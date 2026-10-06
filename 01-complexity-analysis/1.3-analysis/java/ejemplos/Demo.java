import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static long sumLoop(int n) {
        if (n < 0 || n > 1000000) throw new IllegalArgumentException("fuera de rango");
        long total = 0;
        for (int i = 1; i <= n; i++) total += i;
        return total;
    }
    static long sumFormula(int n) {
        if (n < 0 || n > 1000000) throw new IllegalArgumentException("fuera de rango");
        return (long)n * (n + 1) / 2;
    }
    public static void main(String[] args) {
        for (int n : new int[]{0, 5, 10, 1000}) {
            check(sumLoop(n) == sumFormula(n));
            System.out.println(n + ":" + sumLoop(n));
        }
        try { sumLoop(-1); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { }
    }
}
