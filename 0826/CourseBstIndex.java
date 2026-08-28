import java.util.*;

public class CourseBstIndex {

    static class Course {
        final String code;
              String title;
              int    credit;

        Course(String code, String title, int credit) {
            if (code  == null || code.isBlank())  throw new IllegalArgumentException("code invalid");
            if (title == null || title.isBlank()) throw new IllegalArgumentException("title invalid");
            this.code   = code;
            this.title  = title;
            this.credit = Math.min(6, Math.max(1, credit));
        }

        @Override
        public String toString() { return code + "|" + title + "|" + credit; }
    }

    static class Node {
        Course data; Node left, right;
        Node(Course c) { data = c; }
    }

    static Node root;

    static boolean add(Course c) {
        if (c == null) return false;
        if (root == null) { root = new Node(c); return true; }
        Node cur = root;
        while (true) {
            int cmp = c.code.compareTo(cur.data.code);
            if (cmp == 0)  return false;
            if (cmp < 0) {
                if (cur.left  == null) { cur.left  = new Node(c); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(c); return true; }
                cur = cur.right;
            }
        }
    }

    static Course find(String code) {
        Node cur = root;
        while (cur != null) {
            int cmp = code.compareTo(cur.data.code);
            if (cmp == 0) return cur.data;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    static boolean updateCredit(String code, int credit) {
        Course c = find(code);
        if (c == null) return false;
        if (credit < 1 || credit > 6) return false;
        c.credit = credit;
        return true;
    }

    static boolean remove(String code) {
        if (find(code) == null) return false;
        root = removeNode(root, code);
        return true;
    }

    static Node removeNode(Node n, String code) {
        if (n == null) return null;
        int cmp = code.compareTo(n.data.code);
        if      (cmp < 0) n.left  = removeNode(n.left,  code);
        else if (cmp > 0) n.right = removeNode(n.right, code);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.data = succ.data;
            n.right = removeNode(n.right, succ.data.code);
        }
        return n;
    }

    // code range query [low, high]
    static List<Course> codeRange(String low, String high) {
        List<Course> result = new ArrayList<>();
        if (low.compareTo(high) > 0) return result;
        rangeHelper(root, low, high, result);
        return result;
    }

    static void rangeHelper(Node n, String low, String high, List<Course> result) {
        if (n == null) return;
        if (n.data.code.compareTo(low)  > 0) rangeHelper(n.left,  low, high, result);
        if (n.data.code.compareTo(low)  >= 0
         && n.data.code.compareTo(high) <= 0) result.add(n.data);
        if (n.data.code.compareTo(high) < 0) rangeHelper(n.right, low, high, result);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.println(n.data); inorder(n.right);
    }

    public static void main(String[] args) {
        add(new Course("CS201", "Data Structures", 3));
        add(new Course("CS101", "Intro to Java",   3));
        add(new Course("CS301", "Algorithm",       3));
        add(new Course("CS102", "OOP",             2));
        add(new Course("CS201", "Dup",             1)); // 重複

        System.out.println("=== Inorder ===");
        inorder(root);

        System.out.println("\n=== 搜尋 ===");
        System.out.println(find("CS201"));
        System.out.println(find("CS999"));

        System.out.println("\n=== 更新學分 ===");
        System.out.println(updateCredit("CS101", 4));  // true
        System.out.println(updateCredit("CS101", 7));  // false（超過 6）
        System.out.println(updateCredit("CS999", 2));  // false（找不到）

        System.out.println("\n=== Code Range [CS101, CS201] ===");
        codeRange("CS101", "CS201").forEach(System.out::println);

        System.out.println("\n=== 刪除 CS201 ===");
        System.out.println(remove("CS201"));

        System.out.println("\n=== 最終排序報表 ===");
        inorder(root);
    }
}