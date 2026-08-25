public class ProductInventoryBst {

    static class Product {
        int id, stock;
        String name;
        Product(int id, String name, int stock) {
            this.id = id; this.name = name;
            this.stock = Math.max(0, stock);
        }
        @Override public String toString() {
            return id + " " + name + " stock=" + stock;
        }
    }

    static class Node {
        Product data; Node left, right;
        Node(Product p) { data = p; }
    }

    static Node root;

    static boolean add(Product p) {
        if (p == null) return false;
        if (root == null) { root = new Node(p); return true; }
        Node cur = root;
        while (true) {
            if (p.id == cur.data.id) return false;
            if (p.id < cur.data.id) {
                if (cur.left  == null) { cur.left  = new Node(p); return true; }
                cur = cur.left;
            } else {
                if (cur.right == null) { cur.right = new Node(p); return true; }
                cur = cur.right;
            }
        }
    }

    static Product find(int id) {
        Node cur = root;
        while (cur != null) {
            if (id == cur.data.id) return cur.data;
            cur = id < cur.data.id ? cur.left : cur.right;
        }
        return null;
    }

    static boolean restock(int id, int qty) {
        if (qty <= 0) return false;
        Product p = find(id);
        if (p == null) return false;
        p.stock += qty;
        return true;
    }

    static boolean sell(int id, int qty) {
        if (qty <= 0) return false;
        Product p = find(id);
        if (p == null || p.stock < qty) return false;
        p.stock -= qty;
        return true;
    }

    static boolean remove(int id) {
        if (find(id) == null) return false;
        root = removeNode(root, id);
        return true;
    }

    static Node removeNode(Node n, int id) {
        if (n == null) return null;
        if      (id < n.data.id) n.left  = removeNode(n.left,  id);
        else if (id > n.data.id) n.right = removeNode(n.right, id);
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
        add(new Product(300, "Keyboard", 10));
        add(new Product(100, "Mouse",    20));
        add(new Product(500, "Monitor",   5));
        add(new Product(200, "Hub",       8));

        System.out.println("=== 初始報表 ===");
        inorder(root);

        System.out.println("\n補貨 100 +5: "  + restock(100, 5));
        System.out.println("扣庫存 300 -3: "  + sell(300, 3));
        System.out.println("扣庫存 500 -99: " + sell(500, 99)); // 庫存不足
        System.out.println("刪除 200: "        + remove(200));
        System.out.println("刪除 999: "        + remove(999));

        System.out.println("\n=== 最終報表 ===");
        inorder(root);
    }
}