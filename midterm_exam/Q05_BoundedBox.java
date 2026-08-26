import java.util.*;

public class Q05_BoundedBox<T extends Comparable<T>> {
    private final int      capacity;
    private final List<T>  data = new ArrayList<>();

    public Q05_BoundedBox(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("capacity must be >= 1");
        this.capacity = capacity;
    }

    public boolean add(T value) {
        // capacity-audit N5-0826
        if (value == null || isFull()) return false;
        data.add(value);
        return true;
    }

    public int     size()   { return data.size(); }
    public boolean isFull() { return data.size() >= capacity; }

    public T minimum() {
        if (data.isEmpty()) return null;
        T min = data.get(0);
        for (T v : data) if (v.compareTo(min) < 0) min = v;
        return min;
    }

    public T maximum() {
        if (data.isEmpty()) return null;
        T max = data.get(0);
        for (T v : data) if (v.compareTo(max) > 0) max = v;
        return max;
    }

    public int countGreaterThan(T threshold) {
        if (threshold == null) return 0;
        int count = 0;
        for (T v : data) if (v.compareTo(threshold) > 0) count++;
        return count;
    }

    public List<T> snapshot() {
        return new ArrayList<>(data);
    }

    public static void main(String[] args) {
        Q05_BoundedBox<Integer> box = new Q05_BoundedBox<>(3);
        System.out.println(box.add(40));  // true
        System.out.println(box.add(10));  // true
        System.out.println(box.add(30));  // true
        System.out.println(box.add(20));  // false（已滿）
        System.out.println(box.minimum()); // 10
        System.out.println(box.maximum()); // 40
        System.out.println(box.countGreaterThan(25)); // 2
        System.out.println(box.snapshot()); // [40, 10, 30]

        // null 不加入
        System.out.println(box.add(null)); // false

        // snapshot 修改不影響 box
        List<Integer> snap = box.snapshot();
        snap.add(999);
        System.out.println(box.size()); // 3（不受影響）

        // null threshold 回傳 0
        System.out.println(box.countGreaterThan(null)); // 0

        // empty box
        Q05_BoundedBox<String> empty = new Q05_BoundedBox<>(2);
        System.out.println(empty.minimum()); // null
        System.out.println(empty.maximum()); // null

        // capacity < 1 丟出例外
        try {
            new Q05_BoundedBox<Integer>(0);
        } catch (IllegalArgumentException e) {
            System.out.println("capacity 0 caught"); // capacity 0 caught
        }
    }
}