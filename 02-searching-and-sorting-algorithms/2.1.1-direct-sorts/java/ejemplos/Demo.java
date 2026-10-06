import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    static void bubble(int[] a) {
        for (int end = a.length - 1; end > 0; end--) {
            boolean changed = false;
            for (int j = 0; j < end; j++) {
                if (a[j] > a[j+1]) { swap(a,j,j+1); changed = true; }
            }
            if (!changed) break;
        }
    }
    static void selection(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i+1; j < a.length; j++)
                if (a[j] < a[min]) min = j;
            if (min != i) swap(a,i,min);
        }
    }
    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i-1;
            while (j >= 0 && a[j] > key) { a[j+1] = a[j]; j--; }
            a[j+1] = key;
        }
    }
    static void sort(int[] a) {
        int[] b = a.clone(), c = a.clone();
        bubble(b); selection(c); insertion(a);
        check(Arrays.equals(a,b) && Arrays.equals(a,c));
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
