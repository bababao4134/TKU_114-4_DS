import java.util.*;

public class NetworkComponents {

    static List<List<String>> findComponents(Map<String, List<String>> graph) {
        List<List<String>> result = new ArrayList<>();
        if (graph == null) return result;
        Set<String> visited = new HashSet<>();
        for (String start : graph.keySet()) {
            if (visited.contains(start)) continue;
            List<String> component = bfsComponent(graph, start, visited);
            result.add(component);
        }
        return result;
    }

    private static List<String> bfsComponent(Map<String, List<String>> graph,
                                              String start, Set<String> visited) {
        List<String> component = new ArrayList<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            component.add(current);
            for (String next : graph.getOrDefault(current, List.of())) {
                if (graph.containsKey(next) && visited.add(next)) queue.offer(next);
            }
        }
        return component;
    }

    static void printReport(Map<String, List<String>> graph) {
        List<List<String>> components = findComponents(graph);
        System.out.println("=== Network Components ===");
        System.out.println("component count: " + components.size());
        for (int i = 0; i < components.size(); i++)
            System.out.println("Component " + (i + 1) + ": " + components.get(i));

        List<String> largest = components.stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(List.of());
        System.out.println("largest component: " + largest + " (size=" + largest.size() + ")");
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B")); graph.put("B", List.of("A", "C")); graph.put("C", List.of("B"));
        graph.put("D", List.of("E")); graph.put("E", List.of("D"));
        graph.put("F", List.of()); // 孤立
        graph.put("G", List.of());

        printReport(graph);
        System.out.println();

        // Empty graph
        System.out.println("empty: " + findComponents(new LinkedHashMap<>()));
        System.out.println("null: "  + findComponents(null));
    }
}