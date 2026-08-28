public class TraversalSelector {

    static class Node {
        String value;
        Node left, right;
        boolean isOperator;

        Node(String value, boolean isOperator) {
            this.value = value;
            this.isOperator = isOperator;
        }
    }

    // Preorder → prefix（前序：根 左 右）
    static String prefix(Node n) {
        if (n == null) return "";
        return n.value + " " + prefix(n.left) + prefix(n.right);
    }

    // Inorder → infix（中序：左 根 右，加括號）
    static String infix(Node n) {
        if (n == null) return "";
        if (!n.isOperator) return n.value;
        return "(" + infix(n.left) + " " + n.value + " " + infix(n.right) + ")";
    }

    // Postorder → postfix（後序：左 右 根）
    static String postfix(Node n) {
        if (n == null) return "";
        return postfix(n.left) + postfix(n.right) + n.value + " ";
    }

    public static void main(String[] args) {
        // 建立 expression tree：(3 + 4) * (5 - 2)
        //          *
        //        /   \
        //       +     -
        //      / \   / \
        //     3   4 5   2
        Node root = new Node("*", true);
        root.left  = new Node("+", true);
        root.right = new Node("-", true);
        root.left.left   = new Node("3", false);
        root.left.right  = new Node("4", false);
        root.right.left  = new Node("5", false);
        root.right.right = new Node("2", false);

        System.out.println("=== Expression Tree: (3+4)*(5-2) ===");
        System.out.println("prefix（preorder） : " + prefix(root).trim());
        System.out.println("infix（inorder）   : " + infix(root));
        System.out.println("postfix（postorder）: " + postfix(root).trim());

        // 第二棵樹：2 + 3 * 4
        //      +
        //     / \
        //    2   *
        //       / \
        //      3   4
        Node root2 = new Node("+", true);
        root2.left  = new Node("2", false);
        root2.right = new Node("*", true);
        root2.right.left  = new Node("3", false);
        root2.right.right = new Node("4", false);

        System.out.println("\n=== Expression Tree: 2+(3*4) ===");
        System.out.println("prefix : " + prefix(root2).trim());
        System.out.println("infix  : " + infix(root2));
        System.out.println("postfix: " + postfix(root2).trim());
    }
}