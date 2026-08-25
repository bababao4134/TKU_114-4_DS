public class ThreeTraversalPractice {

    static class Node {
        String value;
        Node left, right;
        Node(String v)               { value = v; }
        Node(String v, Node l, Node r) { value = v; left = l; right = r; }
    }

    static void preorder(Node n) {
        if (n == null) return;
        System.out.print(n.value + " ");
        preorder(n.left);
        preorder(n.right);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.value + " ");
        inorder(n.right);
    }

    static void postorder(Node n) {
        if (n == null) return;
        postorder(n.left);
        postorder(n.right);
        System.out.print(n.value + " ");
    }

    public static void main(String[] args) {
        // M(F(B,null), T(R,Z))
        Node root = new Node("M",
            new Node("F", new Node("B"), null),
            new Node("T", new Node("R"), new Node("Z")));

        System.out.print("preorder : "); preorder(root);  System.out.println();
        System.out.print("inorder  : "); inorder(root);   System.out.println();
        System.out.print("postorder: "); postorder(root); System.out.println();

        System.out.println("\n--- empty tree ---");
        System.out.print("preorder : "); preorder(null);  System.out.println("(done)");
    }
}