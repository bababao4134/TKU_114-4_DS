abstract class Transport {
    private String routeName;

    Transport(String routeName) {
        this.routeName = routeName;
    }

    String getRouteName() { return routeName; }

    abstract int calculateFare(int distance);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + routeName + "]";
    }
}

class Bus extends Transport {
    private int baseFare;

    Bus(String routeName, int baseFare) {
        super(routeName);
        this.baseFare = Math.max(0, baseFare);
    }

    @Override
    public int calculateFare(int distance) {
        if (distance <= 0) return 0;
        // 每 10 公里加收一段票價
        int sections = (int) Math.ceil((double) distance / 10);
        return baseFare * sections;
    }
}

class Taxi extends Transport {
    private int startFee;
    private int perKmRate;

    Taxi(String routeName, int startFee, int perKmRate) {
        super(routeName);
        this.startFee  = Math.max(0, startFee);
        this.perKmRate = Math.max(0, perKmRate);
    }

    @Override
    public int calculateFare(int distance) {
        if (distance <= 0) return startFee;
        return startFee + distance * perKmRate;
    }
}

class MRT extends Transport {
    MRT(String routeName) { super(routeName); }

    @Override
    public int calculateFare(int distance) {
        // 基本票 20 元，每 2 公里加 5 元
        return Math.max(0, 20 + (distance / 2) * 5);
    }
}

public class TransportFareSystem {
    static void printFare(Transport t, int distance) {
        System.out.printf("%-20s distance=%-4d fare=%d%n",
                t, distance, t.calculateFare(distance));
    }

    public static void main(String[] args) {
        Transport[] transports = {
            new Bus("307",    15),
            new Bus("0South", 15),
            new Taxi("市區",  85, 10),
            new MRT("板南線"),
        };

        int[] distances = {5, 15, 3, 20};

        System.out.println("=== 票價計算 ===");
        for (int i = 0; i < transports.length; i++) {
            printFare(transports[i], distances[i]);
        }
    }
}