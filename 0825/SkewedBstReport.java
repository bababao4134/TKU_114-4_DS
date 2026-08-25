public class SkewedBstReport {

    static class Node {
        int value; Node left, right;
        Node(int v) { value = v; }
    }

    static Node insert(Node root, int v) {
        if (root == null) return new Node(v);
        if (v < root.value) root.left  = insert(root.left,  v);
        else if (v > root.value) root.right = insert(root.right, v);
        return root;
    }

    static int height(Node n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    static int size(Node n) {
        return n == null ? 0 : 1 + size(n.left) + size(n.right);
    }

    // 搜尋並計算比較次數
    static int searchCount(Node n, int target) {
        int count = 0;
        while (n != null) {
            count++;
            if (target == n.value) return count;
            n = target < n.value ? n.left : n.right;
        }
        return count; // 找不到也回傳走了幾步
    }

    // 搜尋全部 value 的比較次數總和
    static int totalSearchCount(Node root, int[] values) {
        int total = 0;
        for (int v : values) total += searchCount(root, v);
        return total;
    }

    public static void main(String[] args) {
        int[] sorted   = {10, 20, 30, 40, 50, 60, 70}; // 升冪插入 -> skewed
        int[] balanced = {40, 20, 60, 10, 30, 50, 70}; // 平衡順序
        int[] reversed = {70, 60, 50, 40, 30, 20, 10}; // 降冪插入 -> skewed(左)

        Node skewedRight = null;
        for (int v : sorted)   skewedRight = insert(skewedRight, v);

        Node balancedTree = null;
        for (int v : balanced) balancedTree = insert(balancedTree, v);

        Node skewedLeft = null;
        for (int v : reversed) skewedLeft  = insert(skewedLeft,  v);

        System.out.println("=== 升冪插入（右傾 skewed）===");
        System.out.println("size=" + size(skewedRight) + " height=" + height(skewedRight));
        System.out.println("total search comparisons=" + totalSearchCount(skewedRight, sorted));

        System.out.println("\n=== 平衡順序插入 ===");
        System.out.println("size=" + size(balancedTree) + " height=" + height(balancedTree));
        System.out.println("total search comparisons=" + totalSearchCount(balancedTree, sorted));

        System.out.println("\n=== 降冪插入（左傾 skewed）===");
        System.out.println("size=" + size(skewedLeft) + " height=" + height(skewedLeft));
        System.out.println("total search comparisons=" + totalSearchCount(skewedLeft, sorted));

        System.out.println("\n觀察：skewed tree height 接近 n-1，搜尋退化為 O(n)；" + "平衡 tree height 約 log2(n)，搜尋為 O(log n)。");
    }
}