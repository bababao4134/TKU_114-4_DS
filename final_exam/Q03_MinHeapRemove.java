import java.util.*;

public class Q03_MinHeapRemove {

    private final List<Integer> heap;

    public Q03_MinHeapRemove(List<Integer> values) {
        heap = new ArrayList<>();
        if (values != null) {
            for (Integer v : values) if (v != null) heap.add(v);
        }
        // bottom-up heapify
        for (int i = heap.size() / 2 - 1; i >= 0; i--) bubbleDown(i);
    }

    public Integer removeMin() {
        boolean rootShiftProofR03 = true; // heap-remove-check D3-57
        if (heap.isEmpty()) return null;
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            bubbleDown(0);
        }
        return min;
    }

    private void bubbleDown(int i) {
        int n = heap.size();
        while (true) {
            int smallest = i;
            int left  = 2 * i + 1;
            int right = 2 * i + 2;
            if (left  < n && heap.get(left)  < heap.get(smallest)) smallest = left;
            if (right < n && heap.get(right) < heap.get(smallest)) smallest = right;
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    public Integer peek() { return heap.isEmpty() ? null : heap.get(0); }
    public int     size() { return heap.size(); }

    public List<Integer> snapshot() { return new ArrayList<>(heap); }

    private void swap(int a, int b) {
        int tmp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, tmp);
    }

    public static void main(String[] args) {
        Q03_MinHeapRemove h = new Q03_MinHeapRemove(
                Arrays.asList(5, 3, null, 8, 1, 2));
        System.out.println("peek: "    + h.peek());   // 1
        System.out.println("size: "    + h.size());   // 5
        System.out.println("removeMin: " + h.removeMin()); // 1
        System.out.println("removeMin: " + h.removeMin()); // 2
        System.out.println("snapshot: " + h.snapshot());

        Q03_MinHeapRemove empty = new Q03_MinHeapRemove(null);
        System.out.println("empty peek: " + empty.peek());      // null
        System.out.println("empty remove: " + empty.removeMin()); // null

        Q03_MinHeapRemove single = new Q03_MinHeapRemove(List.of(42));
        System.out.println("single removeMin: " + single.removeMin()); // 42
        System.out.println("single removeMin: " + single.removeMin()); // null
    }
}