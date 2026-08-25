public class BstDeleteCases {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node root;

    static void add(int v) {
        if (root == null) { root = new Node(v); return; }
        Node cur = root;
        while (true) {
            if (v == cur.value) return;
            if (v < cur.value) {
                if (cur.left  == null) { cur.left  = new Node(v); return; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(v); return; }
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

    static int size(Node n) { return n == null ? 0 : 1 + size(n.left) + size(n.right); }

    static boolean isValid(Node n, long min, long max) {
        if (n == null) return true;
        if (n.value <= min || n.value >= max) return false;
        return isValid(n.left, min, n.value) && isValid(n.right, n.value, max);
    }

    static void report(String op) {
        System.out.print("inorder: ");
        inorder(root); System.out.println();
        System.out.println("size=" + size(root) + " valid=" + isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));
        System.out.println();
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.print(n.value + " "); inorder(n.right);
    }

    public static void main(String[] args) {
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) add(v);
        System.out.println("初始:"); report("initial");

        // Case 1：刪除 leaf（20）
        System.out.println("刪除 leaf 20: remove=" + remove(20));
        report("after leaf delete");

        // Case 2：刪除 single-child node（30 現在只剩 right child 40）
        System.out.println("刪除 single-child 30: remove=" + remove(30));
        report("after single-child delete");

        // Case 3：刪除 two-child node（50 仍有左 40 右 70）
        System.out.println("刪除 two-child 50: remove=" + remove(50));
        report("after two-child delete");

        // 刪除不存在的值
        System.out.println("刪除不存在的 99: remove=" + remove(99));
    }
}