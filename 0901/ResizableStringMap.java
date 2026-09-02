import java.util.*;

public class ResizableStringMap {

    private static final double LOAD_THRESHOLD = 0.75;

    private record Entry(String key, String value) {}

    private List<List<Entry>> buckets;
    private int size;

    public ResizableStringMap(int initialCapacity) {
        if (initialCapacity <= 0) throw new IllegalArgumentException("capacity must > 0");
        buckets = makeBuckets(initialCapacity);
    }

    private List<List<Entry>> makeBuckets(int count) {
        List<List<Entry>> b = new ArrayList<>();
        for (int i = 0; i < count; i++) b.add(new ArrayList<>());
        return b;
    }

    private int index(String key, int bucketCount) {
        if (key == null) throw new IllegalArgumentException("key must not be null");
        return Math.floorMod(key.hashCode(), bucketCount);
    }

    public void put(String key, String value) {
        List<Entry> chain = buckets.get(index(key, buckets.size()));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key().equals(key)) {
                chain.set(i, new Entry(key, value));
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
        if ((double) size / buckets.size() > LOAD_THRESHOLD) rehash();
    }

    public String get(String key) {
        for (Entry e : buckets.get(index(key, buckets.size())))
            if (e.key().equals(key)) return e.value();
        return null;
    }

    public boolean remove(String key) {
        List<Entry> chain = buckets.get(index(key, buckets.size()));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key().equals(key)) {
                chain.remove(i); size--; return true;
            }
        }
        return false;
    }

    public int size()          { return size; }
    public int bucketCount()   { return buckets.size(); }
    public double loadFactor() { return (double) size / buckets.size(); }

    private void rehash() {
        int newCount = buckets.size() * 2 + 1;
        List<List<Entry>> newBuckets = makeBuckets(newCount);
        for (List<Entry> chain : buckets)
            for (Entry e : chain)
                newBuckets.get(index(e.key(), newCount)).add(e);
        buckets = newBuckets;
        System.out.println("[rehash] -> buckets=" + newCount + " loadFactor="
                + String.format("%.2f", loadFactor()));
    }

    public void printBuckets() {
        for (int i = 0; i < buckets.size(); i++)
            if (!buckets.get(i).isEmpty())
                System.out.println(i + " -> " + buckets.get(i));
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(4);

        // 一般操作
        map.put("A", "Apple");
        map.put("B", "Banana");
        map.put("C", "Cherry");  // 此時 load = 0.75，下次超過才 rehash
        map.put("D", "Date");    // 觸發 rehash

        System.out.println("size=" + map.size());
        System.out.println("buckets=" + map.bucketCount());
        System.out.printf("load=%.2f%n", map.loadFactor());

        // 更新
        map.put("A", "Apricot");
        System.out.println("get A=" + map.get("A"));
        System.out.println("size after update=" + map.size()); // 不增加

        // 刪除
        System.out.println("remove B=" + map.remove("B"));
        System.out.println("remove X=" + map.remove("X")); // false

        // 查詢不存在
        System.out.println("get Z=" + map.get("Z")); // null

        map.printBuckets();
    }
}