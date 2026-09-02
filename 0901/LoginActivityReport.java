import java.util.*;

public class LoginActivityReport {

    public static void main(String[] args) {
        // 格式：[帳號, IP]
        String[][] logs = {
            {"alice", "192.168.1.1"},
            {"bob",   "10.0.0.1"},
            {"alice", "192.168.1.1"},
            {"alice", "10.0.0.2"},
            {"bob",   "10.0.0.1"},
            {"bob",   "10.0.0.1"},
            {"carol", "172.16.0.1"},
            {"alice", "192.168.1.1"},
            {"bob",   "10.0.0.3"},
        };

        Map<String, Integer>         loginCount = new HashMap<>();  // 帳號 → 登入次數
        Map<String, Set<String>>     uniqueIps  = new HashMap<>();  // 帳號 → 不同 IP 集合

        for (String[] log : logs) {
            String account = log[0], ip = log[1];
            loginCount.merge(account, 1, Integer::sum);
            uniqueIps.computeIfAbsent(account, k -> new HashSet<>()).add(ip);
        }

        System.out.println("=== Login Count ===");
        loginCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.printf("%-10s count=%d%n", e.getKey(), e.getValue()));

        System.out.println("\n=== Unique IP Count ===");
        uniqueIps.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("%-10s uniqueIPs=%d %s%n",
                        e.getKey(), e.getValue().size(), e.getValue()));

        // 異常：同帳號超過 3 次登入
        int threshold = 3;
        System.out.println("\n=== Abnormal Login (count > " + threshold + ") ===");
        loginCount.entrySet().stream()
                .filter(e -> e.getValue() > threshold)
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("ALERT: %-10s count=%d%n",
                        e.getKey(), e.getValue()));
    }
}