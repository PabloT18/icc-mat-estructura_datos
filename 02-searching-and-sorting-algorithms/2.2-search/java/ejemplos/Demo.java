import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static int sequential(int[] a, int key) {
        for (int i=0; i<a.length; i++) if (a[i]==key) return i;
        return -1;
    }
    static int binaryFirst(int[] a, int key) {
        int lo=0, hi=a.length;
        while (lo<hi) {
            int mid=lo+(hi-lo)/2;
            if (a[mid]<key) lo=mid+1; else hi=mid;
        }
        return lo<a.length && a[lo]==key ? lo : -1;
    }
    public static void main(String[] args) {
        int[] a={1,3,3,7,9};
        for (int key : new int[]{3,8,1,9}) {
            check(binaryFirst(a,key)==sequential(a,key));
            System.out.println(key+":"+binaryFirst(a,key));
        }
        check(binaryFirst(new int[0],3)==-1);
    }
}
