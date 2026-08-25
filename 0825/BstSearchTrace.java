public class BstSearchTrace {

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

    // 輸出每次比較的 current value、方向與 comparison count
    static boolean searchTrace(int target) {
        System.out.print("search " + target + ": ");
        Node cur = root;
        int count = 0;
        while (cur != null) {
            count++;
            System.out.print("[" + cur.value + "]");
            if (target == cur.value) {
                System.out.println(" FOUND (comparisons=" + count + ")");
                return true;
            }
            if (target < cur.value) { System.out.print("->L "); cur = cur.left;  }
            else                    { System.out.print("->R "); cur = cur.right; }
        }
        System.out.println("null NOT_FOUND (comparisons=" + count + ")");
        return false;
    }

    public static void main(String[] args) {
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) add(v);

        searchTrace(50); // root
        searchTrace(20); // leaf
        searchTrace(30); // internal node
        searchTrace(65); // missing
        searchTrace(90); // missing（超出範圍）
    }
}