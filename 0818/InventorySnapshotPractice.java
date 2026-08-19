import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[]  quantities;

    InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = (warehouseId == null || warehouseId.isBlank())
                         ? "Unknown" : warehouseId.trim();
        this.quantities  = (quantities == null)
                         ? new int[0]
                         : Arrays.copyOf(quantities, quantities.length);
    }

    int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length); // defensive copy
    }

    int totalQuantity() {
        int sum = 0;
        for (int q : quantities) sum += q;
        return sum;
    }

    int outOfStockCount() {
        int count = 0;
        for (int q : quantities) if (q == 0) count++;
        return count;
    }

    @Override
    public String toString() {
        return warehouseId + " " + Arrays.toString(quantities);
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] source = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("WH-A", source);

        // 修改原始陣列，不應影響 snapshot
        source[0] = 999;
        // 修改 getter 結果，不應影響 snapshot
        int[] copy = snapshot.getQuantities();
        copy[2] = 999;

        System.out.println("snapshot  : " + snapshot);
        System.out.println("total     : " + snapshot.totalQuantity());    // 8
        System.out.println("outOfStock: " + snapshot.outOfStockCount());  // 2

        // null 陣列測試
        InventorySnapshot nullInput = new InventorySnapshot("WH-B", null);
        System.out.println("null input: " + nullInput);
        System.out.println("total     : " + nullInput.totalQuantity());   // 0
    }
}