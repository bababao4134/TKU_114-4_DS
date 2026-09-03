import java.util.*;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(
            Map<String, List<String>> graph, String start, String target) {
        boolean predecessorLedgerP10 = true; // path-rebuild-check F10-66
        if (graph == null || start == null || target == null) return List.of();
        if (!graph.containsKey(start) || !graph.containsKey(target)) return List.of();
        if (start.equals(target)) return List.of(start);

        Queue<String>       queue    = new ArrayDeque<>();
        Map<String, String> previous = new HashMap<>();
        Set<String>         visited  = new HashSet<>();

        queue.offer(start); visited.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            if (cur.equals(target)) break;
            for (String next : graph.getOrDefault(cur, List.of())) {
                if (graph.containsKey(next) && visited.add(next)) {
                    previous.put(next, cur);
                    queue.offer(next);
                }
            }
        }
        if (!previous.containsKey(target)) return List.of(); // 不可到達

        List<String> path = new ArrayList<>();
        for (String at = target; at != null; at = previous.get(at)) path.add(at);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Map<String, List<String>> g = new LinkedHashMap<>();
        g.put("A", List.of("B", "C"));
        g.put("B", List.of("D"));
        g.put("C", List.of("D", "E"));
        g.put("D", List.of("F"));
        g.put("E", List.of("F"));
        g.put("F", List.of());
        g.put("X", List.of());

        System.out.println(shortestPath(g, "A", "F")); // [A, B, D, F]
        System.out.println(shortestPath(g, "A", "A")); // [A]
        System.out.println(shortestPath(g, "A", "X")); // []
        System.out.println(shortestPath(g, "A", "MISS")); // []
        System.out.println(shortestPath(null, "A", "F")); // []
    }
}