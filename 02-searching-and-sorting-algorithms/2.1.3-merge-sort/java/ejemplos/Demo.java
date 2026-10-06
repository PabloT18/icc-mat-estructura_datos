import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    static void sort(int[] a) { mergeSort(a, new int[a.length], 0, a.length); }
    static void mergeSort(int[] a, int[] buffer, int lo, int hi) {
        if (hi - lo <= 1) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a,buffer,lo,mid); mergeSort(a,buffer,mid,hi);
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi)
            buffer[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i < mid) buffer[k++] = a[i++];
        while (j < hi) buffer[k++] = a[j++];
        for (k = lo; k < hi; k++) a[k] = buffer[k];
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
