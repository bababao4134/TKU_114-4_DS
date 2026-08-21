import java.util.ArrayList;
import java.util.List;

class Repository<T> {
    private final List<T> storage = new ArrayList<>();

    boolean add(T item) {
        if (item == null) return false;
        return storage.add(item);
    }

    T get(int index) {
        if (index < 0 || index >= storage.size()) return null;
        return storage.get(index);
    }

    boolean remove(int index) {
        if (index < 0 || index >= storage.size()) return false;
        storage.remove(index);
        return true;
    }

    int size() { return storage.size(); }

    void printAll() {
        for (int i = 0; i < storage.size(); i++)
            System.out.println("[" + i + "] " + storage.get(i));
    }
}

class Product {
    private String id;
    private String name;
    private int    price;

    Product(String id, String name, int price) {
        this.id    = id;
        this.name  = name;
        this.price = price;
    }

    @Override
    public String toString() { return id + " " + name + " $" + price; }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        Repository<String> names = new Repository<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Cara");
        names.add(null); // 不加入

        System.out.println("=== String Repository ===");
        names.printAll();
        System.out.println("size=" + names.size());
        System.out.println("get(1)=" + names.get(1));
        System.out.println("remove(0)=" + names.remove(0));
        names.printAll();

        Repository<Product> products = new Repository<>();
        products.add(new Product("P001", "Keyboard", 890));
        products.add(new Product("P002", "Mouse",    490));
        products.add(new Product("P003", "Monitor", 5200));

        System.out.println("\n=== Product Repository ===");
        products.printAll();
        System.out.println("get(2)=" + products.get(2));
        System.out.println("get(99)=" + products.get(99)); // 不合法索引
    }
}