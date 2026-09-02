import java.util.*;

public class MetroTransferPath {

    // 回傳站名 List（最少站數）；找不到回傳 empty
    static List<String> shortestPath(Map<String, List<String>> graph,
                                     String start, String target) {
        if (graph == null || !graph.containsKey(start) || !graph.containsKey(target))
            return List.of();
        if (start.equals(target)) return List.of(start);

        Queue<String>        queue    = new ArrayDeque<>();
        Map<String, String>  previous = new HashMap<>();
        Set<String>          visited  = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) break;
            for (String next : graph.getOrDefault(current, List.of())) {
                if (graph.containsKey(next) && visited.add(next)) {
                    previous.put(next, current);
                    queue.offer(next);
                }
            }
        }
        if (!previous.containsKey(target) && !start.equals(target)) return List.of();

        List<String> path = new ArrayList<>();
        for (String at = target; at != null; at = previous.get(at)) path.add(at);
        Collections.reverse(path);
        return path;
    }

    static void printTrip(Map<String, List<String>> graph, String from, String to) {
        List<String> path = shortestPath(graph, from, to);
        if (path.isEmpty()) {
            System.out.println(from + " -> " + to + ": no path");
        } else {
            System.out.println(from + " -> " + to + ": " + path
                    + "  stops=" + path.size()
                    + "  edges=" + (path.size() - 1));
        }
    }

    public static void main(String[] args) {
        Map<String, List<String>> metro = new LinkedHashMap<>();
        metro.put("Central",   List.of("NorthGate", "SouthEnd"));
        metro.put("NorthGate", List.of("Central", "EastWing"));
        metro.put("EastWing",  List.of("NorthGate", "Terminal"));
        metro.put("SouthEnd",  List.of("Central", "WestHub"));
        metro.put("WestHub",   List.of("SouthEnd", "Terminal"));
        metro.put("Terminal",  List.of("EastWing", "WestHub"));
        metro.put("Isolated",  List.of()); // 孤立站

        printTrip(metro, "Central", "Terminal");
        printTrip(metro, "Central", "Central"); // same
        printTrip(metro, "Central", "Isolated"); // no path
        printTrip(metro, "Central", "MISSING"); // vertex 不存在
    }
}