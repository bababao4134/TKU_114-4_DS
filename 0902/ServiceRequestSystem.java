import java.util.*;

public class ServiceRequestSystem {

    static class ServiceRequest implements Comparable<ServiceRequest> {
        final String id;
        final String description;
        final int    priority;   // 數字越大優先度越高

        ServiceRequest(String id, String description, int priority) {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("id");
            this.id          = id;
            this.description = description;
            this.priority    = priority;
        }

        @Override
        public int compareTo(ServiceRequest other) {
            return Integer.compare(other.priority, this.priority); // 大優先度排前
        }

        @Override
        public String toString() { return id + "[P" + priority + "] " + description; }
    }

    private final Map<String, ServiceRequest> byId      = new HashMap<>();
    private final PriorityQueue<ServiceRequest> queue   = new PriorityQueue<>();

    boolean add(ServiceRequest req) {
        if (req == null || byId.containsKey(req.id)) return false;
        byId.put(req.id, req);
        queue.offer(req);
        return true;
    }

    ServiceRequest find(String id) { return byId.get(id); }

    // 取消：兩份結構必須一致
    boolean cancel(String id) {
        ServiceRequest req = byId.remove(id);
        if (req == null) return false;
        queue.remove(req); // O(n) 但符合語意
        return true;
    }

    ServiceRequest processNext() {
        while (!queue.isEmpty()) {
            ServiceRequest req = queue.poll();
            if (byId.containsKey(req.id)) { // 確認未被取消
                byId.remove(req.id);
                return req;
            }
        }
        return null;
    }

    int pendingCount() { return byId.size(); }

    void printStatus() {
        System.out.println("pending=" + pendingCount() + " ids=" + new TreeSet<>(byId.keySet()));
    }

    public static void main(String[] args) {
        ServiceRequestSystem sys = new ServiceRequestSystem();

        sys.add(new ServiceRequest("R001", "Printer jam",     2));
        sys.add(new ServiceRequest("R002", "Server down",     5));
        sys.add(new ServiceRequest("R003", "Email slow",      1));
        sys.add(new ServiceRequest("R004", "Login issue",     4));
        sys.add(new ServiceRequest("R001", "Duplicate",       9)); // false

        sys.printStatus();

        // 取消一筆
        System.out.println("cancel R003: " + sys.cancel("R003"));
        System.out.println("cancel R999: " + sys.cancel("R999")); // false
        sys.printStatus();

        // 依優先度處理
        System.out.println("\n=== Processing ===");
        ServiceRequest next;
        while ((next = sys.processNext()) != null) {
            System.out.println("processed: " + next);
        }
        System.out.println("no more: " + sys.processNext());
        sys.printStatus();
    }
}