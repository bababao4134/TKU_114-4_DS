public class OrderBstSystem {

    static class Order {
        int    orderId;
        String customer;
        int    amount;
        boolean cancelled;

        Order(int orderId, String customer, int amount) {
            this.orderId   = orderId;
            this.customer  = customer;
            this.amount    = Math.max(0, amount);
            this.cancelled = false;
        }

        @Override public String toString() {
            return orderId + " " + customer + " $" + amount
                 + (cancelled ? " [CANCELLED]" : "");
        }
    }

    static class Node {
        Order data; Node left, right;
        Node(Order o) { data = o; }
    }

    static Node root;
    static int totalCount = 0, totalAmount = 0;

    static boolean add(Order o) {
        if (o == null) return false;
        if (root == null) { root = new Node(o); update(o, 1); return true; }
        Node cur = root;
        while (true) {
            if (o.orderId == cur.data.orderId) return false;
            if (o.orderId < cur.data.orderId) {
                if (cur.left  == null) { cur.left  = new Node(o); update(o, 1); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(o); update(o, 1); return true; }
                cur = cur.right;
            }
        }
    }

    static void update(Order o, int sign) {
        if (!o.cancelled) { totalCount += sign; totalAmount += sign * o.amount; }
    }

    static Order find(int id) {
        Node cur = root;
        while (cur != null) {
            if (id == cur.data.orderId) return cur.data;
            cur = id < cur.data.orderId ? cur.left : cur.right;
        }
        return null;
    }

    static boolean cancel(int id) {
        Order o = find(id);
        if (o == null || o.cancelled) return false;
        o.cancelled = true;
        totalCount--; totalAmount -= o.amount;
        return true;
    }

    static boolean updateAmount(int id, int newAmount) {
        if (newAmount < 0) return false;
        Order o = find(id);
        if (o == null || o.cancelled) return false;
        totalAmount += (newAmount - o.amount);
        o.amount = newAmount;
        return true;
    }

    // 輸出 orderId 在 [low, high] 範圍的訂單
    static void printRange(Node n, int low, int high) {
        if (n == null) return;
        if (n.data.orderId > low)  printRange(n.left,  low, high);
        if (n.data.orderId >= low && n.data.orderId <= high) System.out.println(n.data);
        if (n.data.orderId < high) printRange(n.right, low, high);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.println(n.data); inorder(n.right);
    }

    static void printSummary() {
        System.out.println("=== Summary ===");
        System.out.println("active orders: " + totalCount);
        System.out.println("total amount : $" + totalAmount);
    }

    public static void main(String[] args) {
        System.out.println("=== 新增訂單 ===");
        System.out.println(add(new Order(300, "Amy",  1500)));
        System.out.println(add(new Order(100, "Ben",   800)));
        System.out.println(add(new Order(500, "Cara", 3200)));
        System.out.println(add(new Order(200, "Dave",  600)));
        System.out.println(add(new Order(300, "Dup",     0))); // 重複

        System.out.println("\n=== inorder ===");
        inorder(root);

        System.out.println("\n=== 查詢 200 ===");
        System.out.println(find(200));
        System.out.println("find 999: " + find(999));

        System.out.println("\n=== 取消 100 ===");
        System.out.println("cancel 100: " + cancel(100));
        System.out.println("cancel 999: " + cancel(999));

        System.out.println("\n=== 修改金額 300 -> 2000 ===");
        System.out.println("update 300: " + updateAmount(300, 2000));
        System.out.println("update 100（已取消）: " + updateAmount(100, 9999));

        System.out.println("\n=== range [100, 350] ===");
        printRange(root, 100, 350);

        printSummary();
    }
}