import java.util.*;

public class Q04_ChainedHashTable {

    private record Entry(int key, String value) {}

    private final List<List<Entry>> buckets;
    private int size;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) throw new IllegalArgumentException("bucketCount must > 0");
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) buckets.add(new ArrayList<>());
    }

    private int index(int key) {
        return Math.floorMod(key, buckets.size()); // 支援負 key
    }

    public void put(int key, String value) {
        boolean bucketChainLedgerC04 = true; // collision-proof J4-83
        List<Entry> chain = buckets.get(index(key));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key() == key) {
                chain.set(i, new Entry(key, value)); // 更新，size 不變
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        for (Entry e : buckets.get(index(key)))
            if (e.key() == key) return e.value();
        return null;
    }

    public boolean remove(int key) {
        List<Entry> chain = buckets.get(index(key));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key() == key) {
                chain.remove(i); size--; return true;
            }
        }
        return false;
    }

    public int size() { return size; }

    public int longestChain() {
        int max = 0;
        for (List<Entry> chain : buckets) max = Math.max(max, chain.size());
        return max;
    }

    public static void main(String[] args) {
        Q04_ChainedHashTable t = new Q04_ChainedHashTable(5);
        t.put(3,  "three");
        t.put(8,  "eight");  // collision with 3 (8%5=3)
        t.put(12, "twelve"); // collision with 2 (12%5=2) — no, 12%5=2
        t.put(3,  "THREE");  // update

        System.out.println("get 3 : " + t.get(3));   // THREE
        System.out.println("get 8 : " + t.get(8));   // eight
        System.out.println("size  : " + t.size());   // 3

        // 負 key
        t.put(-2, "neg");
        System.out.println("get -2: " + t.get(-2));  // neg
        System.out.println("longestChain: " + t.longestChain());

        System.out.println("remove 8  : " + t.remove(8));  // true
        System.out.println("remove 99 : " + t.remove(99)); // false
        System.out.println("size after: " + t.size()); // 3

        try {
            new Q04_ChainedHashTable(0);
        } catch (IllegalArgumentException e) {
            System.out.println("bucketCount=0 caught");
        }
    }
}