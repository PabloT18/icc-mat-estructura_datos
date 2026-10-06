import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static Map<String,Integer> frequencies(List<String> tokens) {
        Map<String,Integer> counts=new HashMap<>();
        for (String token:tokens) counts.put(token, counts.getOrDefault(token,0)+1);
        return counts;
    }
    public static void main(String[] args) {
        Map<String,Integer> m=frequencies(Arrays.asList("sol","luna","sol"));
        check(m.get("sol")==2 && m.get("luna")==1 && !m.containsKey("mar"));
        check(frequencies(Collections.emptyList()).isEmpty());
        for (String key:new TreeSet<>(m.keySet())) System.out.println(key+":"+m.get(key));
    }
}
