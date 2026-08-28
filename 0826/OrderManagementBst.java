import java.util.*;

public class OrderManagementBst {

    static class Order {
        final int    orderId;
              String customer;
              int    amount;
              String status; // PENDING / COMPLETED / CANCELLED

        Order(int orderId, String customer, int amount) {
            if (orderId <= 0)                         throw new IllegalArgumentException("orderId > 0");
            if (customer == null || customer.isBlank()) throw new IllegalArgumentException("customer invalid");
            if (amount < 0)                           throw new IllegalArgumentException("amount >= 0");
            this.orderId  = orderId;
            this.customer = customer;
            this.amount   = amount;
            this.status   = "PENDING";
        }

        @Override
        public String toString() {
            return orderId + "|" + customer + "|$" + amount + "|" + status;
        }
    }

    static class Node { Order data; Node left, right; Node(Order o){data=o;} }

    static Node root;

    static boolean add(Order o) {
        if (o == null) return false;
        if (root == null) { root = new Node(o); return true; }
        Node cur = root;
        while (true) {
            int cmp = Integer.compare(o.orderId, cur.data.orderId);
            if (cmp == 0) return false;
            if (cmp < 0) { if(cur.left ==null){cur.left =new Node(o);return true;} cur=cur.left; }
            else         { if(cur.right==null){cur.right=new Node(o);return true;} cur=cur.right; }
        }
    }

    static Order find(int id) {
        Node cur = root;
        while (cur != null) {
            int cmp = Integer.compare(id, cur.data.orderId);
            if (cmp == 0) return cur.data;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    static boolean updateStatus(int id, String status) {
        if (status == null || status.isBlank()) return false;
        Order o = find(id);
        if (o == null) return false;
        o.status = status;
        return true;
    }

    static boolean cancel(int id) {
        Order o = find(id);
        if (o == null) return false;
        o.status = "CANCELLED";
        return true;
    }

    // 只有 CANCELLED 訂單可以 remove
    static boolean remove(int id) {
        Order o = find(id);
        if (o == null || !"CANCELLED".equals(o.status)) return false;
        root = removeNode(root, id);
        return true;
    }

    static Node removeNode(Node n, int id) {
        if (n == null) return null;
        int cmp = Integer.compare(id, n.data.orderId);
        if      (cmp < 0) n.left  = removeNode(n.left,  id);
        else if (cmp > 0) n.right = removeNode(n.right, id);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.data = succ.data;
            n.right = removeNode(n.right, succ.data.orderId);
        }
        return n;
    }

    // id range report
    static void idRange(Node n, int low, int high) {
        if (n == null) return;
        if (n.data.orderId > low)  idRange(n.left,  low, high);
        if (n.data.orderId >= low && n.data.orderId <= high) System.out.println(n.data);
        if (n.data.orderId < high) idRange(n.right, low, high);
    }

    static long totalAmount(Node n) {
        if (n == null) return 0;
        return n.data.amount + totalAmount(n.left) + totalAmount(n.right);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.println(n.data); inorder(n.right);
    }

    public static void main(String[] args) {
        add(new Order(300, "Amy",  1500));
        add(new Order(100, "Ben",   800));
        add(new Order(500, "Cara", 3200));
        add(new Order(200, "Dave",  600));

        System.out.println("=== 初始 Inorder ===");
        inorder(root);

        System.out.println("\n=== 更新狀態 ===");
        System.out.println(updateStatus(100, "COMPLETED")); // true
        System.out.println(updateStatus(999, "COMPLETED")); // false

        System.out.println("\n=== 取消 ===");
        System.out.println(cancel(300)); // true

        System.out.println("\n=== 刪除（只有 CANCELLED 可刪）===");
        System.out.println(remove(100)); // false（COMPLETED 不能刪）
        System.out.println(remove(300)); // true

        System.out.println("\n=== Range [100, 300] ===");
        idRange(root, 100, 300);

        System.out.println("\nTotal amount: $" + totalAmount(root));

        System.out.println("\n=== 最終 Inorder ===");
        inorder(root);

        // amount 不得為負
        try {
            new Order(999, "X", -100);
        } catch (IllegalArgumentException e) {
            System.out.println("negative amount caught");
        }
    }
}