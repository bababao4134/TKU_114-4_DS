import java.util.*;

public class TreeBugLab {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    // ════════ 共用工具 ════════
    static List<Integer> inorder(Node n) {
        List<Integer> r = new ArrayList<>();
        inH(n, r); return r;
    }
    static void inH(Node n, List<Integer> r) {
        if (n == null) return; inH(n.left, r); r.add(n.value); inH(n.right, r);
    }
    static boolean isValidBoundary(Node n, long min, long max) {
        if (n == null) return true;
        if (n.value <= min || n.value >= max) return false;
        return isValidBoundary(n.left, min, n.value)
            && isValidBoundary(n.right, n.value, max);
    }

    // ════════ Bug 1：Search 方向相反 ════════
    static boolean bugSearch(Node root, int target) {
        Node cur = root;
        while (cur != null) {
            if (target == cur.value) return true;
            // ❌ 方向相反：小的往 right，大的往 left
            cur = target < cur.value ? cur.right : cur.left;
        }
        return false;
    }
    static boolean fixedSearch(Node root, int target) {
        Node cur = root;
        while (cur != null) {
            if (target == cur.value) return true;
            // ✅ 修正：小往 left，大往 right
            cur = target < cur.value ? cur.left : cur.right;
        }
        return false;
    }

    // ════════ Bug 2：Inorder 順序錯誤（處理 node 在 right 遞迴前）════════
    static void bugInorder(Node n, List<Integer> r) {
        if (n == null) return;
        bugInorder(n.left, r);
        bugInorder(n.right, r);
        r.add(n.value); // ❌ node 在兩個 recursive 後加入，應在中間
    }
    static void fixedInorder(Node n, List<Integer> r) {
        if (n == null) return;
        fixedInorder(n.left, r);
        r.add(n.value); // ✅ 中間加入
        fixedInorder(n.right, r);
    }

    // ════════ Bug 3：Delete 遺失 child（one-child 回傳 null）════════
    static Node bugRemove(Node n, int v) {
        if (n == null) return null;
        if      (v < n.value) n.left  = bugRemove(n.left,  v);
        else if (v > n.value) n.right = bugRemove(n.right, v);
        else {
            return null; // ❌ 一律回傳 null，遺失唯一 child
        }
        return n;
    }
    static Node fixedRemove(Node n, int v) {
        if (n == null) return null;
        if      (v < n.value) n.left  = fixedRemove(n.left,  v);
        else if (v > n.value) n.right = fixedRemove(n.right, v);
        else {
            if (n.left  == null) return n.right; // ✅ 回傳唯一 child
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.value = succ.value;
            n.right = fixedRemove(n.right, succ.value);
        }
        return n;
    }

    // ════════ Bug 4：Validation 只檢查直接 child ════════
    static boolean bugValidation(Node n) {
        if (n == null) return true;
        // ❌ 只比 parent-child，無全域 boundary
        if (n.left  != null && n.left.value  >= n.value) return false;
        if (n.right != null && n.right.value <= n.value) return false;
        return bugValidation(n.left) && bugValidation(n.right);
    }
    static boolean fixedValidation(Node n) {
        // ✅ 傳遞全域 boundary
        return isValidBoundary(n, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    // ════════ 建立測試樹 ════════
    static Node buildTree(int... values) {
        Node root = null;
        for (int v : values) {
            if (root == null) { root = new Node(v); continue; }
            Node cur = root;
            while (true) {
                if (v < cur.value) {
                    if (cur.left  == null) { cur.left  = new Node(v); break; }
                    cur = cur.left;
                } else if (v > cur.value) {
                    if (cur.right == null) { cur.right = new Node(v); break; }
                    cur = cur.right;
                } else break;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        // ── Bug 1：Search 方向相反 ──
        System.out.println("=== Bug 1：Search 方向相反 ===");
        Node t1 = buildTree(50, 30, 70, 20, 40);
        System.out.println("bugSearch(40)   = " + bugSearch(t1, 40));   // false（找不到存在的 40）
        System.out.println("fixedSearch(40) = " + fixedSearch(t1, 40)); // true

        // ── Bug 2：Inorder 順序錯誤 ──
        System.out.println("\n=== Bug 2：Inorder 順序錯誤 ===");
        Node t2 = buildTree(50, 30, 70);
        List<Integer> bugResult = new ArrayList<>();
        bugInorder(t2, bugResult);
        System.out.println("bugInorder   = " + bugResult); // [30, 70, 50]（postorder）
        List<Integer> fixResult = new ArrayList<>();
        fixedInorder(t2, fixResult);
        System.out.println("fixedInorder = " + fixResult); // [30, 50, 70]

        // ── Bug 3：Delete 遺失 child ──
        System.out.println("\n=== Bug 3：Delete 遺失 child ===");
        // 建立 50 -> left:30，刪除 30 應剩下 50
        // 但 30 有 right child 40，刪除 30 後 40 應保留
        Node t3bug  = buildTree(50, 30, 40);
        Node t3fix  = buildTree(50, 30, 40);
        t3bug = bugRemove(t3bug, 30);
        t3fix = fixedRemove(t3fix, 30);
        System.out.println("bugRemove(30) inorder   = " + inorder(t3bug)); // [50]（遺失 40）
        System.out.println("fixedRemove(30) inorder = " + inorder(t3fix)); // [40, 50]

        // ── Bug 4：Validation 只檢查直接 child ──
        System.out.println("\n=== Bug 4：Validation 只檢查直接 child ===");
        // 手動建立深層違規：root=50, left=30, left.right=55（55>50 卻在 left subtree）
        Node t4 = new Node(50);
        t4.left  = new Node(30);
        t4.right = new Node(70);
        t4.left.right = new Node(55); // 55 > 50，放在 left subtree，違規

        System.out.println("bugValidation   = " + bugValidation(t4));   // true（漏掉深層錯誤）
        System.out.println("fixedValidation = " + fixedValidation(t4)); // false（正確偵測）
    }
}