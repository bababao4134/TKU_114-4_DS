import java.util.*;

public class DirectoryTreeReport {

    static class FsNode {
        String  name;
        boolean isFile;
        long    ownSize;    // file 的大小；directory 本身大小通常為 0
        FsNode  left, right; // 這裡用二元樹模擬目錄結構

        FsNode(String name, boolean isFile, long ownSize) {
            this.name    = name;
            this.isFile  = isFile;
            this.ownSize = ownSize;
        }
    }

    static int  totalNodes;
    static int  fileCount;
    static int  dirCount;
    static long maxFileSize;
    static String maxFileName;

    // Postorder：先計算子節點，再合計
    static long postorderSize(FsNode n, int depth) {
        if (n == null) return 0;
        long left  = postorderSize(n.left,  depth + 1);
        long right = postorderSize(n.right, depth + 1);
        long total = n.ownSize + left + right;

        // 統計
        totalNodes++;
        if (n.isFile) {
            fileCount++;
            if (n.ownSize > maxFileSize) {
                maxFileSize = n.ownSize;
                maxFileName = n.name;
            }
        } else {
            dirCount++;
        }

        String indent = "  ".repeat(depth);
        System.out.printf("%s%s %s (totalSize=%d)%n",
                indent, n.isFile ? "[F]" : "[D]", n.name, total);
        return total;
    }

    static int height(FsNode n) {
        return n == null ? -1 : 1 + Math.max(height(n.left), height(n.right));
    }

    public static void main(String[] args) {
        // 目錄結構：
        //        Root(dir)
        //       /          \
        //   Documents(dir)  Downloads(dir)
        //   /       \           \
        // Work(dir) Report.pdf  Movie.mp4
        //   /
        // Code.java

        FsNode root = new FsNode("Root", false, 0);
        root.left  = new FsNode("Documents", false, 0);
        root.right = new FsNode("Downloads", false, 0);

        root.left.left  = new FsNode("Work", false, 0);
        root.left.right = new FsNode("Report.pdf", true, 2048);

        root.right.right = new FsNode("Movie.mp4", true, 204800);

        root.left.left.left = new FsNode("Code.java", true, 512);

        // 重置統計
        totalNodes = fileCount = dirCount = 0;
        maxFileSize = 0; maxFileName = "";

        System.out.println("=== Postorder 容量計算 ===");
        long totalSize = postorderSize(root, 0);

        System.out.println("\n=== 統計報表 ===");
        System.out.println("total size    : " + totalSize);
        System.out.println("total nodes   : " + totalNodes);
        System.out.println("file count    : " + fileCount);
        System.out.println("directory count: " + dirCount);
        System.out.println("height        : " + height(root));
        System.out.println("max file      : " + maxFileName + " (" + maxFileSize + " bytes)");
    }
}