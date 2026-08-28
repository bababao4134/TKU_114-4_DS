import java.util.*;

public class CompleteBstTestSuite {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node root;
    static int  size;
    static int  passed, failed;

    static void reset() { root = null; size = 0; }

    static boolean add(int v) {
        if (root == null) { root = new Node(v); size++; return true; }
        Node cur = root;
        while (true) {
            if (v == cur.value) return false;
            if (v < cur.value) {
                if (cur.left  == null) { cur.left  = new Node(v); size++; return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(v); size++; return true; }
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
        root = removeNode(root, v); size--; return true;
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
        inH(n, r); return r;
    }
    static void inH(Node n, List<Integer> r) {
        if (n == null) return; inH(n.left, r); r.add(n.value); inH(n.right, r);
    }

    static boolean isValid(Node n, long min, long max) {
        if (n == null) return true;
        if (n.value <= min || n.value >= max) return false;
        return isValid(n.left, min, n.value) && isValid(n.right, n.value, max);
    }

    static List<Integer> range(int low, int high) {
        List<Integer> r = new ArrayList<>();
        if (low <= high) rangeH(root, low, high, r);
        return r;
    }
    static void rangeH(Node n, int low, int high, List<Integer> r) {
        if (n == null) return;
        if (n.value > low)  rangeH(n.left,  low, high, r);
        if (n.value >= low && n.value <= high) r.add(n.value);
        if (n.value < high) rangeH(n.right, low, high, r);
    }

    static void check(String desc, boolean condition) {
        String result = condition ? "PASS" : "FAIL";
        System.out.printf("%-50s %s%n", desc, result);
        if (condition) passed++; else failed++;
    }

    public static void main(String[] args) {
        passed = failed = 0;

        // 1. Empty tree
        reset();
        check("empty: contains(50) = false",    !contains(50));
        check("empty: remove(50) = false",       !remove(50));
        check("empty: inorder = []",             inorder(root).isEmpty());
        check("empty: size = 0",                 size == 0);
        check("empty: isValid = true",           isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));

        // 2. Single root
        reset(); add(50);
        check("single: contains(50) = true",    contains(50));
        check("single: size = 1",               size == 1);
        check("single: remove(50) = true",      remove(50));
        check("single: after remove size = 0",  size == 0);
        check("single: after remove valid",     isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));

        // 3. Duplicate
        reset();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) add(v);
        check("duplicate: add(40) = false",     !add(40));
        check("duplicate: size unchanged = 7",  size == 7);

        // 4. Missing
        check("missing: contains(99) = false",  !contains(99));
        check("missing: remove(99) = false",    !remove(99));

        // 5. Leaf delete
        int beforeLeaf = size;
        remove(20);
        check("leaf: remove(20) inorder",       !inorder(root).contains(20));
        check("leaf: size decreased",           size == beforeLeaf - 1);
        check("leaf: still valid",              isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));

        // 6. One-child delete（30 現在只剩 right 40）
        int before1 = size;
        remove(30);
        check("one-child: 40 preserved",        contains(40));
        check("one-child: size decreased",      size == before1 - 1);
        check("one-child: valid",               isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));

        // 7. Two-children delete（50 有 40 和 70）
        int before2 = size;
        remove(50);
        check("two-child: inorder sorted",      isSorted(inorder(root)));
        check("two-child: size decreased",      size == before2 - 1);
        check("two-child: valid",               isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));

        // 8. Range query
        reset();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) add(v);
        check("range [30,60] size = 4",         range(30, 60).size() == 4);
        check("range [50,50] = [50]",           range(50, 50).equals(List.of(50)));
        check("range low>high = []",            range(70, 30).isEmpty());

        // 9. Invariant
        check("invariant: standard tree valid", isValid(root, Long.MIN_VALUE, Long.MAX_VALUE));

        System.out.println("\n=== Result: " + passed + " PASS / " + failed + " FAIL ===");
    }

    static boolean isSorted(List<Integer> list) {
        for (int i = 1; i < list.size(); i++)
            if (list.get(i) <= list.get(i - 1)) return false;
        return true;
    }
}