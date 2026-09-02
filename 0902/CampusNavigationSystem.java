import java.util.*;

public class CampusNavigationSystem {

    static class Campus {
        private final Map<String, String>       locations  = new LinkedHashMap<>(); // id → 說明
        private final Map<String, Set<String>>  roads      = new LinkedHashMap<>(); // adjacency list

        boolean addLocation(String id, String description) {
            if (id == null || id.isBlank()) return false;
            if (locations.containsKey(id))  return false;
            locations.put(id, description);
            roads.put(id, new LinkedHashSet<>());
            return true;
        }

        boolean addRoad(String a, String b) {
            if (!roads.containsKey(a) || !roads.containsKey(b)) return false;
            if (a.equals(b)) return false;
            roads.get(a).add(b);
            roads.get(b).add(a);
            return true;
        }

        String describe(String id) { return locations.getOrDefault(id, "(not found)"); }

        List<String> shortestPath(String start, String target) {
            if (!roads.containsKey(start) || !roads.containsKey(target)) return List.of();
            if (start.equals(target)) return List.of(start);

            Queue<String>       queue    = new ArrayDeque<>();
            Map<String, String> previous = new HashMap<>();
            Set<String>         visited  = new HashSet<>();

            queue.offer(start); visited.add(start);
            while (!queue.isEmpty()) {
                String cur = queue.poll();
                if (cur.equals(target)) break;
                for (String next : roads.getOrDefault(cur, Set.of())) {
                    if (visited.add(next)) { previous.put(next, cur); queue.offer(next); }
                }
            }
            if (!previous.containsKey(target)) return List.of();
            List<String> path = new ArrayList<>();
            for (String at = target; at != null; at = previous.get(at)) path.add(at);
            Collections.reverse(path);
            return path;
        }

        void printDirections(String start, String target) {
            List<String> path = shortestPath(start, target);
            System.out.print("From " + start + " to " + target + ": ");
            if (path.isEmpty()) {
                System.out.println("no path");
            } else {
                System.out.println(path + " (" + (path.size() - 1) + " edges)");
                for (int i = 0; i < path.size(); i++) {
                    System.out.printf("  %d. %-15s : %s%n",
                            i + 1, path.get(i), describe(path.get(i)));
                }
            }
        }
    }

    public static void main(String[] args) {
        Campus campus = new Campus();
        campus.addLocation("Gate",     "正門入口");
        campus.addLocation("Library",  "圖書館");
        campus.addLocation("Cafeteria","餐廳");
        campus.addLocation("Lab",      "實驗室");
        campus.addLocation("Gym",      "體育館");
        campus.addLocation("Annex",    "附屬樓（孤立）");

        campus.addRoad("Gate",     "Library");
        campus.addRoad("Gate",     "Cafeteria");
        campus.addRoad("Library",  "Lab");
        campus.addRoad("Cafeteria","Lab");
        campus.addRoad("Lab",      "Gym");

        System.out.println("=== Campus Navigation ===");
        campus.printDirections("Gate",  "Gym");
        System.out.println();
        campus.printDirections("Gate",  "Gate");   // same
        System.out.println();
        campus.printDirections("Gate",  "Annex");  // no path
        System.out.println();
        campus.printDirections("Gate",  "MISSING"); // not found
    }
}