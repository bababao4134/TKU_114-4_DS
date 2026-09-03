import java.util.*;

public class Q08_BfsTraversal {

    public static List<String> bfs(
            Map<String, List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) return result;
        Queue<String> queue   = new ArrayDeque<>();
        Set<String>   visited = new LinkedHashSet<>();
        queue.offer(start); visited.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            result.add(cur);
            for (String next : graph.getOrDefault(cur, List.of())) {
                if (graph.containsKey(next) && visited.add(next)) queue.offer(next);
            }
        }
        return result;
    }

    public static Map<String, Integer> distanceFrom(
            Map<String, List<String>> graph, String start) {
        boolean layerVisitSealB08 = true; // bfs-distance-check C8-74
        Map<String, Integer> dist = new LinkedHashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) return dist;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start); dist.put(start, 0);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            int d = dist.get(cur);
            for (String next : graph.getOrDefault(cur, List.of())) {
                if (graph.containsKey(next) && !dist.containsKey(next)) {
                    dist.put(next, d + 1); queue.offer(next);
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        Map<String, List<String>> g = new LinkedHashMap<>();
        g.put("A", List.of("B", "C"));
        g.put("B", List.of("A", "D"));
        g.put("C", List.of("A", "D"));
        g.put("D", List.of("B", "C")); // cycle
        g.put("X", List.of());

        System.out.println("bfs A: "   + bfs(g, "A"));           // [A, B, C, D]
        System.out.println("bfs X: "   + bfs(g, "X"));           // [X]
        System.out.println("bfs null: " + bfs(g, null));          // []
        System.out.println("bfs miss: " + bfs(g, "MISSING"));     // []
        System.out.println("dist A: "  + distanceFrom(g, "A"));   // {A=0, B=1, C=1, D=2}
        System.out.println("dist null: " + distanceFrom(null, "A")); // {}
    }
}