import java.util.*;

public class DeliveryWorkflowSystem {

    static class DeliveryTask {
        private String id;
        private String address;
        private boolean done;

        DeliveryTask(String id, String address) {
            this.id      = id;
            this.address = address;
        }

        String getId()  { return id;  }
        void   complete() { done = true;  }
        void   reopen()   { done = false; }

        @Override
        public String toString() { return id + " [" + address + "] done=" + done; }
    }

    static Map<String, DeliveryTask> taskMap   = new LinkedHashMap<>();
    static Deque<DeliveryTask>       waiting   = new ArrayDeque<>();
    static Deque<DeliveryTask>       completed = new ArrayDeque<>();
    static Set<String>               ids       = new HashSet<>();

    static boolean add(String id, String address) {
        if (!ids.add(id)) { System.out.println("重複 id：" + id); return false; }
        DeliveryTask t = new DeliveryTask(id, address);
        taskMap.put(id, t);
        waiting.offerLast(t);
        System.out.println("新增：" + t);
        return true;
    }

    static void processNext() {
        DeliveryTask t = waiting.pollFirst();
        if (t == null) { System.out.println("process: 無待配送任務"); return; }
        t.complete();
        completed.push(t);
        System.out.println("完成：" + t);
    }

    static void undo() {
        DeliveryTask t = completed.poll();
        if (t == null) { System.out.println("undo: 無可復原任務"); return; }
        t.reopen();
        waiting.offerFirst(t);
        System.out.println("復原：" + t);
    }

    static void findById(String id) {
        DeliveryTask t = taskMap.get(id);
        System.out.println("查詢 " + id + "：" + (t == null ? "找不到" : t));
    }

    static void printStats() {
        System.out.println("=== 統計 ===");
        System.out.println("總任務：" + taskMap.size());
        System.out.println("等待中：" + waiting.size());
        System.out.println("已完成：" + completed.size());
    }

    public static void main(String[] args) {
        add("D001", "台北市信義區");
        add("D002", "新北市板橋區");
        add("D003", "桃園市中壢區");
        add("D001", "重複地址");    // 重複 id

        findById("D002");
        findById("D999");

        processNext();
        processNext();
        undo();          // 復原最後完成

        processNext();
        processNext();
        processNext();   // 空 Queue

        undo();
        undo();
        undo();          // 空 Stack

        printStats();
    }
}