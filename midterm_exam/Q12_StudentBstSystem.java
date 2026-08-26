import java.util.*;

public class Q12_StudentBstSystem {

    public static class Student {
        private final int    id;
        private final String name;
        private       int    score;

        public Student(int id, String name, int score) {
            if (id <= 0)                        throw new IllegalArgumentException("id must be > 0");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name invalid");
            this.id    = id;
            this.name  = name;
            this.score = Math.min(100, Math.max(0, score));
        }

        public int    getId()    { return id;    }
        public String getName()  { return name;  }
        public int    getScore() { return score; }
        void          setScore(int s) { score = Math.min(100, Math.max(0, s)); }

        @Override
        public String toString() { return id + "|" + name + "|" + score; }
    }

    private static class Node {
        Student data; Node left, right;
        Node(Student s) { data = s; }
    }

    private Node root;

    public boolean add(Student student) {
        if (student == null) return false;
        if (root == null) { root = new Node(student); return true; }
        Node cur = root;
        while (true) {
            int cmp = Integer.compare(student.getId(), cur.data.getId());
            if (cmp == 0) return false;
            if (cmp < 0) {
                if (cur.left  == null) { cur.left  = new Node(student); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(student); return true; }
                cur = cur.right;
            }
        }
    }

    public Student find(int id) {
        Node cur = root;
        while (cur != null) {
            int cmp = Integer.compare(id, cur.data.getId());
            if (cmp == 0) return cur.data;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student s = find(id);
        if (s == null) return false;
        s.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) return false;
        root = removeNode(root, id);
        return true;
    }

    private Node removeNode(Node n, int id) {
        if (n == null) return null;
        int cmp = Integer.compare(id, n.data.getId());
        if      (cmp < 0) n.left  = removeNode(n.left,  id);
        else if (cmp > 0) n.right = removeNode(n.right, id);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.data = succ.data;
            n.right = removeNode(n.right, succ.data.getId());
        }
        return n;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        // student-index-check S12-88
        List<Student> result = new ArrayList<>();
        if (lowId > highId) return result;
        rangeHelper(root, lowId, highId, result);
        return result;
    }

    private void rangeHelper(Node n, int low, int high, List<Student> result) {
        if (n == null) return;
        int id = n.data.getId();
        if (id > low)  rangeHelper(n.left,  low, high, result);
        if (id >= low && id <= high) result.add(n.data);
        if (id < high) rangeHelper(n.right, low, high, result);
    }

    public List<Student> inorder() {
        List<Student> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node n, List<Student> result) {
        if (n == null) return;
        inorderHelper(n.left, result);
        result.add(n.data);
        inorderHelper(n.right, result);
    }

    public static void main(String[] args) {
        Q12_StudentBstSystem system = new Q12_StudentBstSystem();
        system.add(new Student(300, "Mina", 78));
        system.add(new Student(100, "Leo",  84));
        system.add(new Student(500, "Nora", 105)); // score 超過 100 以 100 儲存
        system.add(new Student(200, "Ivy",  69));

        System.out.println(system.updateScore(200, 88));          // true
        System.out.println(system.studentsBetween(150, 500));     // [200|Ivy|88, 300|Mina|78, 500|Nora|100]
        System.out.println(system.remove(300));                   // true
        System.out.println(system.inorder());                     // [100|Leo|84, 200|Ivy|88, 500|Nora|100]

        // 邊界測試
        System.out.println(system.add(null));                     // false
        System.out.println(system.add(new Student(100, "Dup", 50))); // false（重複 id）
        System.out.println(system.find(999));                     // null
        System.out.println(system.updateScore(999, 90));          // false
        System.out.println(system.remove(999));                   // false

        // lowId > highId 回傳 empty
        System.out.println(system.studentsBetween(500, 100));     // []

        // score 邊界
        system.updateScore(100, -10);  // 儲存為 0
        System.out.println(system.find(100));   // 100|Leo|0

        // Student constructor 驗證
        try {
            new Student(0, "X", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("id=0 caught");   // id=0 caught
        }
        try {
            new Student(1, null, 50);
        } catch (IllegalArgumentException e) {
            System.out.println("null name caught"); // null name caught
        }
    }
}