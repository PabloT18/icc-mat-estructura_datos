import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static class Node { int key; Node left,right; Node(int k) {key=k;} }
    static Node insert(Node n,int key) {
        if (n==null) return new Node(key);
        if (key<n.key) n.left=insert(n.left,key);
        else if (key>n.key) n.right=insert(n.right,key);
        return n;
    }
    static boolean contains(Node n,int key) {
        while (n!=null) {
            if (key==n.key) return true;
            n=key<n.key ? n.left : n.right;
        }
        return false;
    }
    static void inorder(Node n,List<Integer> out) {
        if (n==null) return;
        inorder(n.left,out); out.add(n.key); inorder(n.right,out);
    }
    public static void main(String[] args) {
        Node root=null;
        for (int x:new int[]{8,3,10,1,6,3}) root=insert(root,x);
        List<Integer> out=new ArrayList<>(); inorder(root,out);
        check(out.equals(Arrays.asList(1,3,6,8,10)));
        check(contains(root,6) && !contains(root,7) && !contains(null,1));
        System.out.println(out);
    }
}
