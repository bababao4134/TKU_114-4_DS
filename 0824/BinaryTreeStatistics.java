public class BinaryTreeStatistics {

    static class Node {
        int value;
        Node left, right;
        Node(int v) { value = v; }
    }

    static int size(Node n) {
        return n == null ? 0 : 1 + size(n.left) + size(n.right);
    }

    static int sum(Node n) {
        return n == null ? 0 : n.value + sum(n.left) + sum(n.right);
    }

    // maximum：empty tree 拋出例外
    static int maximum(Node n) {
        if (n == null) throw new IllegalStateException("empty tree has no maximum");
        return maxHelper(n, n.value);
    }

    private static int maxHelper(Node n, int current) {
        if (n == null) return current;
        int left  = maxHelper(n.left,  Math.max(current, n.value));
        int right = maxHelper(n.right, Math.max(current, n.value));
        return Math.max(left, right);
    }

    static int leafCount(Node n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return leafCount(n.left) + leafCount(n.right);
    }

    static int height(Node n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    static boolean contains(Node n, int target) {
        if (n == null) return false;
        return n.value == target || contains(n.left, target) || contains(n.right, target);
    }

    static void report(String label, Node root) {
        System.out.println("=== " + label + " ===");
        System.out.println("size     = " + size(root));
        System.out.println("sum      = " + sum(root));
        System.out.println("leafCount= " + leafCount(root));
        System.out.println("height   = " + height(root));
        try {
            System.out.println("maximum  = " + maximum(root));
        } catch (IllegalStateException e) {
            System.out.println("maximum  = " + e.getMessage());
        }
        System.out.println("contains 7 = " + contains(root, 7));
        System.out.println("contains 99= " + contains(root, 99));
    }

    public static void main(String[] args) {
        Node root = new Node(10);
        root.left  = new Node(5);
        root.right = new Node(20);
        root.left.left  = new Node(3);
        root.left.right = new Node(7);
        report("general tree", root);

        report("empty tree", null);
        report("single-node", new Node(42));
    }
}