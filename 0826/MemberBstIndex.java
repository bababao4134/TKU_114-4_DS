import java.util.*;

public class MemberBstIndex {

    static class Member {
        final int    memberId;
        final String name;
              String email;

        Member(int memberId, String name, String email) {
            if (memberId <= 0)
                throw new IllegalArgumentException("memberId must > 0");
            if (name == null || name.isBlank())
                throw new IllegalArgumentException("name invalid");
            if (email == null || email.isBlank())
                throw new IllegalArgumentException("email invalid");
            this.memberId = memberId;
            this.name     = name;
            this.email    = email;
        }

        @Override
        public String toString() { return memberId + "|" + name + "|" + email; }
    }

    static class Node {
        Member data; Node left, right;
        Node(Member m) { data = m; }
    }

    static Node root;

    static boolean add(Member m) {
        if (m == null) return false;
        if (root == null) { root = new Node(m); return true; }
        Node cur = root;
        while (true) {
            int cmp = Integer.compare(m.memberId, cur.data.memberId);
            if (cmp == 0)  return false;
            if (cmp < 0) {
                if (cur.left  == null) { cur.left  = new Node(m); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(m); return true; }
                cur = cur.right;
            }
        }
    }

    static Member find(int id) {
        Node cur = root;
        while (cur != null) {
            int cmp = Integer.compare(id, cur.data.memberId);
            if (cmp == 0) return cur.data;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    static boolean updateEmail(int id, String email) {
        if (email == null || email.isBlank()) return false;
        Member m = find(id);
        if (m == null) return false;
        m.email = email;
        return true;
    }

    static boolean remove(int id) {
        if (find(id) == null) return false;
        root = removeNode(root, id);
        return true;
    }

    static Node removeNode(Node n, int id) {
        if (n == null) return null;
        int cmp = Integer.compare(id, n.data.memberId);
        if      (cmp < 0) n.left  = removeNode(n.left,  id);
        else if (cmp > 0) n.right = removeNode(n.right, id);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.data = succ.data;
            n.right = removeNode(n.right, succ.data.memberId);
        }
        return n;
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.println(n.data);
        inorder(n.right);
    }

    public static void main(String[] args) {
        System.out.println("=== 新增會員 ===");
        System.out.println(add(new Member(300, "Mina", "mina@mail.com")));
        System.out.println(add(new Member(100, "Leo",  "leo@mail.com")));
        System.out.println(add(new Member(500, "Nora", "nora@mail.com")));
        System.out.println(add(new Member(200, "Ivy",  "ivy@mail.com")));
        System.out.println(add(new Member(100, "Dup",  "dup@mail.com"))); // 重複 false

        System.out.println("\n=== Inorder ===");
        inorder(root);

        System.out.println("\n=== 搜尋 ===");
        System.out.println(find(200));
        System.out.println(find(999));

        System.out.println("\n=== 更新 Email ===");
        System.out.println(updateEmail(200, "ivy_new@mail.com")); // true
        System.out.println(updateEmail(200, "  "));               // false（blank）
        System.out.println(updateEmail(999, "x@y.com"));           // false（找不到）

        System.out.println("\n=== 刪除 ===");
        System.out.println(remove(300)); // true
        System.out.println(remove(999)); // false

        System.out.println("\n=== 最終 Inorder ===");
        inorder(root);

        // null / 非法 Member
        try {
            new Member(0, "X", "x@y.com");
        } catch (IllegalArgumentException e) {
            System.out.println("id=0 caught");
        }
        try {
            new Member(1, null, "x@y.com");
        } catch (IllegalArgumentException e) {
            System.out.println("null name caught");
        }
    }
}