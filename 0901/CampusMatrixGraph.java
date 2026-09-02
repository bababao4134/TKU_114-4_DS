import java.util.*;

public class CampusMatrixGraph {

    private final List<String> vertices;
    private final boolean[][]  edges;

    public CampusMatrixGraph(List<String> vertices) {
        if (vertices == null || vertices.isEmpty())
            throw new IllegalArgumentException("vertices must not be empty");
        this.vertices = new ArrayList<>(vertices);
        this.edges    = new boolean[vertices.size()][vertices.size()];
    }

    private int indexOf(String v) {
        int idx = vertices.indexOf(v);
        if (idx < 0) throw new IllegalArgumentException("unknown vertex: " + v);
        return idx;
    }

    public boolean addEdge(String a, String b) {
        if (a.equals(b)) return false;
        int i = indexOf(a), j = indexOf(b);
        if (edges[i][j]) return false; // 已存在
        edges[i][j] = edges[j][i] = true;
        return true;
    }

    public boolean removeEdge(String a, String b) {
        int i = indexOf(a), j = indexOf(b);
        if (!edges[i][j]) return false;
        edges[i][j] = edges[j][i] = false;
        return true;
    }

    public boolean hasEdge(String a, String b) {
        return edges[indexOf(a)][indexOf(b)];
    }

    public int degree(String v) {
        int row = indexOf(v), count = 0;
        for (boolean connected : edges[row]) if (connected) count++;
        return count;
    }

    public List<String> neighbors(String v) {
        int row = indexOf(v);
        List<String> result = new ArrayList<>();
        for (int col = 0; col < vertices.size(); col++)
            if (edges[row][col]) result.add(vertices.get(col));
        return result;
    }

    public int edgeCount() {
        int count = 0;
        for (int i = 0; i < vertices.size(); i++)
            for (int j = i + 1; j < vertices.size(); j++)
                if (edges[i][j]) count++;
        return count;
    }

    public void printMatrix() {
        System.out.print("  ");
        for (String v : vertices) System.out.printf("%3s", v);
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%2s", vertices.get(i));
            for (int j = 0; j < vertices.size(); j++)
                System.out.printf("%3s", edges[i][j] ? "1" : "0");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CampusMatrixGraph g = new CampusMatrixGraph(
                Arrays.asList("Library", "Gym", "Cafeteria", "Lab", "Dorm"));

        g.addEdge("Library", "Gym");
        g.addEdge("Library", "Cafeteria");
        g.addEdge("Cafeteria", "Dorm");
        g.addEdge("Lab", "Gym");
        g.addEdge("Library", "Gym"); // 重複，不計入

        System.out.println("Library neighbors: " + g.neighbors("Library"));
        System.out.println("Gym degree: " + g.degree("Gym"));
        System.out.println("edgeCount: " + g.edgeCount()); // 4
        System.out.println("Library-Lab: " + g.hasEdge("Library", "Lab")); // false

        g.removeEdge("Library", "Gym");
        System.out.println("after remove Library-Gym, degree Gym: " + g.degree("Gym"));

        System.out.println("\n=== Matrix ===");
        g.printMatrix();
    }
}