import java.util.*;

public class BstOperationAudit {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node root;
    static int  size;

    static void reset() { root = null; size = 0; }

    static boolean add(int value) {
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

    static boolean contains(int v) {
        Node cur = root;
        while (cur != null) {
            if (v == cur.value) return true;
            cur = v < cur.value ? cur.left : cur.right;
        }
        return false;
    }

    static boolean remove(int v) {
        if (!contains(v)) return false;
        root = removeNode(root, v);
        size--;
        return true;
    }

    static Node removeNode(Node n, int v) {
        if (n == null) return null;
        if      (v < n.value) n.left  = removeNode(n.left,  v);
        else if (v > n.value) n.right = removeNode(n.right, v);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.value = succ.value;
            n.right = removeNode(n.right, succ.value);
        }
        return n;
    }

    static List<Integer> inorder(Node n) {
        List<Integer> r = new ArrayList<>();
        inorderH(n, r); return r;
    }
    static void inorderH(Node n, List<Integer> r) {
        if (n == null) return;
        inorderH(n.left, r); r.add(n.value); inorderH(n.right, r);
    }

    static int height(Node n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    static boolean isValid(Node n, long min, long max) {
        if (n == null) return true;
        if (n.value <= min || n.value >= max) return false;
        return isValid(n.left, min, n.value) && isValid(n.right, n.value, max);
    }

    static String deleteCase(int v) {
        Node cur = root;
        while (cur != null) {
            if (v == cur.value) {
                if (cur.left == null && cur.right == null) return "LEAF";
                if (cur.left == null || cur.right == null) return "ONE_CHILD";
                return "TWO_CHILDREN";
            }
            cur = v < cur.value ? cur.left : cur.right;
        }
        return "MISSING";
    }

    static void addAudit(int v) {
        boolean result = add(v);
        System.out.printf("ADD %d | result=%-5s | inorder=%-30s | size=%d height=%d valid=%s%n",
                v, result, inorder(root), size, height(root),
                isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));
    }

    static void removeAudit(int v) {
        String cas = deleteCase(v);
        boolean result = remove(v);
        System.out.printf("REMOVE %d | case=%-13s | result=%-5s | inorder=%-30s | size=%d valid=%s%n",
                v, cas, result, inorder(root), size,
                isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));
    }

    public static void main(String[] args) {
        System.out.println("=== BST Operation Audit ===");
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) addAudit(v);

        System.out.println();
        addAudit(40);   // duplicate
        addAudit(99);   // 新增成功

        System.out.println();
        removeAudit(20);  // LEAF
        removeAudit(30);  // ONE_CHILD（刪後 30 只剩 right 40）
        removeAudit(50);  // TWO_CHILDREN
        removeAudit(999); // MISSING
    }
}