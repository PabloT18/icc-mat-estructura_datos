import java.util.*;

public class Demo {
    static void check(boolean ok) {
        if (!ok) throw new AssertionError("Verificación fallida");
    }
static boolean balanced(String text) {
        Deque<Character> stack=new ArrayDeque<>();
        String open="([{", close=")]}";
        for (char c : text.toCharArray()) {
            if (open.indexOf(c)>=0) stack.push(c);
            else if (close.indexOf(c)>=0) {
                int kind=close.indexOf(c);
                if (stack.isEmpty() || stack.pop()!=open.charAt(kind)) return false;
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] args) {
        String[] inputs={"([])","([)]","","("};
        boolean[] expected={true,false,true,false};
        for (int i=0;i<inputs.length;i++) {
            check(balanced(inputs[i])==expected[i]);
            System.out.println(balanced(inputs[i])?"valido":"invalido");
        }
    }
}
