public class BinaryTreeStructureReport {

    static class Node {
        int value;
        Node left, right;
        Node(int v) { value = v; }
    }

    static int size(Node n)   { return n == null ? 0 : 1 + size(n.left) + size(n.right); }
    static int leaves(Node n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return leaves(n.left) + leaves(n.right);
    }
    static int height(Node n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    static void printLeaves(Node n) {
        if (n == null) return;
        if (n.left == null && n.right == null) { System.out.print(n.value + " "); return; }
        printLeaves(n.left);
        printLeaves(n.right);
    }

    static void report(String label, Node root) {
        System.out.println("=== " + label + " ===");
        System.out.println("root   = " + (root == null ? "null" : root.value));
        System.out.print  ("leaves = "); printLeaves(root); System.out.println();
        System.out.println("size   = " + size(root));
        System.out.println("leafCnt= " + leaves(root));
        System.out.println("height = " + height(root));
    }

    public static void main(String[] args) {
        // 7 個 node 的樹
        Node root = new Node(10);
        root.left  = new Node(5);
        root.right = new Node(20);
        root.left.left  = new Node(3);
        root.left.right = new Node(7);
        root.right.left = new Node(15);
        root.right.right= new Node(25);
        report("7-node tree", root);

        // Empty tree
        report("empty tree", null);

        // Single-node tree
        report("single-node", new Node(99));
    }
}