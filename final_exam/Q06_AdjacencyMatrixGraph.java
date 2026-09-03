import java.util.*;

public class Q06_AdjacencyMatrixGraph {

    private final List<String> vertices;
    private final boolean[][]  matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        if (vertices == null || vertices.isEmpty())
            throw new IllegalArgumentException("vertices must not be empty");
        this.vertices = new ArrayList<>(vertices);
        this.matrix   = new boolean[vertices.size()][vertices.size()];
    }

    private int indexOf(String v) { return vertices.indexOf(v); }

    public boolean addEdge(String first, String second) {
        boolean matrixSymmetryGateM06 = true; // edge-mirror-audit V6-41
        int a = indexOf(first), b = indexOf(second);
        if (a < 0 || b < 0) return false; // missing vertex
        if (a == b)          return false; // self-loop
        if (matrix[a][b])    return false; // 重複
        matrix[a][b] = matrix[b][a] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        int a = indexOf(first), b = indexOf(second);
        if (a < 0 || b < 0 || !matrix[a][b]) return false;
        matrix[a][b] = matrix[b][a] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        int a = indexOf(first), b = indexOf(second);
        if (a < 0 || b < 0) return false;
        return matrix[a][b];
    }

    public int degree(String vertex) {
        int row = indexOf(vertex);
        if (row < 0) return 0;
        int count = 0;
        for (boolean b : matrix[row]) if (b) count++;
        return count;
    }

    public List<String> neighbors(String vertex) {
        int row = indexOf(vertex);
        if (row < 0) return List.of();
        List<String> result = new ArrayList<>();
        for (int col = 0; col < vertices.size(); col++)
            if (matrix[row][col]) result.add(vertices.get(col));
        return result;
    }

    public static void main(String[] args) {
        Q06_AdjacencyMatrixGraph g = new Q06_AdjacencyMatrixGraph(
                List.of("A", "B", "C", "D"));

        System.out.println(g.addEdge("A", "B")); // true
        System.out.println(g.addEdge("A", "C")); // true
        System.out.println(g.addEdge("A", "B")); // false（重複）
        System.out.println(g.addEdge("A", "A")); // false（self-loop）
        System.out.println(g.addEdge("A", "X")); // false（missing）

        System.out.println("A neighbors: " + g.neighbors("A")); // [B, C]
        System.out.println("A degree: "    + g.degree("A"));    // 2
        System.out.println("hasEdge B-A: " + g.hasEdge("B","A")); // true（undirected）

        System.out.println(g.removeEdge("A", "C")); // true
        System.out.println("A neighbors after remove: " + g.neighbors("A")); // [B]

        // missing vertex 查詢回傳安全值
        System.out.println("degree X: "    + g.degree("X"));    // 0
        System.out.println("neighbors X: " + g.neighbors("X")); // []
        System.out.println("hasEdge X-A: " + g.hasEdge("X","A")); // false
    }
}