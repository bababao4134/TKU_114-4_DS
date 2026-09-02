import java.util.*;

public class LogisticsWeightedGraph {

    public record Route(String to, int cost) {
        public Route {
            if (to == null || to.isBlank()) throw new IllegalArgumentException("to");
            if (cost < 0)                   throw new IllegalArgumentException("cost must >= 0");
        }
        @Override public String toString() { return to + "($" + cost + ")"; }
    }

    private final Map<String, List<Route>> outgoing = new LinkedHashMap<>();

    public boolean addWarehouse(String name) {
        if (name == null || name.isBlank()) return false;
        return outgoing.putIfAbsent(name.trim(), new ArrayList<>()) == null;
    }

    public boolean addRoute(String from, String to, int cost) {
        if (!outgoing.containsKey(from) || !outgoing.containsKey(to)) return false;
        if (from.equals(to)) return false;
        if (cost < 0) return false;
        List<Route> routes = outgoing.get(from);
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).to().equals(to)) {
                routes.set(i, new Route(to, cost)); // 更新
                return false;
            }
        }
        routes.add(new Route(to, cost));
        return true;
    }

    public boolean removeRoute(String from, String to) {
        if (!outgoing.containsKey(from)) return false;
        return outgoing.get(from).removeIf(r -> r.to().equals(to));
    }

    public Optional<Integer> getCost(String from, String to) {
        List<Route> routes = outgoing.get(from);
        if (routes == null) return Optional.empty();
        for (Route r : routes)
            if (r.to().equals(to)) return Optional.of(r.cost());
        return Optional.empty();
    }

    public List<Route> routesFrom(String from) {
        return List.copyOf(outgoing.getOrDefault(from, List.of()));
    }

    public void printReport() {
        System.out.println("=== Logistics Network ===");
        outgoing.forEach((from, routes) ->
                System.out.println(from + " -> " + routes));
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph g = new LogisticsWeightedGraph();
        for (String w : Arrays.asList("TW", "JP", "KR", "SG", "AU"))
            g.addWarehouse(w);

        System.out.println(g.addRoute("TW", "JP", 300)); // true
        System.out.println(g.addRoute("TW", "SG", 200)); // true
        System.out.println(g.addRoute("JP", "KR",  80)); // true
        System.out.println(g.addRoute("SG", "AU",  500)); // true
        System.out.println(g.addRoute("TW", "JP", 280)); // false（更新）

        System.out.println("TW->JP cost: " + g.getCost("TW", "JP")); // 280
        System.out.println("TW->AU cost: " + g.getCost("TW", "AU")); // empty

        System.out.println("remove TW->SG: " + g.removeRoute("TW", "SG")); // true
        System.out.println("remove TW->SG again: " + g.removeRoute("TW", "SG")); // false

        // 負權重拒絕
        System.out.println("add negative: " + g.addRoute("KR", "AU", -1)); // false

        // 不存在 vertex
        System.out.println("add unknown: " + g.addRoute("TW", "US", 500)); // false

        g.printReport();
    }
}