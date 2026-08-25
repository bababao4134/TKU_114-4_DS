public class BstDuplicateCounter {

    static class Node {
        int value, count;
        Node left, right;
        Node(int v) { value = v; count = 1; }
    }

    static Node root;

    static void add(int v) {
        if (root == null) { root = new Node(v); return; }
        Node cur = root;
        while (true) {
            if (v == cur.value) { cur.count++; return; } // 相同 key 累加 count
            if (v < cur.value) {
                if (cur.left  == null) { cur.left  = new Node(v); return; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(v); return; }
                cur = cur.right;
            }
        }
    }

    // inorder 輸出 key(count)
    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.value + "(" + n.count + ") ");
        inorder(n.right);
    }

    static int size(Node n) {
        return n == null ? 0 : 1 + size(n.left) + size(n.right);
    }

    static int totalCount(Node n) {
        return n == null ? 0 : n.count + totalCount(n.left) + totalCount(n.right);
    }

    public static void main(String[] args) {
        int[] data = {50, 30, 70, 30, 50, 20, 70, 70, 40};
        for (int v : data) add(v);

        inorder(root);
        System.out.println();
        System.out.println("unique nodes: " + size(root));
        System.out.println("total inserts: " + totalCount(root));
    }
}