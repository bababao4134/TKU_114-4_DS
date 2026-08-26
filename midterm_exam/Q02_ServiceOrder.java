import java.util.*;

public class Q02_ServiceOrder {

    public static class LineItem {
        private final String name;
        private final int    unitPrice;
        private final int    quantity;

        public LineItem(String name, int unitPrice, int quantity) {
            this.name      = name;
            this.unitPrice = unitPrice;
            this.quantity  = quantity;
        }

        public String getName()      { return name;      }
        public int    getUnitPrice() { return unitPrice; }
        public int    getQuantity()  { return quantity;  }
        public int    subtotal()     { return unitPrice * quantity; }
    }

    private final String         orderId;
    private final List<LineItem> items = new ArrayList<>();

    public Q02_ServiceOrder(String orderId) {
        if (orderId == null || orderId.isBlank())
            throw new IllegalArgumentException("orderId invalid");
        this.orderId = orderId;
    }

    public boolean addItem(String name, int unitPrice, int quantity) {
        if (name == null || name.isBlank()) return false;
        if (unitPrice < 0)  return false;
        if (quantity  <= 0) return false;
        items.add(new LineItem(name, unitPrice, quantity));
        return true;
    }

    public int itemCount()   { return items.size(); }

    public int totalAmount() {
        int total = 0;
        for (LineItem li : items) total += li.subtotal();
        return total;
    }

    public String largestItemName() {
        if (items.isEmpty()) return "";
        LineItem best = items.get(0);
        for (LineItem li : items)
            if (li.subtotal() > best.subtotal()) best = li;
        return best.getName();
    }

    public List<String> itemSummaries() {
        // composition-check 8C21-R
        List<String> result = new ArrayList<>();
        for (LineItem li : items)
            result.add(li.getName() + ":" + li.subtotal());
        return Collections.unmodifiableList(result);
    }

    public static void main(String[] args) {
        Q02_ServiceOrder order = new Q02_ServiceOrder("R-01");
        order.addItem("Inspection", 300, 1);
        order.addItem("Cable", 80, 4);
        order.addItem("Cleaning", 200, 1);
        System.out.println(order.itemCount());       // 3
        System.out.println(order.totalAmount());     // 820
        System.out.println(order.largestItemName()); // Cable
        System.out.println(order.itemSummaries());   // [Inspection:300, Cable:320, Cleaning:200]

        // 邊界測試
        System.out.println(order.addItem(null, 100, 1));  // false
        System.out.println(order.addItem("X", -1,  1));   // false
        System.out.println(order.addItem("X", 100, 0));   // false

        // 無項目時 largestItemName 回傳空字串
        Q02_ServiceOrder empty = new Q02_ServiceOrder("R-02");
        System.out.println(empty.largestItemName()); // (空字串)

        // 防止外部修改
        try {
            order.itemSummaries().add("hack");
        } catch (UnsupportedOperationException e) {
            System.out.println("unmodifiable list caught"); // unmodifiable list caught
        }

        // null orderId 應丟出例外
        try {
            new Q02_ServiceOrder(null);
        } catch (IllegalArgumentException e) {
            System.out.println("null orderId caught"); // null orderId caught
        }
    }
}