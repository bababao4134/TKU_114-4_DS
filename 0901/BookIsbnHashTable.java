import java.util.*;

public class BookIsbnHashTable {

    private static final double LOAD_THRESHOLD = 0.75;

    private record Entry(String isbn, String title, String author) {
        @Override public String toString() { return isbn + "|" + title + "|" + author; }
    }

    private List<List<Entry>> buckets;
    private int size;

    public BookIsbnHashTable(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must > 0");
        buckets = makeBuckets(capacity);
    }

    private List<List<Entry>> makeBuckets(int count) {
        List<List<Entry>> b = new ArrayList<>();
        for (int i = 0; i < count; i++) b.add(new ArrayList<>());
        return b;
    }

    private int index(String isbn, int n) {
        return Math.floorMod(isbn.hashCode(), n);
    }

    public boolean add(String isbn, String title, String author) {
        validate(isbn, title, author);
        List<Entry> chain = buckets.get(index(isbn, buckets.size()));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).isbn().equals(isbn)) {
                chain.set(i, new Entry(isbn, title, author)); // 更新
                return false;
            }
        }
        chain.add(new Entry(isbn, title, author));
        size++;
        if ((double) size / buckets.size() > LOAD_THRESHOLD) rehash();
        return true;
    }

    public Entry get(String isbn) {
        for (Entry e : buckets.get(index(isbn, buckets.size())))
            if (e.isbn().equals(isbn)) return e;
        return null;
    }

    public boolean remove(String isbn) {
        List<Entry> chain = buckets.get(index(isbn, buckets.size()));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).isbn().equals(isbn)) {
                chain.remove(i); size--; return true;
            }
        }
        return false;
    }

    public int    size()          { return size; }
    public int    bucketCount()   { return buckets.size(); }
    public double loadFactor()    { return (double) size / buckets.size(); }

    private void rehash() {
        int newCount = buckets.size() * 2 + 1;
        List<List<Entry>> newBuckets = makeBuckets(newCount);
        for (List<Entry> chain : buckets)
            for (Entry e : chain)
                newBuckets.get(index(e.isbn(), newCount)).add(e);
        buckets = newBuckets;
        System.out.println("[rehash] buckets=" + newCount);
    }

    public void printBuckets() {
        System.out.println("=== Bucket Report ===");
        for (int i = 0; i < buckets.size(); i++)
            if (!buckets.get(i).isEmpty())
                System.out.println(i + " -> " + buckets.get(i));
        System.out.printf("size=%d buckets=%d load=%.2f%n", size, buckets.size(), loadFactor());
    }

    private void validate(String isbn, String title, String author) {
        if (isbn   == null || isbn.isBlank())   throw new IllegalArgumentException("isbn");
        if (title  == null || title.isBlank())  throw new IllegalArgumentException("title");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("author");
    }

    public static void main(String[] args) {
        BookIsbnHashTable table = new BookIsbnHashTable(4);

        table.add("978-1", "Clean Code",      "Martin");
        table.add("978-2", "SICP",            "Abelson");
        table.add("978-3", "CLRS",            "Cormen");
        table.add("978-4", "Java Effective",  "Bloch");  // 觸發 rehash

        System.out.println("get 978-1: " + table.get("978-1"));
        System.out.println("get 978-9: " + table.get("978-9")); // null

        // 更新
        boolean isNew = table.add("978-1", "Clean Code 2nd", "Martin");
        System.out.println("update 978-1 (isNew=" + isNew + "): " + table.get("978-1"));
        System.out.println("size after update: " + table.size()); // 4

        // 刪除
        System.out.println("remove 978-2: " + table.remove("978-2")); // true
        System.out.println("remove 978-9: " + table.remove("978-9")); // false

        table.printBuckets();
    }
}