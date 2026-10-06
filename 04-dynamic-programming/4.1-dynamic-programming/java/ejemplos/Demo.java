import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static long calls;
    static long recursive(int n) { calls++; return n<2?n:recursive(n-1)+recursive(n-2); }
    static long tabulated(int n) {
        long[] dp=new long[n+2]; dp[1]=1;
        for (int i=2;i<=n;i++) dp[i]=dp[i-1]+dp[i-2];
        return dp[n];
    }
    static void validate(int n) {
        if (n<0 || n>30) throw new IllegalArgumentException("n fuera de rango");
    }
    public static void main(String[] args) {
        for (int n:new int[]{0,1,5,10}) {
            validate(n); calls=0; long value=recursive(n); check(value==tabulated(n));
            System.out.println(n+":"+value+":"+calls);
        }
        try { validate(-1); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { }
    }
}
