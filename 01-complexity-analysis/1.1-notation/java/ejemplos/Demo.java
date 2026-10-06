import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static int visits(int[] a) {
        int count = 0;
        for (int value : a) count++;
        return count;
    }
    public static void main(String[] args) {
        check(visits(new int[0]) == 0);
        for (int n : new int[]{0, 4, 8}) {
            check(visits(new int[n]) == n);
            System.out.println(n + ":" + visits(new int[n]));
        }
    }
}
