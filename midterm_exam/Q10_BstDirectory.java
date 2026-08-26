import java.util.*;

public class Q10_BstDirectory {

    private static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    private Node root;
    private int  size;

    public boolean add(int value) {
        if (root == null) { root = new Node(value); size++; return true; }
        Node cur = root;
        while (true) {
            if (value == cur.value) return false;
            if (value < cur.value) {
                if (cur.left  == null) { cur.left  = new Node(value); size++; return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(value); size++; return true; }
                cur = cur.right;
            }
        }
    }

    public boolean contains(int value) {
        Node cur = root;
        while (cur != null) {
            if (value == cur.value) return true;
            cur = value < cur.value ? cur.left : cur.right;
        }
        return false;
    }

    public int size() { return size; }

    public List<Integer> searchPath(int target) {
        // bst-path-check T10-73
        List<Integer> path = new ArrayList<>();
        Node cur = root;
        while (cur != null) {
            path.add(cur.value);
            if (target == cur.value) break;
            cur = target < cur.value ? cur.left : cur.right;
        }
        return path;
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node n, List<Integer> result) {
        if (n == null) return;
        inorderHelper(n.left, result);
        result.add(n.value);
        inorderHelper(n.right, result);
    }

    public boolean isValid() {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValid(Node n, long min, long max) {
        if (n == null) return true;
        if (n.value <= min || n.value >= max) return false;
        return isValid(n.left, min, n.value) && isValid(n.right, n.value, max);
    }

    public static void main(String[] args) {
        Q10_BstDirectory tree = new Q10_BstDirectory();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) tree.add(v);

        System.out.println(tree.add(40));           // false（重複）
        System.out.println(tree.searchPath(60));    // [50, 70, 60]
        System.out.println(tree.searchPath(65));    // [50, 70, 60]（找不到停在最後比較的 node）
        System.out.println(tree.inorder());         // [20, 30, 40, 50, 60, 70, 80]
        System.out.println(tree.isValid());         // true
        System.out.println(tree.size());            // 7
        System.out.println(tree.contains(30));      // true
        System.out.println(tree.contains(99));      // false

        // empty tree
        Q10_BstDirectory empty = new Q10_BstDirectory();
        System.out.println(empty.inorder());        // []
        System.out.println(empty.isValid());        // true
        System.out.println(empty.searchPath(50));   // []
    }
}