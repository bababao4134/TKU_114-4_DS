import java.util.*;

public class MetroMatrixGraph {

    private final List<String> stations;
    private final boolean[][]  edges;

    public MetroMatrixGraph(List<String> stations) {
        if (stations == null || stations.isEmpty())
            throw new IllegalArgumentException("stations must not be empty");
        this.stations = new ArrayList<>(stations);
        this.edges    = new boolean[stations.size()][stations.size()];
    }

    private int indexOf(String s) {
        int idx = stations.indexOf(s);
        if (idx < 0) throw new IllegalArgumentException("unknown station: " + s);
        return idx;
    }

    public boolean addEdge(String a, String b) {
        if (a.equals(b)) return false;
        int i = indexOf(a), j = indexOf(b);
        if (edges[i][j]) return false;
        edges[i][j] = edges[j][i] = true;
        return true;
    }

    public boolean removeEdge(String a, String b) {
        int i = indexOf(a), j = indexOf(b);
        if (!edges[i][j]) return false;
        edges[i][j] = edges[j][i] = false;
        return true;
    }

    public List<String> adjacentStations(String station) {
        int row = indexOf(station);
        List<String> result = new ArrayList<>();
        for (int col = 0; col < stations.size(); col++)
            if (edges[row][col]) result.add(stations.get(col));
        return result;
    }

    public int degree(String station) {
        int row = indexOf(station), count = 0;
        for (boolean b : edges[row]) if (b) count++;
        return count;
    }

    public int edgeCount() {
        int count = 0;
        for (int i = 0; i < stations.size(); i++)
            for (int j = i + 1; j < stations.size(); j++)
                if (edges[i][j]) count++;
        return count;
    }

    public void printMatrix() {
        System.out.println("=== Metro Matrix ===");
        System.out.printf("%12s", "");
        for (String s : stations) System.out.printf("%12s", s);
        System.out.println();
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%12s", stations.get(i));
            for (int j = 0; j < stations.size(); j++)
                System.out.printf("%12s", edges[i][j] ? "1" : "0");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<String> stationList = Arrays.asList(
                "Central", "NorthGate", "SouthEnd", "EastWing", "WestHub");
        MetroMatrixGraph metro = new MetroMatrixGraph(stationList);

        metro.addEdge("Central",  "NorthGate");
        metro.addEdge("Central",  "SouthEnd");
        metro.addEdge("Central",  "WestHub");
        metro.addEdge("NorthGate","EastWing");
        metro.addEdge("SouthEnd", "WestHub");

        System.out.println("Central adjacent: " + metro.adjacentStations("Central"));
        System.out.println("Central degree  : " + metro.degree("Central"));
        System.out.println("Edge count      : " + metro.edgeCount());

        metro.removeEdge("Central", "WestHub");
        System.out.println("After remove Central-WestHub, Central degree: " + metro.degree("Central"));

        metro.printMatrix();
    }
}