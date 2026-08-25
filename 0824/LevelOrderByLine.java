import java.util.*;

public class LevelOrderByLine {

    static class Node {
        String value;
        Node left, right;
        Node(String v) { value = v; }
    }

    static void levelOrderByLine(Node root) {
        if (root == null) { System.out.println("(empty)"); return; }

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        int level = 0;
        while (!queue.isEmpty()) {
            int count = queue.size(); // 目前層的 node 數
            System.out.print("Level " + level + "（" + count + " 個）: ");
            for (int i = 0; i < count; i++) {
                Node cur = queue.poll();
                System.out.print(cur.value + " ");
                if (cur.left  != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        Node root = new Node("A");
        root.left = new Node("B"); root.right = new Node("C");
        root.left.left = new Node("D"); root.left.right = new Node("E");
        root.right.right = new Node("F");

        System.out.println("=== 7-node tree ===");
        levelOrderByLine(root);

        System.out.println("\n=== empty tree ===");
        levelOrderByLine(null);

        System.out.println("\n=== single-node ===");
        levelOrderByLine(new Node("X"));
    }
}