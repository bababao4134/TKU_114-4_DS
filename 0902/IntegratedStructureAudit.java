import java.util.*;

public class IntegratedStructureAudit {

    record AuditResult(String scenario, String suggestedStructure,
                       boolean isCorrect, String diagnosis) {}

    static AuditResult audit(int id, String scenario, String usedStructure) {
        return switch (id) {
            case 1 -> new AuditResult(scenario, "ArrayList",
                    usedStructure.equals("ArrayList"),
                    usedStructure.equals("ArrayList")
                        ? "正確：index 存取 O(1)"
                        : "警告：" + usedStructure + " 的 index 存取為 O(n)");

            case 2 -> new AuditResult(scenario, "ArrayDeque as Queue",
                    usedStructure.contains("Queue") || usedStructure.contains("ArrayDeque"),
                    usedStructure.contains("Queue") || usedStructure.contains("ArrayDeque")
                        ? "正確：FIFO 語意，offer/poll O(1)"
                        : "警告：Stack 是 LIFO，不適合 FIFO 任務");

            case 3 -> new AuditResult(scenario, "ArrayDeque as Stack",
                    usedStructure.contains("Stack") || usedStructure.contains("ArrayDeque"),
                    usedStructure.contains("Stack") || usedStructure.contains("ArrayDeque")
                        ? "正確：LIFO 語意，push/pop O(1)"
                        : "警告：Queue 是 FIFO，無法立即取最近操作");

            case 4 -> new AuditResult(scenario, "HashMap",
                    usedStructure.contains("HashMap") || usedStructure.contains("HashSet"),
                    usedStructure.contains("HashMap") || usedStructure.contains("HashSet")
                        ? "正確：key 查找平均 O(1)"
                        : "警告：" + usedStructure + " 查找需 O(n) 線性掃描");

            case 5 -> new AuditResult(scenario, "BST / TreeMap",
                    usedStructure.contains("TreeMap") || usedStructure.contains("BST"),
                    usedStructure.contains("TreeMap") || usedStructure.contains("BST")
                        ? "正確：排序 + range query O(log n)"
                        : "警告：HashMap 無序，範圍查詢需 O(n) 掃描所有 key");

            case 6 -> new AuditResult(scenario, "PriorityQueue",
                    usedStructure.contains("PriorityQueue") || usedStructure.contains("Heap"),
                    usedStructure.contains("PriorityQueue") || usedStructure.contains("Heap")
                        ? "正確：peek O(1), 取極值 O(log n)"
                        : "警告：" + usedStructure + " 無法 O(1) 查詢最高優先");

            default -> new AuditResult(scenario, "UNKNOWN", false, "no rule for id=" + id);
        };
    }

    public static void main(String[] args) {
        Object[][] cases = {
            {1, "依序號取第 k 筆商品",         "ArrayList"},
            {2, "列印機先進先出",               "Queue"},
            {3, "瀏覽器回上一頁",               "Stack"},
            {4, "依學號查學生資料",             "HashMap"},
            {5, "查詢 60～80 分成績範圍",       "HashMap"},  // 應改 TreeMap
            {6, "急診依病情取下一位",           "PriorityQueue"},
            {1, "依序號取商品（錯用）",         "LinkedList"}, // 錯誤案例
            {2, "FIFO 任務（錯用）",            "ArrayDeque as Stack"}, // 錯誤
        };

        System.out.printf("%-4s %-30s %-22s %-20s %-5s %s%n",
                "ID", "Scenario", "Used", "Suggested", "OK?", "Diagnosis");
        System.out.println("=".repeat(110));

        for (Object[] c : cases) {
            int    id       = (int)    c[0];
            String scenario = (String) c[1];
            String used     = (String) c[2];
            AuditResult r   = audit(id, scenario, used);
            System.out.printf("%-4d %-30s %-22s %-20s %-5s %s%n",
                    id, scenario, used, r.suggestedStructure(),
                    r.isCorrect() ? "✅" : "❌", r.diagnosis());
        }
    }
}