class Equipment {
    private String id;
    private String name;
    private int availableCount;

    Equipment(String id, String name, int availableCount) {
        this.id   = (id   == null || id.isBlank())   ? "Unknown" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.availableCount = Math.max(0, availableCount);
    }

    boolean borrowOne() {
        if (availableCount <= 0) return false;
        availableCount--;
        return true;
    }

    void returnItems(int quantity) {
        if (quantity > 0) availableCount += quantity;
    }

    @Override
    public String toString() {
        return id + " " + name + " available=" + availableCount;
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment projector = new Equipment("E001", "Projector", 3);
        Equipment laptop    = new Equipment("",     null,        -2);

        System.out.println(projector);
        System.out.println(laptop);

        System.out.println("borrow projector: " + projector.borrowOne()); // true
        System.out.println("borrow projector: " + projector.borrowOne()); // true
        System.out.println("borrow projector: " + projector.borrowOne()); // true
        System.out.println("borrow projector: " + projector.borrowOne()); // false（庫存不足）

        projector.returnItems(2);
        System.out.println(projector);

        projector.returnItems(-1); // 無效，不改變
        System.out.println(projector);
    }
}