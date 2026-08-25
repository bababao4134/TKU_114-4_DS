public class BstShapeExperiment {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node insert(Node root, int v) {
        if (root == null) return new Node(v);
        if      (v < root.value) root.left  = insert(root.left,  v);
        else if (v > root.value) root.right = insert(root.right, v);
        return root;
    }

    static int height(Node n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    static int searchComparisons(Node root, int target) {
        int count = 0;
        Node cur = root;
        while (cur != null) {
            count++;
            if (target == cur.value) return count;
            cur = target < cur.value ? cur.left : cur.right;
        }
        return count;
    }

    static int totalComparisons(Node root, int[] values) {
        int total = 0;
        for (int v : values) total += searchComparisons(root, v);
        return total;
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85};
        int n = values.length;

        // 順序一：升冪（right-skewed）
        int[] ascending = {10,20,25,30,35,40,45,50,55,60,65,70,75,80,85};
        Node t1 = null;
        for (int v : ascending) t1 = insert(t1, v);

        // 順序二：降冪（left-skewed）
        int[] descending = {85,80,75,70,65,60,55,50,45,40,35,30,25,20,10};
        Node t2 = null;
        for (int v : descending) t2 = insert(t2, v);

        // 順序三：中位數優先（接近平衡）
        int[] balanced = {50,25,75,12,37,62,87,6,18,31,43,56,68,81,93};
        // 用相同 15 個值中位數順序近似
        int[] midFirst = {50,30,70,20,40,60,80,10,25,35,45,55,65,75,85};
        Node t3 = null;
        for (int v : midFirst) t3 = insert(t3, v);

        System.out.println("=== Tree Shape Experiment（" + n + " nodes）===");
        System.out.printf("%-20s  height=%d  totalSearchComparisons=%d%n",
                "ascending(skewed)", height(t1), totalComparisons(t1, values));
        System.out.printf("%-20s  height=%d  totalSearchComparisons=%d%n",
                "descending(skewed)", height(t2), totalComparisons(t2, values));
        System.out.printf("%-20s  height=%d  totalSearchComparisons=%d%n",
                "balanced-like", height(t3), totalComparisons(t3, values));

        System.out.println("\n觀察結論：");
        System.out.println("· 升冪/降冪插入導致 skewed tree，height=" + (n - 1) + "，搜尋退化為 O(n)。");
        System.out.println("· 中位數順序插入接近平衡，height~log2(" + n + ")≈"
                + (int)(Math.log(n)/Math.log(2)) + "，搜尋接近 O(log n)。");
    }
}