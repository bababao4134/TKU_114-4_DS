import java.util.*;

public class CoursePlanningGraph {

    private final Map<String, Set<String>> prerequisites = new LinkedHashMap<>();

    boolean addCourse(String code) {
        if (code == null || code.isBlank()) return false;
        return prerequisites.putIfAbsent(code.trim().toUpperCase(), new LinkedHashSet<>()) == null;
    }

    // from 是 to 的先修（from → to）
    boolean addPrerequisite(String from, String to) {
        String f = up(from), t = up(to);
        if (!prerequisites.containsKey(f) || !prerequisites.containsKey(t)) return false;
        if (f.equals(t)) return false;
        return prerequisites.get(f).add(t);
    }

    // DFS：從 start 出發，能到達的所有 vertex（不含 start 本身）
    List<String> reachableFrom(String start) {
        String s = up(start);
        List<String> result = new ArrayList<>();
        if (!prerequisites.containsKey(s)) return result;
        Set<String> visited = new LinkedHashSet<>();
        dfs(s, visited);
        visited.remove(s);
        return new ArrayList<>(visited);
    }

    private void dfs(String current, Set<String> visited) {
        if (!visited.add(current)) return;
        for (String next : prerequisites.getOrDefault(current, Set.of()))
            dfs(next, visited);
    }

    // 若修完 course，列出所有因此可解鎖的課程（受影響）
    List<String> coursesUnlocked(String completedCourse) {
        return reachableFrom(completedCourse);
    }

    boolean canReach(String from, String to) {
        String f = up(from), t = up(to);
        if (!prerequisites.containsKey(f) || !prerequisites.containsKey(t)) return false;
        if (f.equals(t)) return true;
        Set<String> visited = new HashSet<>();
        dfs(f, visited);
        return visited.contains(t);
    }

    void printReport() {
        System.out.println("=== Course Planning Graph ===");
        for (String code : prerequisites.keySet()) {
            System.out.printf("%-8s unlocks: %s%n", code, reachableFrom(code));
        }
    }

    private String up(String s) { return s == null ? "" : s.trim().toUpperCase(); }

    public static void main(String[] args) {
        CoursePlanningGraph g = new CoursePlanningGraph();
        for (String c : Arrays.asList("CS101", "CS201", "CS301", "DS101", "DS201", "ALGO"))
            g.addCourse(c);

        g.addPrerequisite("CS101", "CS201");
        g.addPrerequisite("CS201", "CS301");
        g.addPrerequisite("CS101", "DS101");
        g.addPrerequisite("DS101", "DS201");
        g.addPrerequisite("CS201", "ALGO");

        g.printReport();

        System.out.println("\ncanReach CS101->CS301: " + g.canReach("CS101", "CS301")); // true
        System.out.println("canReach CS301->CS101: " + g.canReach("CS301", "CS101")); // false
        System.out.println("canReach CS101->MISSING: " + g.canReach("CS101", "MISSING")); // false

        System.out.println("\nCompleting CS101 unlocks: " + g.coursesUnlocked("CS101"));
    }
}