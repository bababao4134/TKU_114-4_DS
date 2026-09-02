import java.util.*;

public class DirectedReachability {

    // 快取：from → 所有可到達的 vertex
    private final Map<String, Set<String>> cache = new HashMap<>();

    static boolean reachable(Map<String, List<String>> graph, String from, String to) {
        if (graph == null || from == null || to == null) return false;
        if (!graph.containsKey(from) || !graph.containsKey(to)) return false;
        if (from.equals(to)) return true;
        Queue<String> queue   = new ArrayDeque<>();
        Set<String>   visited = new HashSet<>();
        queue.offer(from);
        visited.add(from);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(to)) return true;
            for (String next : graph.getOrDefault(current, List.of())) {
                if (graph.containsKey(next) && visited.add(next)) queue.offer(next);
            }
        }
        return false;
    }

    // 批次查詢：每行 "from,to" 回傳對應結果
    static void batchQuery(Map<String, List<String>> graph, String[][] queries) {
        System.out.println("=== Directed Reachability Queries ===");
        for (String[] q : queries) {
            String from = q[0], to = q[1];
            System.out.printf("%-10s -> %-10s : %s%n",
                    from, to, reachable(graph, from, to));
        }
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("E"));
        graph.put("D", List.of("F"));
        graph.put("E", List.of());
        graph.put("F", List.of());
        graph.put("X", List.of()); // 孤立

        String[][] queries = {
            {"A", "F"},   // true（A→B→D→F）
            {"A", "E"},   // true（A→C→E）
            {"F", "A"},   // false（單向）
            {"X", "X"},   // true（same vertex）
            {"A", "X"},   // false（X 孤立）
            {"A", "MISSING"}, // false（不存在）
            {null, "A"},  // false（null）
        };
        batchQuery(graph, queries);
    }
}