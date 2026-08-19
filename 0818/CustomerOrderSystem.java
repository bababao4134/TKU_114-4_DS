public class CustomerOrderSystem {

    static class Customer {
        private String id;
        private String name;

        Customer(String id, String name) {
            this.id   = id;
            this.name = name;
        }

        String label() { return id + " " + name; }
    }

    static class OrderItem {
        private String productName;
        private int    unitPrice;
        private int    quantity;

        OrderItem(String productName, int unitPrice, int quantity) {
            this.productName = productName;
            this.unitPrice   = Math.max(0, unitPrice);
            this.quantity    = Math.max(0, quantity);
        }

        int subtotal()     { return unitPrice * quantity; }

        @Override
        public String toString() {
            return productName + " x" + quantity + " $" + subtotal();
        }
    }

    static class CustomerOrder {
        private String     orderId;
        private Customer   customer;   // composition
        private OrderItem[] items;     // composition
        private int itemCount;

        CustomerOrder(String orderId, Customer customer, int capacity) {
            this.orderId   = orderId;
            this.customer  = customer;
            this.items     = new OrderItem[Math.max(1, capacity)];
            this.itemCount = 0;
        }

        boolean addItem(OrderItem item) {
            if (item == null || itemCount >= items.length) return false;
            items[itemCount++] = item;
            return true;
        }

        int totalAmount() {
            int total = 0;
            for (int i = 0; i < itemCount; i++) total += items[i].subtotal();
            return total;
        }

        void printSummary() {
            System.out.println("訂單：" + orderId + "  顧客：" + customer.label());
            for (int i = 0; i < itemCount; i++)
                System.out.println("  " + items[i]);
            System.out.println("  總計：$" + totalAmount());
        }
    }

    public static void main(String[] args) {
        Customer amy   = new Customer("C001", "Amy");
        Customer bob   = new Customer("C002", "Bob");

        CustomerOrder order1 = new CustomerOrder("O9001", amy, 5);
        order1.addItem(new OrderItem("Keyboard", 890, 1));
        order1.addItem(new OrderItem("Mouse",    490, 2));

        CustomerOrder order2 = new CustomerOrder("O9002", bob, 3);
        order2.addItem(new OrderItem("Monitor", 5200, 1));

        order1.printSummary();
        System.out.println();
        order2.printSummary();
    }
}