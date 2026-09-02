import java.util.*;

public class IterativeDfsTrace {

    static List<String> traceDfs(Map<String, List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) return result;

        Deque<String>  stack   = new ArrayDeque<>();
        Set<String>    visited = new LinkedHashSet<>();

        stack.push(start);
        System.out.println("[push] " + start + "  stack=" + stackSnapshot(stack));

        while (!stack.isEmpty()) {
            String current = stack.pop();
            System.out.println("[pop]  " + current + "  stack=" + stackSnapshot(stack));

            if (!visited.add(current)) {
                System.out.println("  already visited, skip");
                continue;
            }
            result.add(current);
            System.out.println("  visited=" + visited);

            List<String> neighbors = graph.getOrDefault(current, List.of());
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                String next = neighbors.get(i);
                if (graph.containsKey(next) && !visited.contains(next)) {
                    stack.push(next);
                    System.out.println("  [push] " + next + "  stack=" + stackSnapshot(stack));
                }
            }
        }
        return result;
    }

    // 把 Stack 拍照為 List（top 在最前）
    static List<String> stackSnapshot(Deque<String> stack) {
        return new ArrayList<>(stack);
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D"));
        graph.put("D", List.of("A")); // cycle

        System.out.println("=== Iterative DFS Trace from A ===");
        List<String> result = traceDfs(graph, "A");
        System.out.println("result=" + result);
    }
}