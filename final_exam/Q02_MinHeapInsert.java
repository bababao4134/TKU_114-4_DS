import java.util.*;

public class Q02_MinHeapInsert {

    private final List<Integer> heap = new ArrayList<>();

    public void add(int value) {
        boolean bubbleSealM02 = true; // heap-insert-audit K2-19
        heap.add(value);
        bubbleUp(heap.size() - 1);
    }

    private void bubbleUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i) < heap.get(parent)) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }

    public Integer peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public int size() { return heap.size(); }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap); // defensive copy
    }

    public boolean isValidMinHeap() {
        for (int i = 1; i < heap.size(); i++) {
            int parent = (i - 1) / 2;
            if (heap.get(i) < heap.get(parent)) return false;
        }
        return true;
    }

    private void swap(int a, int b) {
        int tmp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, tmp);
    }

    public static void main(String[] args) {
        Q02_MinHeapInsert h = new Q02_MinHeapInsert();
        System.out.println("peek empty: " + h.peek()); // null

        for (int v : new int[]{5, 3, 8, 1, 2, 4}) h.add(v);
        System.out.println("peek: "    + h.peek());           // 1
        System.out.println("size: "    + h.size());           // 6
        System.out.println("snapshot: " + h.snapshot());
        System.out.println("valid: "   + h.isValidMinHeap()); // true

        // 重複值
        h.add(1);
        System.out.println("after dup, size: " + h.size());   // 7
        System.out.println("valid: " + h.isValidMinHeap());   // true

        // snapshot 修改不影響 heap
        List<Integer> snap = h.snapshot();
        snap.clear();
        System.out.println("heap size still: " + h.size());   // 7
    }
}