import java.util.*;

public class TraversalTestReport {

    static class Node {
        String v; Node left, right;
        Node(String v) { this.v = v; }
    }

    static List<String> pre(Node n) {
        List<String> r = new ArrayList<>();
        preH(n, r); return r;
    }
    static void preH(Node n, List<String> r) {
        if (n == null) return; r.add(n.v); preH(n.left, r); preH(n.right, r);
    }

    static List<String> in(Node n) {
        List<String> r = new ArrayList<>();
        inH(n, r); return r;
    }
    static void inH(Node n, List<String> r) {
        if (n == null) return; inH(n.left, r); r.add(n.v); inH(n.right, r);
    }

    static List<String> post(Node n) {
        List<String> r = new ArrayList<>();
        postH(n, r); return r;
    }
    static void postH(Node n, List<String> r) {
        if (n == null) return; postH(n.left, r); postH(n.right, r); r.add(n.v);
    }

    static List<String> level(Node root) {
        List<String> r = new ArrayList<>();
        if (root == null) return r;
        Queue<Node> q = new ArrayDeque<>(); q.offer(root);
        while (!q.isEmpty()) {
            Node cur = q.poll(); r.add(cur.v);
            if (cur.left != null) q.offer(cur.left);
            if (cur.right != null) q.offer(cur.right);
        }
        return r;
    }

    static void check(String traversal, List<String> actual, List<String> expected) {
        boolean ok = actual.equals(expected);
        System.out.printf("  %-12s actual=%-30s expected=%-30s %s%n",
                traversal, actual, expected, ok ? "✅" : "❌");
    }

    static void testTree(String label, Node root,
                         List<String> expPre, List<String> expIn,
                         List<String> expPost, List<String> expLevel) {
        System.out.println("=== " + label + " ===");
        check("preorder",  pre(root),   expPre);
        check("inorder",   in(root),    expIn);
        check("postorder", post(root),  expPost);
        check("levelorder",level(root), expLevel);
    }

    public static void main(String[] args) {
        // 1. empty
        testTree("empty", null,
            List.of(), List.of(), List.of(), List.of());

        // 2. single-node
        testTree("single-node", new Node("A"),
            List.of("A"), List.of("A"), List.of("A"), List.of("A"));

        // 3. only-left（A->B->C）
        Node onlyLeft = new Node("A");
        onlyLeft.left = new Node("B");
        onlyLeft.left.left = new Node("C");
        testTree("only-left", onlyLeft,
            List.of("A","B","C"),
            List.of("C","B","A"),
            List.of("C","B","A"),
            List.of("A","B","C"));

        // 4. only-right（A->B->C）
        Node onlyRight = new Node("A");
        onlyRight.right = new Node("B");
        onlyRight.right.right = new Node("C");
        testTree("only-right", onlyRight,
            List.of("A","B","C"),
            List.of("A","B","C"),
            List.of("C","B","A"),
            List.of("A","B","C"));

        // 5. complete tree（A,B,C,D,E,F,G）
        Node comp = new Node("A");
        comp.left  = new Node("B"); comp.right = new Node("C");
        comp.left.left  = new Node("D"); comp.left.right  = new Node("E");
        comp.right.left = new Node("F"); comp.right.right = new Node("G");
        testTree("complete", comp,
            List.of("A","B","D","E","C","F","G"),
            List.of("D","B","E","A","F","C","G"),
            List.of("D","E","B","F","G","C","A"),
            List.of("A","B","C","D","E","F","G"));

        // 6. irregular tree（A,B(C,null),D）
        Node irreg = new Node("A");
        irreg.left = new Node("B");
        irreg.left.left = new Node("C");
        irreg.right = new Node("D");
        testTree("irregular", irreg,
            List.of("A","B","C","D"),
            List.of("C","B","A","D"),
            List.of("C","B","D","A"),
            List.of("A","B","D","C"));
    }
}