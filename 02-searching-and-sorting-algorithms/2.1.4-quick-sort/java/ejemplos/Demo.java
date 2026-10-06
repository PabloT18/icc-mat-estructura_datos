import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi], i = lo;
        for (int j = lo; j < hi; j++)
            if (a[j] <= pivot) { swap(a,i,j); i++; }
        swap(a,i,hi); return i;
    }
    static void quick(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a,lo,hi);
        quick(a,lo,p-1); quick(a,p+1,hi);
    }
    static void sort(int[] a) { quick(a,0,a.length-1); }

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
