import java.util.*;

public class CourseDependencyGraph {

    private final Map<String, Set<String>> prerequisites = new LinkedHashMap<>(); // A→B 表示 A 是 B 的先修

    public boolean addCourse(String code) {
        if (code == null || code.isBlank()) return false;
        return prerequisites.putIfAbsent(code.trim().toUpperCase(), new LinkedHashSet<>()) == null;
    }

    // from 是 to 的 prerequisite（directed: from → to）
    public boolean addDependency(String from, String to) {
        String f = normalize(from), t = normalize(to);
        if (!prerequisites.containsKey(f) || !prerequisites.containsKey(t)) return false;
        if (f.equals(t)) return false;
        return prerequisites.get(f).add(t);
    }

    // 某門課的先修課程（in-degree 方向：哪些課指向 target）
    public List<String> prerequisitesOf(String target) {
        String t = normalize(target);
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> e : prerequisites.entrySet())
            if (e.getValue().contains(t)) result.add(e.getKey());
        Collections.sort(result);
        return result;
    }

    // 某門課之後可解鎖哪些課（outgoing）
    public List<String> successorsOf(String course) {
        Set<String> s = prerequisites.get(normalize(course));
        return s == null ? List.of() : new ArrayList<>(s);
    }

    public int outDegree(String course) {
        Set<String> s = prerequisites.get(normalize(course));
        return s == null ? 0 : s.size();
    }

    public int inDegree(String course) {
        String t = normalize(course);
        int count = 0;
        for (Set<String> s : prerequisites.values()) if (s.contains(t)) count++;
        return count;
    }

    public void printReport() {
        System.out.println("=== Course Dependency Report ===");
        for (String code : prerequisites.keySet()) {
            System.out.printf("%-8s prerequisites=%s  successors=%s  in=%d  out=%d%n",
                    code,
                    prerequisitesOf(code),
                    successorsOf(code),
                    inDegree(code),
                    outDegree(code));
        }
    }

    private String normalize(String code) {
        return code == null ? "" : code.trim().toUpperCase();
    }

    public static void main(String[] args) {
        CourseDependencyGraph g = new CourseDependencyGraph();
        for (String c : Arrays.asList("CS101", "CS201", "CS301", "DS101", "DS201"))
            g.addCourse(c);

        // CS101 → CS201（CS101 是 CS201 的先修）
        g.addDependency("CS101", "CS201");
        g.addDependency("CS101", "DS101");
        g.addDependency("CS201", "CS301");
        g.addDependency("DS101", "DS201");
        g.addDependency("CS201", "DS201");

        g.printReport();

        System.out.println("\nprerequisitesOf CS301: " + g.prerequisitesOf("CS301"));
        System.out.println("successorsOf    CS101: " + g.successorsOf("CS101"));
        System.out.println("inDegree DS201: " + g.inDegree("DS201")); // 2
    }
}