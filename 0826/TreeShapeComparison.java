public class TreeShapeComparison {

    static class Node { int value; Node left, right; Node(int v){value=v;} }

    static Node insert(Node root, int v) {
        if (root == null) return new Node(v);
        if      (v < root.value) root.left  = insert(root.left,  v);
        else if (v > root.value) root.right = insert(root.right, v);
        return root;
    }

    static int height(Node n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    static int searchComp(Node root, int target) {
        int count = 0;
        Node cur = root;
        while (cur != null) {
            count++;
            if (target == cur.value) return count;
            cur = target < cur.value ? cur.left : cur.right;
        }
        return count;
    }

    static int totalSearchComp(Node root, int[] keys) {
        int total = 0;
        for (int k : keys) total += searchComp(root, k);
        return total;
    }

    // missing key：比較所有 key 後沒找到的步驟數
    static int missingComp(Node root, int target) {
        return searchComp(root, target);
    }

    public static void main(String[] args) {
        int[] keys = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85};
        int   missingKey = 99;

        // 升冪（right-skewed）
        int[] ascending = {10,20,25,30,35,40,45,50,55,60,65,70,75,80,85};
        Node t1 = null;
        for (int v : ascending) t1 = insert(t1, v);

        // 降冪（left-skewed）
        int[] descending = {85,80,75,70,65,60,55,50,45,40,35,30,25,20,10};
        Node t2 = null;
        for (int v : descending) t2 = insert(t2, v);

        // 接近平衡（中位數先插入）
        int[] balanced = {50,30,70,20,40,60,80,10,25,35,45,55,65,75,85};
        Node t3 = null;
        for (int v : balanced) t3 = insert(t3, v);

        System.out.println("=== Tree Shape Comparison（" + keys.length + " keys）===");
        System.out.printf("%-20s  height=%d  totalSearchComp=%d  missingComp=%d%n",
                "ascending(skewed)",  height(t1), totalSearchComp(t1, keys), missingComp(t1, missingKey));
        System.out.printf("%-20s  height=%d  totalSearchComp=%d  missingComp=%d%n",
                "descending(skewed)", height(t2), totalSearchComp(t2, keys), missingComp(t2, missingKey));
        System.out.printf("%-20s  height=%d  totalSearchComp=%d  missingComp=%d%n",
                "balanced-like",      height(t3), totalSearchComp(t3, keys), missingComp(t3, missingKey));

        System.out.println("\n=== 觀察 ===");
        System.out.println("升冪/降冪插入 → height=" + (keys.length - 1) + "，退化為 O(n)。");
        System.out.println("平衡插入 → height~log2(" + keys.length + ")≈"
                + (int)(Math.log(keys.length) / Math.log(2)) + "，接近 O(log n)。");
    }
}