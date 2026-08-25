public class BstDeleteTestSuite {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node root;

    static void reset() { root = null; }

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

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.print(n.value + " "); inorder(n.right);
    }

    static void report(String label, boolean result) {
        System.out.print(label + " remove=" + result + " inorder: ");
        inorder(root); System.out.println();
        System.out.println("size=" + size(root) + " valid=" + isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));
        System.out.println();
    }

    public static void main(String[] args) {
        // 1. empty tree 刪除
        reset();
        report("empty tree remove(50): ", remove(50));

        // 2. missing value
        reset(); add(30);
        report("missing value remove(99):", remove(99));

        // 3. single root
        reset(); add(50);
        report("single root remove(50):", remove(50));

        // 4. root with one child（只有右子）
        reset(); add(50); add(70);
        report("root+one-child(right) remove(50):", remove(50));

        // 5. root with one child（只有左子）
        reset(); add(50); add(30);
        report("root+one-child(left) remove(50):", remove(50));

        // 6. root with two children
        reset(); add(50); add(30); add(70);
        report("root+two-children remove(50):", remove(50));

        // 7. 連續刪除到 empty
        reset();
        for (int v : new int[]{50, 30, 70}) add(v);
        System.out.println("連續刪除到 empty:");
        System.out.println("remove 50: " + remove(50));
        System.out.println("remove 30: " + remove(30));
        System.out.println("remove 70: " + remove(70));
        System.out.print("inorder: "); inorder(root); System.out.println("(empty)");
        System.out.println("size=" + size(root));
    }
}