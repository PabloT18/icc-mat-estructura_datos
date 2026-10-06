import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static List<String> unique(List<String> input) {
        Set<String> seen=new HashSet<>(); List<String> out=new ArrayList<>();
        for (String value:input) if (seen.add(value)) out.add(value);
        return out;
    }
    public static void main(String[] args) {
        List<String> out=unique(Arrays.asList("B","A","B","C","A"));
        check(out.equals(Arrays.asList("B","A","C")));
        check(unique(Collections.emptyList()).isEmpty());
        System.out.println(String.join(",",out));
    }
}
