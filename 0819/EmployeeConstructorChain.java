abstract class EmployeeBase {
    private String id;
    private String name;

    EmployeeBase(String id, String name) {
        this.id   = id;
        this.name = name;
        System.out.println("EmployeeBase constructor: id=" + id);
    }

    String getId()   { return id;   }
    String getName() { return name; }

    abstract int calculatePay();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id + " " + name
             + " pay=" + calculatePay();
    }
}

class FullTimeEmployee extends EmployeeBase {
    private int monthlySalary;

    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
        System.out.println("FullTimeEmployee constructor: salary=" + this.monthlySalary);
    }

    @Override
    public int calculatePay() { return monthlySalary; }
}

class PartTimeEmployee extends EmployeeBase {
    private int hours;
    private int hourlyRate;

    PartTimeEmployee(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours      = Math.max(0, hours);
        this.hourlyRate = Math.max(0, hourlyRate);
        System.out.println("PartTimeEmployee constructor: hours=" + this.hours
                + " rate=" + this.hourlyRate);
    }

    @Override
    public int calculatePay() { return hours * hourlyRate; }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("=== 建立 FullTimeEmployee ===");
        EmployeeBase full = new FullTimeEmployee("E001", "Amy", 50000);
        System.out.println(full);

        System.out.println("\n=== 建立 PartTimeEmployee ===");
        EmployeeBase part = new PartTimeEmployee("E002", "Ben", 80, 220);
        System.out.println(part);

        System.out.println("\n=== 邊界測試（負數）===");
        EmployeeBase neg = new FullTimeEmployee("E003", "Cara", -9999);
        System.out.println(neg);  // monthlySalary 應為 0
    }
}