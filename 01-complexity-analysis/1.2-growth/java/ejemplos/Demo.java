import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static long pairs(int n) {
        if (n < 0) throw new IllegalArgumentException("n negativo");
        long count = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) count++;
        return count;
    }
    public static void main(String[] args) {
        check(pairs(0) == 0);
        for (int n : new int[]{4, 8, 16}) {
            check(pairs(n) == (long)n * (n - 1) / 2);
            System.out.println(n + ":" + pairs(n));
        }
        try { pairs(-1); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { }
    }
}
