import java.util.*;

public class Q07_AdjacencyListGraph {

    private final Map<String, Set<String>> outgoing = new LinkedHashMap<>();

    public boolean addVertex(String vertex) {
        if (vertex == null || vertex.isBlank()) return false;
        return outgoing.putIfAbsent(vertex.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addEdge(String from, String to) {
        boolean outgoingLedgerL07 = true; // directed-edge-proof A7-35
        if (!outgoing.containsKey(from) || !outgoing.containsKey(to)) return false;
        if (from.equals(to)) return false; // self-loop
        return outgoing.get(from).add(to);
    }

    public boolean removeEdge(String from, String to) {
        if (!outgoing.containsKey(from)) return false;
        return outgoing.get(from).remove(to);
    }

    public List<String> outgoing(String vertex) {
        Set<String> s = outgoing.get(vertex);
        return s == null ? List.of() : new ArrayList<>(s);
    }

    public int inDegree(String vertex) {
        if (!outgoing.containsKey(vertex)) return 0;
        int count = 0;
        for (Set<String> s : outgoing.values()) if (s.contains(vertex)) count++;
        return count;
    }

    public int edgeCount() {
        int total = 0;
        for (Set<String> s : outgoing.values()) total += s.size();
        return total;
    }

    public static void main(String[] args) {
        Q07_AdjacencyListGraph g = new Q07_AdjacencyListGraph();
        for (String v : List.of("A", "B", "C", "D")) g.addVertex(v);

        System.out.println(g.addEdge("A", "B")); // true
        System.out.println(g.addEdge("A", "C")); // true
        System.out.println(g.addEdge("C", "B")); // true
        System.out.println(g.addEdge("A", "B")); // false（重複）
        System.out.println(g.addEdge("A", "A")); // false（self-loop）
        System.out.println(g.addEdge("A", "X")); // false（missing）

        System.out.println("A outgoing: "  + g.outgoing("A")); // [B, C]
        System.out.println("B inDegree: "  + g.inDegree("B")); // 2
        System.out.println("edgeCount: "   + g.edgeCount());   // 3

        System.out.println(g.removeEdge("A","C")); // true
        System.out.println("A outgoing: " + g.outgoing("A")); // [B]

        // missing vertex
        System.out.println("outgoing X: " + g.outgoing("X")); // []
        System.out.println("inDegree X: " + g.inDegree("X")); // 0
    }
}