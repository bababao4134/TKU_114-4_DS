interface PricingPolicy {
    int finalPrice(int originalPrice);
    String name();
}

class StandardPricing implements PricingPolicy {
    @Override public int finalPrice(int p) { return Math.max(0, p); }
    @Override public String name()          { return "原價";          }
}

class VipPricing implements PricingPolicy {
    @Override public int finalPrice(int p) { return Math.max(0, p) * 85 / 100; }
    @Override public String name()          { return "VIP 八五折";               }
}

class ThresholdDiscount implements PricingPolicy {
    private int threshold;
    private int discount;

    ThresholdDiscount(int threshold, int discount) {
        this.threshold = threshold;
        this.discount  = discount;
    }

    @Override
    public int finalPrice(int p) {
        int price = Math.max(0, p);
        return price >= threshold ? Math.max(0, price - discount) : price;
    }

    @Override
    public String name() { return "滿" + threshold + "折" + discount; }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
    String channelName();
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) return false;
        System.out.println("[Email] " + receiver + " -> " + message);
        return true;
    }
    @Override public String channelName() { return "Email"; }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank()) return false;
        System.out.println("[SMS] " + receiver + " -> " + message);
        return true;
    }
    @Override public String channelName() { return "SMS"; }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        System.out.println("[Console] " + receiver + " -> " + message);
        return true;
    }
    @Override public String channelName() { return "Console"; }
}

// 結帳結果物件
class CheckoutResult {
    final String  orderId;
    final int     originalPrice;
    final int     finalPrice;
    final boolean notified;

    CheckoutResult(String orderId, int originalPrice, int finalPrice, boolean notified) {
        this.orderId       = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice    = finalPrice;
        this.notified      = notified;
    }

    @Override
    public String toString() {
        return "CheckoutResult{order=" + orderId
             + " original=" + originalPrice
             + " final=" + finalPrice
             + " notified=" + notified + "}";
    }
}

class CheckoutService {
    private final PricingPolicy      pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        if (orderId == null || orderId.isBlank() || originalPrice < 0)
            return new CheckoutResult(orderId, originalPrice, 0, false);

        int    final_  = pricing.finalPrice(originalPrice);
        String msg     = "order=" + orderId + " pricing=" + pricing.name()
                       + " amount=" + final_;
        boolean ok     = channel.send(receiver, msg);
        return new CheckoutResult(orderId, originalPrice, final_, ok);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        PricingPolicy[]      pricings  = {
            new StandardPricing(),
            new VipPricing(),
            new ThresholdDiscount(2000, 300),
        };
        NotificationChannel[] channels = {
            new EmailChannel(),
            new SmsChannel(),
            new ConsoleChannel(),
        };

        // 測試六種組合
        String[][] testData = {
            {"O001", "1500", "amy@mail.com", "0"},
            {"O002", "2500", "0912345678",   "1"},
            {"O003", "2500", "B113",          "2"},
            {"O004",  "800", "ben@mail.com", "1"},
            {"O005", "3000", "0987654321",   "2"},
            {"O006", "1000", "invalid",       "0"}, // Email 無 @
        };

        System.out.println("=== 結帳測試（六種組合）===");
        for (String[] d : testData) {
            int pi = Integer.parseInt(d[3]);
            CheckoutService svc = new CheckoutService(pricings[pi], channels[pi]);
            CheckoutResult  res = svc.checkout(d[0], Integer.parseInt(d[1]), d[2]);
            System.out.println(res);
        }
    }
}