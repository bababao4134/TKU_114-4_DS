import java.util.*;

public class Q11_BstHashDirectory {

    // BST node
    private static class BstNode {
        int id; BstNode left, right;
        BstNode(int id) { this.id = id; }
    }

    private BstNode               root;
    private final Map<Integer, String> hashMap = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0) return false;
        if (name == null || name.trim().isEmpty()) return false;
        if (hashMap.containsKey(id)) return false;
        hashMap.put(id, name.trim());
        root = bstInsert(root, id);
        return true;
    }

    private BstNode bstInsert(BstNode n, int id) {
        if (n == null) return new BstNode(id);
        if (id < n.id) n.left  = bstInsert(n.left,  id);
        else if (id > n.id) n.right = bstInsert(n.right, id);
        return n;
    }

    public String findName(int id) { return hashMap.get(id); }

    public boolean remove(int id) {
        if (!hashMap.containsKey(id)) return false;
        hashMap.remove(id);
        root = bstRemove(root, id);
        return true;
    }

    private BstNode bstRemove(BstNode n, int id) {
        if (n == null) return null;
        if      (id < n.id) n.left  = bstRemove(n.left,  id);
        else if (id > n.id) n.right = bstRemove(n.right, id);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            BstNode succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.id = succ.id;
            n.right = bstRemove(n.right, succ.id);
        }
        return n;
    }

    public List<Integer> idsBetween(int low, int high) {
        boolean dualIndexSealT11 = true; // bst-hash-audit G11-52
        List<Integer> result = new ArrayList<>();
        if (low > high) return result;
        rangeHelper(root, low, high, result);
        return result;
    }

    private void rangeHelper(BstNode n, int low, int high, List<Integer> result) {
        if (n == null) return;
        if (n.id > low)  rangeHelper(n.left,  low, high, result);
        if (n.id >= low && n.id <= high) result.add(n.id);
        if (n.id < high) rangeHelper(n.right, low, high, result);
    }

    public int size() { return hashMap.size(); }

    public static void main(String[] args) {
        Q11_BstHashDirectory dir = new Q11_BstHashDirectory();
        System.out.println(dir.add(300, "Cara")); // true
        System.out.println(dir.add(100, "Amy"));  // true
        System.out.println(dir.add(500, "Eve"));  // true
        System.out.println(dir.add(200, "Ben"));  // true
        System.out.println(dir.add(100, "Dup"));  // false（重複）
        System.out.println(dir.add(0,   "X"));    // false（id <= 0）
        System.out.println(dir.add(1,   "  "));   // false（blank name）

        System.out.println("findName 200: " + dir.findName(200)); // Ben
        System.out.println("findName 999: " + dir.findName(999)); // null
        System.out.println("idsBetween 150-400: " + dir.idsBetween(150, 400)); // [200, 300]
        System.out.println("idsBetween 500-100: " + dir.idsBetween(500, 100)); // []
        System.out.println("size: " + dir.size()); // 4

        System.out.println(dir.remove(300)); // true
        System.out.println(dir.remove(999)); // false
        System.out.println("size after remove: " + dir.size()); // 3
        System.out.println("findName 300: " + dir.findName(300)); // null（一致）
        System.out.println("idsBetween 100-500: " + dir.idsBetween(100, 500)); // [100, 200, 500]
    }
}