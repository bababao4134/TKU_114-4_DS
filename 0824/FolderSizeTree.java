public class FolderSizeTree {

    static class FolderNode {
        String name;
        int    ownSize;
        FolderNode left, right;

        FolderNode(String name, int ownSize) {
            this.name    = name;
            this.ownSize = ownSize;
        }
    }

    // postorder 計算 subtree 總大小
    static int subtreeSize(FolderNode n) {
        if (n == null) return 0;
        int leftSize  = subtreeSize(n.left);
        int rightSize = subtreeSize(n.right);
        int total = n.ownSize + leftSize + rightSize;
        System.out.printf("  %-12s ownSize=%4d subtreeTotal=%4d%n",
                n.name, n.ownSize, total);
        return total;
    }

    // 最大 subtree 的 root（以 subtreeSize 比較）
    static FolderNode maxSubtreeNode(FolderNode n) {
        if (n == null) return null;
        FolderNode best = n;
        int bestSize = subtreeSizeQuiet(n);
        FolderNode leftBest  = maxSubtreeNode(n.left);
        FolderNode rightBest = maxSubtreeNode(n.right);
        if (leftBest  != null && subtreeSizeQuiet(leftBest)  > bestSize) { best = leftBest;  bestSize = subtreeSizeQuiet(leftBest); }
        if (rightBest != null && subtreeSizeQuiet(rightBest) > bestSize) best = rightBest;
        return best;
    }

    // 不印 log 的 subtreeSize
    static int subtreeSizeQuiet(FolderNode n) {
        if (n == null) return 0;
        return n.ownSize + subtreeSizeQuiet(n.left) + subtreeSizeQuiet(n.right);
    }

    static void printLeaves(FolderNode n) {
        if (n == null) return;
        if (n.left == null && n.right == null) {
            System.out.println("  leaf: " + n.name + " size=" + n.ownSize);
            return;
        }
        printLeaves(n.left);
        printLeaves(n.right);
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("Root",    10);
        root.left  = new FolderNode("Documents", 50);
        root.right = new FolderNode("Downloads", 200);
        root.left.left  = new FolderNode("Work",   80);
        root.left.right = new FolderNode("Photos", 300);
        root.right.left = new FolderNode("Movies", 500);

        System.out.println("=== Postorder size calculation ===");
        int total = subtreeSize(root);
        System.out.println("\n總大小：" + total);

        FolderNode maxNode = maxSubtreeNode(root);
        System.out.println("最大 subtree 的根：" + maxNode.name
                + "（subtreeSize=" + subtreeSizeQuiet(maxNode) + "）");

        System.out.println("\nLeaf folders：");
        printLeaves(root);
    }
}