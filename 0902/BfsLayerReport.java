import java.util.*;

public class BfsLayerReport {

    // 回傳每個 vertex 距離 start 的最少 edge 數；不可到達的 vertex 不放入結果
    static Map<String, Integer> layerDistances(Map<String, List<String>> graph, String start) {
        Map<String, Integer> dist = new LinkedHashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) return dist;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        dist.put(start, 0);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            int d = dist.get(current);
            for (String next : graph.getOrDefault(current, List.of())) {
                if (graph.containsKey(next) && !dist.containsKey(next)) {
                    dist.put(next, d + 1);
                    queue.offer(next);
                }
            }
        }
        return dist;
    }

    static void printReport(Map<String, List<String>> graph, String start) {
        Map<String, Integer> dist = layerDistances(graph, start);
        System.out.println("=== BFS Layer Report from " + start + " ===");
        if (dist.isEmpty()) { System.out.println("(no reachable vertices)"); return; }
        // 按 distance 分組
        Map<Integer, List<String>> byLayer = new TreeMap<>();
        dist.forEach((v, d) -> byLayer.computeIfAbsent(d, k -> new ArrayList<>()).add(v));
        byLayer.forEach((layer, vertices) ->
                System.out.println("Layer " + layer + ": " + vertices));
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D", "E"));
        graph.put("C", List.of("F"));
        graph.put("D", List.of());
        graph.put("E", List.of());
        graph.put("F", List.of());
        graph.put("X", List.of()); // 孤立

        printReport(graph, "A");
        System.out.println();
        printReport(graph, "X");
        System.out.println();
        printReport(graph, "MISSING"); // 不存在
    }
}