import java.util.*;

public class Q09_TreeTraversal {

    public static class Node {
        public int  value;
        public Node left;
        public Node right;
        public Node(int value) { this.value = value; }
    }

    public static List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(Node n, List<Integer> result) {
        if (n == null) return;
        result.add(n.value);
        preorderHelper(n.left,  result);
        preorderHelper(n.right, result);
    }

    public static List<Integer> inorder(Node root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(Node n, List<Integer> result) {
        if (n == null) return;
        inorderHelper(n.left, result);
        result.add(n.value);
        inorderHelper(n.right, result);
    }

    public static List<Integer> postorder(Node root) {
        List<Integer> walkRecordP09 = new ArrayList<>();
        postorderHelper(root, walkRecordP09);
        return walkRecordP09;
    }

    private static void postorderHelper(Node n, List<Integer> result) {
        if (n == null) return;
        postorderHelper(n.left,  result);
        postorderHelper(n.right, result);
        result.add(n.value);
    }

    public static List<Integer> levelOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            result.add(cur.value);
            if (cur.left  != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
        }
        return result;
    }

    public static void main(String[] args) {
        //        8
        //      /   \
        //     4     12
        //    / \      \
        //   2   6      14
        Node root = new Node(8);
        root.left  = new Node(4);
        root.right = new Node(12);
        root.left.left   = new Node(2);
        root.left.right  = new Node(6);
        root.right.right = new Node(14);

        System.out.println(preorder(root));   // [8, 4, 2, 6, 12, 14]
        System.out.println(inorder(root));    // [2, 4, 6, 8, 12, 14]
        System.out.println(postorder(root));  // [2, 6, 4, 14, 12, 8]
        System.out.println(levelOrder(root)); // [8, 4, 12, 2, 6, 14]

        // null root 回傳 empty list
        System.out.println(preorder(null));   // []
        System.out.println(inorder(null));    // []
        System.out.println(postorder(null));  // []
        System.out.println(levelOrder(null)); // []

        // 重複呼叫結果不殘留
        System.out.println(inorder(root));    // [2, 4, 6, 8, 12, 14]
        System.out.println(inorder(root));    // [2, 4, 6, 8, 12, 14]
    }
}