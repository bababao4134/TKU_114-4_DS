public class BstInvariantChecker {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
        Node(int v, Node l, Node r) { value = v; left = l; right = r; }
    }

    static boolean isValid(Node n, long min, long max) {
        if (n == null) return true;
        if (n.value <= min || n.value >= max) return false;
        return isValid(n.left, min, n.value) && isValid(n.right, n.value, max);
    }

    static boolean validate(Node root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    static void check(String label, Node root) {
        System.out.println(label + " -> isValid=" + validate(root));
    }

    public static void main(String[] args) {
        // Valid tree
        Node valid = new Node(50,
            new Node(30, new Node(20), new Node(40)),
            new Node(70, new Node(60), new Node(80)));
        check("valid BST", valid);

        // 違規 1：右子 35 < root 50，但位在 right subtree（只看 parent-child 看不出）
        // 50 的 right subtree 裡放了 35，違反 > 50 的規則
        Node bad1 = new Node(50,
            new Node(30),
            new Node(35)); // 35 < 50，放右邊違規
        check("violation 1：right child 35 < root 50", bad1);

        // 違規 2：深層錯位，40 在 root=50 的 left subtree，但 40 的 right child 是 100
        // 100 > 50 卻在 root 的 left subtree
        Node bad2 = new Node(50,
            new Node(40, null, new Node(100)), // 100 > 50，放在 left subtree 違規
            new Node(70));
        check("violation 2：left subtree 內有 100 > root 50", bad2);

        // 違規 3：parent-child 看似合法，但不符合全域 boundary
        // root=10, left=5, left.right=15（15 > 10 卻在 left subtree）
        Node bad3 = new Node(10,
            new Node(5, null, new Node(15)), // 15 > 10，但在 left subtree
            new Node(20));
        check("violation 3：left subtree 內有 15 > root 10", bad3);

        // 違規 4：空 subtree 不違規，單 node 不違規
        check("null tree", null);
        check("single node 50", new Node(50));
    }
}