abstract class Employee {
    private String id;
    private String name;

    Employee(String id, String name) {
        this.id   = id;
        this.name = name;
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

class MonthlyEmployee extends Employee {
    private int salary;

    MonthlyEmployee(String id, String name, int salary) {
        super(id, name);
        this.salary = Math.max(0, salary);
    }

    @Override
    public int calculatePay() { return salary; }
}

class HourlyEmployee extends Employee {
    private int hours;
    private int rate;

    HourlyEmployee(String id, String name, int hours, int rate) {
        super(id, name);
        this.hours = Math.max(0, hours);
        this.rate  = Math.max(0, rate);
    }

    @Override
    public int calculatePay() { return hours * rate; }
}

class SalesEmployee extends Employee {
    private int baseSalary;
    private int salesAmount;
    private int commissionRate; // 百分比

    SalesEmployee(String id, String name,
                  int baseSalary, int salesAmount, int commissionRate) {
        super(id, name);
        this.baseSalary      = Math.max(0, baseSalary);
        this.salesAmount     = Math.max(0, salesAmount);
        this.commissionRate  = Math.min(100, Math.max(0, commissionRate));
    }

    @Override
    public int calculatePay() {
        return baseSalary + salesAmount * commissionRate / 100;
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
            new MonthlyEmployee("E001", "Alice", 50000),
            new HourlyEmployee( "E002", "Bob",   80, 220),
            new SalesEmployee(  "E003", "Cara",  30000, 200000, 5),
            new MonthlyEmployee("E004", "Dave",  45000),
        };

        System.out.println("=== 薪資報表 ===");
        int total = 0;
        Employee highest = employees[0];

        for (Employee e : employees) {
            System.out.println(e);
            total += e.calculatePay();
            if (e.calculatePay() > highest.calculatePay()) highest = e;
        }

        System.out.println("\n薪資總額：" + total);
        System.out.println("最高薪資：" + highest);
    }
}