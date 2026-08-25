public class MenuTreeSearch {

    static class Node {
        String label;
        Node left, right;
        Node(String l) { label = l; }
    }

    // 前序顯示（縮排表示層級）
    static void display(Node n, int depth) {
        if (n == null) return;
        System.out.println("  ".repeat(depth) + n.label);
        display(n.left,  depth + 1);
        display(n.right, depth + 1);
    }

    // 是否包含目標
    static boolean contains(Node n, String target) {
        if (n == null || target == null) return false;
        return n.label.equals(target)
            || contains(n.left, target)
            || contains(n.right, target);
    }

    // 目標深度（root = 0，找不到回傳 -1）
    static int findDepth(Node n, String target, int depth) {
        if (n == null || target == null) return -1;
        if (n.label.equals(target)) return depth;
        int left = findDepth(n.left,  target, depth + 1);
        if (left != -1) return left;
        return findDepth(n.right, target, depth + 1);
    }

    // Leaf 數量
    static int countLeaves(Node n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return countLeaves(n.left) + countLeaves(n.right);
    }

    public static void main(String[] args) {
        Node root = new Node("Home");
        root.left  = new Node("Products");
        root.right = new Node("About");
        root.left.left  = new Node("Category-A");
        root.left.right = new Node("Category-B");
        root.right.left = new Node("Team");

        System.out.println("=== 選單樹 ===");
        display(root, 0);

        System.out.println("\ncontains Products  : " + contains(root, "Products"));
        System.out.println("contains Cart      : " + contains(root, "Cart"));
        System.out.println("findDepth Home     : " + findDepth(root, "Home",      0));
        System.out.println("findDepth Category-A: " + findDepth(root, "Category-A", 0));
        System.out.println("findDepth Cart     : " + findDepth(root, "Cart",      0));
        System.out.println("countLeaves        : " + countLeaves(root));
    }
}