import java.util.*;

public class Q11_BstDeletion {

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

    public boolean remove(int value) {
        if (!contains(value)) return false;
        root = removeNode(root, value);
        size--;
        return true;
    }

    private Node removeNode(Node n, int value) {
        if (n == null) return null;
        if      (value < n.value) n.left  = removeNode(n.left,  value);
        else if (value > n.value) n.right = removeNode(n.right, value);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node successorAuditN11 = n.right;
            while (successorAuditN11.left != null) successorAuditN11 = successorAuditN11.left;
            n.value = successorAuditN11.value;
            n.right = removeNode(n.right, successorAuditN11.value);
        }
        return n;
    }

    public int size() { return size; }

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
        Q11_BstDeletion tree = new Q11_BstDeletion();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) tree.add(v);

        System.out.println(tree.remove(20));    // true（leaf）
        System.out.println(tree.remove(30));    // true（single child）
        System.out.println(tree.remove(50));    // true（two children）
        System.out.println(tree.remove(999));   // false（不存在）
        System.out.println(tree.inorder());     // [40, 60, 70, 80]
        System.out.println(tree.size());        // 4
        System.out.println(tree.isValid());     // true

        // 刪除 root（單一 node）
        Q11_BstDeletion single = new Q11_BstDeletion();
        single.add(42);
        System.out.println(single.remove(42));  // true
        System.out.println(single.inorder());   // []
        System.out.println(single.size());      // 0
        System.out.println(single.isValid());   // true

        // empty tree 刪除
        System.out.println(single.remove(42));  // false
    }
}