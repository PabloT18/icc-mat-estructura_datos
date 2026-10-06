import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    static void sort(int[] a) {
        for (int h = a.length / 2; h > 0; h /= 2) {
            for (int i = h; i < a.length; i++) {
                int key = a[i], j = i;
                while (j >= h && a[j-h] > key) { a[j] = a[j-h]; j -= h; }
                a[j] = key;
            }
        }
    }

    public static void main(String[] args) {
        int[][] cases = {{5,3,4,1,2}, {}, {1}, {2,2,1}, {-1,5,0}, {1,2,3}, {3,2,1}};
        for (int[] input : cases) {
            int[] expected = input.clone(); Arrays.sort(expected);
            int[] actual = input.clone(); sort(actual);
            check(Arrays.equals(actual, expected));
        }
        int[] a = {5,3,4,1,2}; sort(a);
        System.out.println(Arrays.toString(a));
    }
}
