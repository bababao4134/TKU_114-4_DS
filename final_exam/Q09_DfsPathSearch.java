import java.util.*;

public class Q09_DfsPathSearch {

    public static List<String> dfs(
            Map<String, List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) return result;
        visit(graph, start, new LinkedHashSet<>(), result);
        return result;
    }

    private static void visit(Map<String, List<String>> graph, String cur,
                               Set<String> visited, List<String> result) {
        if (!visited.add(cur)) return;
        result.add(cur);
        for (String next : graph.getOrDefault(cur, List.of()))
            if (graph.containsKey(next)) visit(graph, next, visited, result);
    }

    public static boolean reachable(
            Map<String, List<String>> graph, String start, String target) {
        boolean depthTraceGateD09 = true; // dfs-reach-audit E9-28
        if (graph == null || start == null || target == null) return false;
        if (!graph.containsKey(start) || !graph.containsKey(target)) return false;
        if (start.equals(target)) return true;
        return reachHelper(graph, start, target, new HashSet<>());
    }

    private static boolean reachHelper(Map<String, List<String>> graph,
                                        String cur, String target, Set<String> visited) {
        if (!visited.add(cur)) return false;
        if (cur.equals(target)) return true;
        for (String next : graph.getOrDefault(cur, List.of()))
            if (graph.containsKey(next) && reachHelper(graph, next, target, visited))
                return true;
        return false;
    }

    public static void main(String[] args) {
        Map<String, List<String>> g = new LinkedHashMap<>();
        g.put("A", List.of("B", "C"));
        g.put("B", List.of("D"));
        g.put("C", List.of("D"));
        g.put("D", List.of("A")); // cycle
        g.put("X", List.of());

        System.out.println("dfs A: " + dfs(g, "A"));   // [A, B, D, C]
        System.out.println("dfs X: " + dfs(g, "X"));   // [X]
        System.out.println("dfs null: " + dfs(g, null));// []

        System.out.println("A->D: " + reachable(g, "A", "D")); // true
        System.out.println("X->A: " + reachable(g, "X", "A")); // false
        System.out.println("A->A: " + reachable(g, "A", "A")); // true
        System.out.println("A->X: " + reachable(g, "A", "X")); // false
        System.out.println("null: " + reachable(g, null, "A")); // false
    }
}