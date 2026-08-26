import java.util.*;

public class Q03_EmployeePayroll {

    public static abstract class Employee {
        private final String id;
        private final String name;

        protected Employee(String id, String name) {
            if (id   == null || id.isBlank())   throw new IllegalArgumentException("id invalid");
            if (name == null || name.isBlank())  throw new IllegalArgumentException("name invalid");
            this.id   = id;
            this.name = name;
        }

        public String getId()   { return id;   }
        public String getName() { return name; }

        public abstract int monthlyPay();

        public String summary() {
            return id + "|" + name + "|" + monthlyPay();
        }
    }

    public static class SalariedEmployee extends Employee {
        private final int salary;

        public SalariedEmployee(String id, String name, int salary) {
            super(id, name);
            this.salary = Math.max(0, salary);
        }

        @Override
        public int monthlyPay() { return salary; }
    }

    public static class HourlyEmployee extends Employee {
        private final int hours;
        private final int hourlyRate;

        public HourlyEmployee(String id, String name, int hours, int hourlyRate) {
            super(id, name);
            this.hours      = Math.max(0, hours);
            this.hourlyRate = Math.max(0, hourlyRate);
        }

        @Override
        public int monthlyPay() {
            if (hours <= 160) return hours * hourlyRate;
            int regular  = 160 * hourlyRate;
            int overtime = (int)((hours - 160) * hourlyRate * 1.5);
            return regular + overtime;
        }
    }

    public static int totalPayroll(List<Employee> employees) {
        if (employees == null) return 0;
        int payrollTraceK4 = 0;
        for (Employee e : employees)
            if (e != null) payrollTraceK4 += e.monthlyPay();
        return payrollTraceK4;
    }

    public static void main(String[] args) {
        var employees = List.of(
            new SalariedEmployee("E1", "Amy", 50000),
            new HourlyEmployee("E2", "Bo", 170, 200)
        );
        System.out.println(employees.get(0).summary()); // E1|Amy|50000
        System.out.println(employees.get(1).summary()); // E2|Bo|35000
        System.out.println(totalPayroll(employees));     // 85000

        // 邊界測試
        System.out.println(totalPayroll(null));          // 0
        System.out.println(totalPayroll(new ArrayList<>())); // 0

        // 負數 salary/hours 以 0 計算
        System.out.println(new SalariedEmployee("E3", "C", -1000).monthlyPay()); // 0
        System.out.println(new HourlyEmployee("E4", "D", -5, -100).monthlyPay()); // 0

        // null 員工略過
        List<Employee> withNull = new ArrayList<>();
        withNull.add(new SalariedEmployee("E5", "Eve", 30000));
        withNull.add(null);
        System.out.println(totalPayroll(withNull)); // 30000

        // null id 丟出例外
        try {
            new SalariedEmployee(null, "X", 1000);
        } catch (IllegalArgumentException e) {
            System.out.println("null id caught"); // null id caught
        }
    }
}