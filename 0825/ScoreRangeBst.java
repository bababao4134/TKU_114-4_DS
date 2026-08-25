public class ScoreRangeBst {

    static class StudentScore {
        int score;
        String studentId, name;
        StudentScore(int score, String studentId, String name) {
            this.score = score; this.studentId = studentId; this.name = name;
        }
        // 比較鍵：先 score 再 studentId（允許同分）
        int compareTo(StudentScore other) {
            if (this.score != other.score) return Integer.compare(this.score, other.score);
            return this.studentId.compareTo(other.studentId);
        }
        @Override public String toString() {
            return score + " " + studentId + " " + name;
        }
    }

    static class Node {
        StudentScore data; Node left, right;
        Node(StudentScore s) { data = s; }
    }

    static Node root;

    static boolean add(StudentScore s) {
        if (root == null) { root = new Node(s); return true; }
        Node cur = root;
        while (true) {
            int cmp = s.compareTo(cur.data);
            if (cmp == 0)  return false;
            if (cmp < 0) {
                if (cur.left  == null) { cur.left  = new Node(s); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(s); return true; }
                cur = cur.right;
            }
        }
    }

    // 輸出 score 在 [low, high] 的所有記錄
    static void printRange(Node n, int low, int high) {
        if (n == null) return;
        if (n.data.score > low)  printRange(n.left,  low, high);
        if (n.data.score >= low && n.data.score <= high) System.out.println(n.data);
        if (n.data.score < high) printRange(n.right, low, high);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.println(n.data); inorder(n.right);
    }

    public static void main(String[] args) {
        add(new StudentScore(90, "S003", "Cara"));
        add(new StudentScore(75, "S001", "Amy"));
        add(new StudentScore(90, "S005", "Eve")); // 同分不同 id
        add(new StudentScore(88, "S002", "Ben"));
        add(new StudentScore(60, "S004", "Dave"));
        add(new StudentScore(75, "S006", "Frank")); // 同分不同 id

        System.out.println("=== 全部（inorder）===");
        inorder(root);

        System.out.println("\n=== range [75, 90] ===");
        printRange(root, 75, 90);

        System.out.println("\n=== range [90, 90] ===");
        printRange(root, 90, 90);

        System.out.println("\n=== range [95, 100]（空）===");
        printRange(root, 95, 100);
        System.out.println("(no result)");
    }
}