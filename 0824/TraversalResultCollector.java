import java.util.*;

public class TraversalResultCollector {

    static class Node {
        String value;
        Node left, right;
        Node(String v) { value = v; }
    }

    static List<String> preorder(Node n) {
        List<String> result = new ArrayList<>();
        preorderHelper(n, result);
        return result;
    }

    private static void preorderHelper(Node n, List<String> result) {
        if (n == null) return;
        result.add(n.value);
        preorderHelper(n.left,  result);
        preorderHelper(n.right, result);
    }

    static List<String> inorder(Node n) {
        List<String> result = new ArrayList<>();
        inorderHelper(n, result);
        return result;
    }

    private static void inorderHelper(Node n, List<String> result) {
        if (n == null) return;
        inorderHelper(n.left, result);
        result.add(n.value);
        inorderHelper(n.right, result);
    }

    static List<String> postorder(Node n) {
        List<String> result = new ArrayList<>();
        postorderHelper(n, result);
        return result;
    }

    private static void postorderHelper(Node n, List<String> result) {
        if (n == null) return;
        postorderHelper(n.left,  result);
        postorderHelper(n.right, result);
        result.add(n.value);
    }

    static List<String> levelOrder(Node root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            result.add(cur.value);
            if (cur.left  != null) q.offer(cur.left);
            if (cur.right != null) q.offer(cur.right);
        }
        return result;
    }

    static void allTraversals(String label, Node root) {
        System.out.println("=== " + label + " ===");
        System.out.println("preorder  : " + preorder(root));
        System.out.println("inorder   : " + inorder(root));
        System.out.println("postorder : " + postorder(root));
        System.out.println("levelorder: " + levelOrder(root));
    }

    public static void main(String[] args) {
        // empty tree
        allTraversals("empty", null);

        // single-node
        allTraversals("single", new Node("X"));

        // left-skewed
        Node ls = new Node("A");
        ls.left = new Node("B");
        ls.left.left = new Node("C");
        allTraversals("left-skewed", ls);

        // complete tree
        Node comp = new Node("A");
        comp.left  = new Node("B"); comp.right = new Node("C");
        comp.left.left  = new Node("D"); comp.left.right  = new Node("E");
        comp.right.left = new Node("F"); comp.right.right = new Node("G");
        allTraversals("complete", comp);
    }
}