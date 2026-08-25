public class BstRangeReport {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node root;

    static void add(int v) {
        if (root == null) { root = new Node(v); return; }
        Node cur = root;
        while (true) {
            if (v == cur.value) return;
            if (v < cur.value) {
                if (cur.left  == null) { cur.left  = new Node(v); return; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(v); return; }
                cur = cur.right;
            }
        }
    }

    static Integer minimum() {
        if (root == null) return null;
        Node cur = root;
        while (cur.left != null) cur = cur.left;
        return cur.value;
    }

    static Integer maximum() {
        if (root == null) return null;
        Node cur = root;
        while (cur.right != null) cur = cur.right;
        return cur.value;
    }

    // 輸出 [low, high] 範圍內的值（含端點）
    static void printRange(Node n, int low, int high) {
        if (n == null) return;
        if (n.value > low)  printRange(n.left,  low, high); // left 可能有符合的
        if (n.value >= low && n.value <= high) System.out.print(n.value + " ");
        if (n.value < high) printRange(n.right, low, high); // right 可能有符合的
    }

    public static void main(String[] args) {
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80, 10, 25, 45, 65}) add(v);

        System.out.println("min=" + minimum());
        System.out.println("max=" + maximum());

        System.out.print("range [25,65]: ");
        printRange(root, 25, 65);
        System.out.println();

        System.out.print("range [50,50]: ");
        printRange(root, 50, 50);
        System.out.println();

        System.out.print("range [90,99]: "); // 空範圍
        printRange(root, 90, 99);
        System.out.println("(empty)");

        // low > high → 不輸出任何值
        System.out.print("range [70,30]: ");
        if (70 > 30) System.out.println("(invalid: low > high)");
        else printRange(root, 70, 30);
    }
}