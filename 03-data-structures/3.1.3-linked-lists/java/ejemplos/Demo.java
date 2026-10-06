import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static class Node { int value; Node next; Node(int v) { value=v; } }
    static class Chain {
        Node head, tail; int size;
        void add(int value) {
            Node n=new Node(value);
            if (tail==null) head=n; else tail.next=n;
            tail=n; size++;
        }
        boolean remove(int value) {
            Node prev=null, cur=head;
            while (cur!=null && cur.value!=value) { prev=cur; cur=cur.next; }
            if (cur==null) return false;
            if (prev==null) head=cur.next; else prev.next=cur.next;
            if (cur==tail) tail=prev;
            size--; return true;
        }
        List<Integer> values() {
            List<Integer> out=new ArrayList<>();
            for (Node n=head;n!=null;n=n.next) out.add(n.value);
            return out;
        }
    }
    public static void main(String[] args) {
        Chain c=new Chain(); for (int x:new int[]{10,20,30}) c.add(x);
        check(c.remove(20)); check(!c.remove(99));
        check(c.values().equals(Arrays.asList(10,30)));
        System.out.println(c.values());
        check(c.remove(10)); check(c.remove(30));
        check(c.head==null && c.tail==null && c.size==0); System.out.println(c.size);
    }
}
