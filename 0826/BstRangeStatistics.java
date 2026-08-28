import java.util.*;

public class BstRangeStatistics {

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

    // 收集 [low, high] 內的值（依 BST 方向剪枝）
    static List<Integer> valuesBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low <= high) rangeHelper(root, low, high, result);
        return result;
    }

    static void rangeHelper(Node n, int low, int high, List<Integer> result) {
        if (n == null) return;
        if (n.value > low)  rangeHelper(n.left,  low, high, result);
        if (n.value >= low && n.value <= high) result.add(n.value);
        if (n.value < high) rangeHelper(n.right, low, high, result);
    }

    static int countBetween(int low, int high) {
        return valuesBetween(low, high).size();
    }

    static int sumBetween(int low, int high) {
        int total = 0;
        for (int v : valuesBetween(low, high)) total += v;
        return total;
    }

    public static void main(String[] args) {
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85})
            add(v);

        System.out.println("=== Range Statistics ===");

        int low = 35, high = 70;
        System.out.println("valuesBetween(" + low + "," + high + ") = " + valuesBetween(low, high));
        System.out.println("countBetween  = " + countBetween(low, high));
        System.out.println("sumBetween    = " + sumBetween(low, high));

        // 恰好端點
        System.out.println("\nrange [50,50] = " + valuesBetween(50, 50));
        System.out.println("count=" + countBetween(50, 50) + " sum=" + sumBetween(50, 50));

        // 空範圍（low > high）
        System.out.println("\nrange [70,35] = " + valuesBetween(70, 35)); // []
        System.out.println("count=" + countBetween(70, 35) + " sum=" + sumBetween(70, 35));

        // 超出範圍
        System.out.println("\nrange [90,99] = " + valuesBetween(90, 99)); // []
    }
}