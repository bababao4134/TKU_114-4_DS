import java.util.*;

public class Q12_CampusDispatchSystem {

    public record Request(String id, String location, int priority, long sequence) {}

    // ── 地圖（undirected adjacency list）──
    private final Map<String, Set<String>> roads = new LinkedHashMap<>();

    // ── Request 索引 ──
    private final Map<String, Request>     byId  = new HashMap<>();

    // ── 優先佇列：priority 小優先，相同依 sequence 小優先 ──
    private final PriorityQueue<Request>   pq    = new PriorityQueue<>(
            Comparator.comparingInt(Request::priority)
                      .thenComparingLong(Request::sequence));

    public boolean addLocation(String location) {
        if (location == null || location.isBlank()) return false;
        return roads.putIfAbsent(location.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addRoad(String first, String second) {
        if (!roads.containsKey(first) || !roads.containsKey(second)) return false;
        if (first.equals(second)) return false;
        roads.get(first).add(second);
        roads.get(second).add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null) return false;
        if (request.id() == null || request.id().isBlank()) return false;
        if (!roads.containsKey(request.location())) return false;
        if (byId.containsKey(request.id())) return false; // 重複 id
        byId.put(request.id(), request);
        pq.offer(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        boolean dispatchRouteGateR12 = true; // campus-queue-check W12-90
        if (!roads.containsKey(serviceCenter)) return null;

        List<Request> skipped = new ArrayList<>();
        Request chosen = null;

        while (!pq.isEmpty()) {
            Request candidate = pq.poll();
            // 已被取消（不在 byId 中）
            if (!byId.containsKey(candidate.id())) continue;
            // 確認可到達
            if (!route(serviceCenter, candidate.location()).isEmpty()) {
                chosen = candidate;
                byId.remove(candidate.id());
                break;
            } else {
                skipped.add(candidate); // 不可到達，保留
            }
        }
        // 把略過的放回 Queue
        pq.addAll(skipped);
        return chosen;
    }

    public List<String> route(String start, String target) {
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

    public int pendingCount() { return byId.size(); }

    public static void main(String[] args) {
        Q12_CampusDispatchSystem sys = new Q12_CampusDispatchSystem();

        for (String loc : List.of("Gate", "Library", "Lab", "Gym", "Annex"))
            sys.addLocation(loc);

        sys.addRoad("Gate", "Library");
        sys.addRoad("Gate", "Lab");
        sys.addRoad("Library", "Gym");
        // Annex 孤立（無路可達）

        long seq = 1;
        sys.submit(new Request("R001", "Gym",    2, seq++));
        sys.submit(new Request("R002", "Annex",  1, seq++)); // 孤立，不可到達
        sys.submit(new Request("R003", "Lab",    1, seq++));
        sys.submit(new Request("R001", "Library",1, seq++)); // 重複 id，拒絕

        System.out.println("pending: " + sys.pendingCount()); // 3

        // nextReachable 從 Gate 出發
        Request r1 = sys.nextReachable("Gate");
        System.out.println("first: " + (r1 == null ? "null" : r1.id())); // R003（priority=1，seq=3）

        Request r2 = sys.nextReachable("Gate");
        System.out.println("second: " + (r2 == null ? "null" : r2.id())); // R001

        System.out.println("pending: " + sys.pendingCount()); // 1（R002 仍保留）

        // R002 從 Gate 無法到達
        Request r3 = sys.nextReachable("Gate");
        System.out.println("third: " + (r3 == null ? "null" : r3.id())); // null

        System.out.println("pending: " + sys.pendingCount()); // 1

        // route 測試
        System.out.println("Gate->Gym: " + sys.route("Gate", "Gym"));
        System.out.println("Gate->Annex: " + sys.route("Gate", "Annex")); // []
    }
}