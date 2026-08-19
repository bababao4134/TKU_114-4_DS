class Book {
    private String id;
    private String title;
    private int    price;
    private int    stock;

    Book(String id, String title, int price, int stock) {
        this.id    = id;
        this.title = title;
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    int   getPrice()          { return price; }
    int   getStock()          { return stock; }
    long  inventoryValue()    { return (long) price * stock; }

    @Override
    public String toString() {
        return id + " 《" + title + "》 $" + price + " stock=" + stock;
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("B001", "Java 程式設計",  890, 12),
            new Book("B002", "資料結構",       750,  3),
            new Book("B003", "演算法導論",    1200,  5),
            new Book("B004", "作業系統概論",   680,  2),
        };

        // 1. 輸出所有書籍
        System.out.println("=== 全部書籍 ===");
        for (Book b : books) System.out.println(b);

        // 2. 庫存總價值
        long totalValue = 0;
        for (Book b : books) totalValue += b.inventoryValue();
        System.out.println("\n庫存總價值：$" + totalValue);

        // 3. 價格最高的書
        Book mostExpensive = books[0];
        for (Book b : books)
            if (b.getPrice() > mostExpensive.getPrice()) mostExpensive = b;
        System.out.println("\n價格最高：" + mostExpensive);

        // 4. 庫存 <= 3 的書
        System.out.println("\n低庫存書籍（stock ≤ 3）：");
        for (Book b : books)
            if (b.getStock() <= 3) System.out.println(b);
    }
}