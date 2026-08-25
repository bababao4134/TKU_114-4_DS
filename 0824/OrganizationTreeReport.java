import java.util.*;

public class OrganizationTreeReport {

    static class OrgNode {
        String name;
        OrgNode left, right;
        OrgNode(String n) { name = n; }
    }

    // findParent（找不到回傳 null）
    static OrgNode findParent(OrgNode root, String target) {
        if (root == null || target == null) return null;
        return parentHelper(root, target);
    }

    private static OrgNode parentHelper(OrgNode n, String target) {
        if (n == null) return null;
        if ((n.left  != null && n.left.name.equals(target)) ||
            (n.right != null && n.right.name.equals(target))) return n;
        OrgNode left  = parentHelper(n.left,  target);
        return left != null ? left : parentHelper(n.right, target);
    }

    // findDepth（找不到回傳 -1）
    static int findDepth(OrgNode n, String target, int depth) {
        if (n == null || target == null) return -1;
        if (n.name.equals(target)) return depth;
        int left = findDepth(n.left,  target, depth + 1);
        return left != -1 ? left : findDepth(n.right, target, depth + 1);
    }

    // pathFromRoot（找不到回傳 empty list）
    static List<String> pathFromRoot(OrgNode root, String target) {
        List<String> path = new ArrayList<>();
        pathHelper(root, target, path);
        return path;
    }

    private static boolean pathHelper(OrgNode n, String target, List<String> path) {
        if (n == null) return false;
        path.add(n.name);
        if (n.name.equals(target)) return true;
        if (pathHelper(n.left, target, path) || pathHelper(n.right, target, path)) return true;
        path.remove(path.size() - 1); // 回溯
        return false;
    }

    // printByLevel（逐層輸出）
    static void printByLevel(OrgNode root) {
        if (root == null) { System.out.println("(empty)"); return; }
        Queue<OrgNode> q = new ArrayDeque<>();
        q.offer(root);
        int level = 0;
        while (!q.isEmpty()) {
            int count = q.size();
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < count; i++) {
                OrgNode cur = q.poll();
                System.out.print(cur.name + " ");
                if (cur.left  != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            System.out.println(); level++;
        }
    }

    public static void main(String[] args) {
        OrgNode root = new OrgNode("HeadOffice");
        root.left  = new OrgNode("Sales");
        root.right = new OrgNode("Technology");
        root.left.left  = new OrgNode("Domestic");
        root.left.right = new OrgNode("Export");
        root.right.left = new OrgNode("Platform");
        root.right.right= new OrgNode("Support");

        System.out.println("=== 逐層報表 ===");
        printByLevel(root);

        System.out.println("\n=== findParent ===");
        OrgNode p1 = findParent(root, "Export");
        System.out.println("parent of Export   : " + (p1 == null ? "null" : p1.name));
        OrgNode p2 = findParent(root, "HeadOffice");
        System.out.println("parent of HeadOffice: " + (p2 == null ? "null（root）" : p2.name));
        OrgNode p3 = findParent(root, "HR");
        System.out.println("parent of HR       : " + (p3 == null ? "找不到" : p3.name));

        System.out.println("\n=== findDepth ===");
        System.out.println("depth of HeadOffice: " + findDepth(root, "HeadOffice", 0));
        System.out.println("depth of Platform  : " + findDepth(root, "Platform",   0));
        System.out.println("depth of HR        : " + findDepth(root, "HR",         0));

        System.out.println("\n=== pathFromRoot ===");
        System.out.println("path to Support : " + pathFromRoot(root, "Support"));
        System.out.println("path to HR      : " + pathFromRoot(root, "HR"));
        System.out.println("path to HeadOffice: " + pathFromRoot(root, "HeadOffice"));
    }
}