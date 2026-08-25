public class StudentBstIndex {

    static class Student {
        String id, name;
        double gpa;
        Student(String id, String name, double gpa) {
            this.id = id; this.name = name; this.gpa = gpa;
        }
        @Override public String toString() {
            return id + " " + name + " gpa=" + gpa;
        }
    }

    static class Node {
        Student data; Node left, right;
        Node(Student s) { data = s; }
    }

    static Node root;

    static boolean add(Student s) {
        if (s == null) return false;
        if (root == null) { root = new Node(s); return true; }
        Node cur = root;
        while (true) {
            int cmp = s.id.compareTo(cur.data.id);
            if (cmp == 0)  return false; // 重複 id 拒絕
            if (cmp < 0) {
                if (cur.left  == null) { cur.left  = new Node(s); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(s); return true; }
                cur = cur.right;
            }
        }
    }

    static Student find(String id) {
        Node cur = root;
        while (cur != null) {
            int cmp = id.compareTo(cur.data.id);
            if (cmp == 0) return cur.data;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    static boolean contains(String id) { return find(id) != null; }

    static boolean remove(String id) {
        if (!contains(id)) return false;
        root = removeNode(root, id);
        return true;
    }

    static Node removeNode(Node n, String id) {
        if (n == null) return null;
        int cmp = id.compareTo(n.data.id);
        if      (cmp < 0) n.left  = removeNode(n.left,  id);
        else if (cmp > 0) n.right = removeNode(n.right, id);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.data = succ.data;
            n.right = removeNode(n.right, succ.data.id);
        }
        return n;
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.println(n.data); inorder(n.right);
    }

    public static void main(String[] args) {
        System.out.println("=== 新增測試 ===");
        System.out.println(add(new Student("S003", "Cara", 3.8)));
        System.out.println(add(new Student("S001", "Amy",  3.5)));
        System.out.println(add(new Student("S005", "Eve",  3.9)));
        System.out.println(add(new Student("S002", "Ben",  3.2)));
        System.out.println(add(new Student("S001", "Dup",  0.0))); // 重複 false

        System.out.println("\n=== inorder（依 id 排序）===");
        inorder(root);

        System.out.println("\n=== 搜尋 ===");
        System.out.println("find S002: " + find("S002"));
        System.out.println("find S999: " + find("S999"));

        System.out.println("\n=== 刪除 ===");
        System.out.println("remove S001: " + remove("S001"));
        System.out.println("remove S999: " + remove("S999"));
        inorder(root);
    }
}