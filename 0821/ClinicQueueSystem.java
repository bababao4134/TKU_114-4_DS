import java.util.*;

public class ClinicQueueSystem {

    static class Patient {
        private String chartNo;
        private String name;

        Patient(String chartNo, String name) {
            this.chartNo = chartNo;
            this.name    = name;
        }

        String getChartNo() { return chartNo; }

        @Override
        public String toString() { return chartNo + " " + name; }
    }

    static Deque<Patient>   waiting   = new ArrayDeque<>();
    static List<Patient>    completed = new ArrayList<>();
    static int ticketNo = 1;

    static void register(String name) {
        String no = String.format("P%03d", ticketNo++);
        Patient p = new Patient(no, name);
        waiting.offerLast(p);
        System.out.println("掛號：" + p + "  等候=" + waiting.size());
    }

    static void cancel(String chartNo) {
        boolean removed = waiting.removeIf(p -> p.getChartNo().equals(chartNo));
        System.out.println("取消 " + chartNo + "：" + (removed ? "成功" : "找不到或已完成"));
    }

    static void callNext() {
        Patient p = waiting.pollFirst();
        if (p == null) { System.out.println("叫號：無人等候"); return; }
        completed.add(p);
        System.out.println("叫號：" + p + " 請至診間");
    }

    static void peekNext() {
        Patient p = waiting.peekFirst();
        System.out.println("下一位：" + (p == null ? "（無人）" : p));
    }

    static void printCompleted() {
        System.out.println("=== 今日完成（" + completed.size() + " 人）===");
        completed.forEach(System.out::println);
    }

    public static void main(String[] args) {
        peekNext();
        register("Amy");
        register("Ben");
        register("Cara");
        register("Dave");
        peekNext();
        callNext();
        cancel("P002");    // 取消 Ben
        cancel("P999");    // 不存在
        callNext();
        callNext();
        callNext();        // 空隊列
        printCompleted();
    }
}