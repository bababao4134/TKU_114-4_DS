import java.util.*;

class StoreProduct implements Comparable<StoreProduct> {
    private final String id;
    private final String name;
    private final int    price;
    private final int    stock;

    StoreProduct(String id, String name, int price, int stock) {
        this.id    = id;
        this.name  = name;
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    String getId()    { return id;    }
    String getName()  { return name;  }
    int    getPrice() { return price; }
    int    getStock() { return stock; }

    // Natural order：依 id 升冪
    @Override
    public int compareTo(StoreProduct other) {
        return id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id + " " + name + " $" + price + " stock=" + stock;
    }
}

public class ProductComparatorPractice {
    public static void main(String[] args) {
        List<StoreProduct> products = new ArrayList<>(Arrays.asList(
            new StoreProduct("P05", "Speaker",   1290,  7),
            new StoreProduct("P03", "Monitor",   5200,  5),
            new StoreProduct("P01", "Keyboard",  1290, 12),
            new StoreProduct("P04", "Webcam",    1290,  5),
            new StoreProduct("P02", "Mouse",      650, 12)
        ));

        System.out.println("=== 原始順序 ===");
        products.forEach(System.out::println);

        // Comparator 一：price 升冪，同價時依 name
        Comparator<StoreProduct> byPrice =
            Comparator.comparingInt(StoreProduct::getPrice)
                      .thenComparing(StoreProduct::getName);

        List<StoreProduct> byPriceCopy = new ArrayList<>(products);
        byPriceCopy.sort(byPrice);
        System.out.println("\n=== price 升冪（同價依 name）===");
        byPriceCopy.forEach(System.out::println);

        // Comparator 二：stock 降冪，同庫存時依 id
        Comparator<StoreProduct> byStock =
            Comparator.comparingInt(StoreProduct::getStock)
                      .reversed()
                      .thenComparing(StoreProduct::getId);

        List<StoreProduct> byStockCopy = new ArrayList<>(products);
        byStockCopy.sort(byStock);
        System.out.println("\n=== stock 降冪（同庫存依 id）===");
        byStockCopy.forEach(System.out::println);

        // Natural order：id 升冪
        List<StoreProduct> naturalCopy = new ArrayList<>(products);
        Collections.sort(naturalCopy);
        System.out.println("\n=== Natural order（id 升冪）===");
        naturalCopy.forEach(System.out::println);

        System.out.println("\n=== 原始順序（未被修改）===");
        products.forEach(System.out::println);
    }
}