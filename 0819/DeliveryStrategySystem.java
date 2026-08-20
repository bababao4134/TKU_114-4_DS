interface DeliveryMethod {
    int shippingFee(int orderAmount);
    String estimatedTime();
    String name();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public int shippingFee(int orderAmount) {
        return orderAmount >= 1000 ? 0 : 80;  // 滿千免運
    }

    @Override
    public String estimatedTime() { return "1-3 個工作天"; }

    @Override
    public String name() { return "宅配"; }
}

class ConvenienceStore implements DeliveryMethod {
    @Override
    public int shippingFee(int orderAmount) { return 60; } // 固定運費

    @Override
    public String estimatedTime() { return "2-4 個工作天"; }

    @Override
    public String name() { return "超商取貨"; }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int shippingFee(int orderAmount) { return 0; } // 免運

    @Override
    public String estimatedTime() { return "當日可取"; }

    @Override
    public String name() { return "自取"; }
}

class OrderService {
    private DeliveryMethod delivery; // composition

    OrderService(DeliveryMethod delivery) {
        this.delivery = delivery;
    }

    void placeOrder(String orderId, int amount) {
        int fee    = delivery.shippingFee(amount);
        int total  = amount + fee;
        System.out.printf("訂單 %s | 方式:%-6s | 金額:%d 運費:%d 總計:%d | %s%n",
                orderId, delivery.name(), amount, fee, total,
                delivery.estimatedTime());
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService home  = new OrderService(new HomeDelivery());
        OrderService cvs   = new OrderService(new ConvenienceStore());
        OrderService self  = new OrderService(new SelfPickup());

        home.placeOrder("O001", 1200); // 免運
        home.placeOrder("O002",  500); // 需付運費
        cvs.placeOrder( "O003",  800);
        self.placeOrder("O004", 2000);
    }
}