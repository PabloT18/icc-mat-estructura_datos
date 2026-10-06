import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static Map<String,Integer> bfs(Map<String,List<String>> graph,String start) {
        if (!graph.containsKey(start)) throw new IllegalArgumentException("origen");
        Map<String,Integer> distance=new LinkedHashMap<>();
        for (String vertex:graph.keySet()) distance.put(vertex,-1);
        Deque<String> queue=new ArrayDeque<>(); queue.addLast(start); distance.put(start,0);
        while (!queue.isEmpty()) {
            String u=queue.removeFirst();
            for (String v:graph.get(u)) if (distance.get(v)==-1) {
                distance.put(v,distance.get(u)+1); queue.addLast(v);
            }
        }
        return distance;
    }
    public static void main(String[] args) {
        Map<String,List<String>> g=new LinkedHashMap<>();
        g.put("A",Arrays.asList("B","C")); g.put("B",Arrays.asList("A","D"));
        g.put("C",Arrays.asList("A","D")); g.put("D",Arrays.asList("B","C"));
        g.put("E",Collections.emptyList());
        Map<String,Integer> d=bfs(g,"A"); check(d.get("D")==2 && d.get("E")==-1);
        for (String key:d.keySet()) System.out.println(key+":"+d.get(key));
        try { bfs(g,"X"); throw new AssertionError(); }
        catch (IllegalArgumentException expected) { }
    }
}
