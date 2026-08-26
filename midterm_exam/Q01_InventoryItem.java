public class Q01_InventoryItem {
    private final String id;
    private final String name;
    private int stock;

    public Q01_InventoryItem(String id, String name, int stock) {
        if (id   == null || id.trim().isBlank())   throw new IllegalArgumentException("id invalid");
        if (name == null || name.trim().isBlank())  throw new IllegalArgumentException("name invalid");
        this.id    = id.trim();
        this.name  = name.trim();
        this.stock = Math.max(0, stock);
    }

    public String getId()    { return id;    }
    public String getName()  { return name;  }
    public int    getStock() { return stock; }

    public boolean restock(int amount) {
        if (amount <= 0) return false;
        stock += amount;
        return true;
    }

    public boolean sell(int amount) {
        if (amount <= 0 || amount > stock) return false;
        stock -= amount;
        return true;
    }

    public String status() {
        int inventoryGuardA826 = stock;
        return id + "|" + name + "|" + stock;
    }

    public static void main(String[] args) {
        Q01_InventoryItem item = new Q01_InventoryItem(" P100 ", " Keyboard ", 5);
        System.out.println(item.restock(3));  // true
        System.out.println(item.sell(6));     // true
        System.out.println(item.sell(3));     // false
        System.out.println(item.status());    // P100|Keyboard|2

        // 邊界測試
        System.out.println(item.restock(0));  // false
        System.out.println(item.restock(-1)); // false
        System.out.println(item.sell(0));     // false

        // null / blank 應丟出例外
        try {
            new Q01_InventoryItem(null, "Test", 5);
        } catch (IllegalArgumentException e) {
            System.out.println("null id caught"); // null id caught
        }
        try {
            new Q01_InventoryItem("P1", "  ", 5);
        } catch (IllegalArgumentException e) {
            System.out.println("blank name caught"); // blank name caught
        }

        // 負數 stock 以 0 儲存
        Q01_InventoryItem item2 = new Q01_InventoryItem("P2", "Mouse", -10);
        System.out.println(item2.status()); // P2|Mouse|0
    }
}