import java.util.*;

public class ServiceCenterWorkflow {

    static class ServiceTicket {
        private String id;
        private String issue;
        private boolean done;

        ServiceTicket(String id, String issue) {
            this.id    = id;
            this.issue = issue;
        }

        String getId()    { return id;   }
        void   complete() { done = true; }
        void   reopen()   { done = false; }

        @Override
        public String toString() { return id + " [" + issue + "] done=" + done; }
    }

    static Map<String, ServiceTicket> ticketMap   = new LinkedHashMap<>();
    static Deque<ServiceTicket>       waiting     = new ArrayDeque<>();
    static Deque<ServiceTicket>       completedStack = new ArrayDeque<>();
    static Set<String>                usedIds     = new HashSet<>();

    static boolean createTicket(String id, String issue) {
        if (!usedIds.add(id)) { System.out.println("重複 id：" + id); return false; }
        ServiceTicket t = new ServiceTicket(id, issue);
        ticketMap.put(id, t);
        waiting.offerLast(t);
        System.out.println("建立：" + t);
        return true;
    }

    static void processNext() {
        ServiceTicket t = waiting.pollFirst();
        if (t == null) { System.out.println("processNext: 無等待工單"); return; }
        t.complete();
        completedStack.push(t);
        System.out.println("完成：" + t);
    }

    static void cancelWaiting(String id) {
        boolean removed = waiting.removeIf(t -> t.getId().equals(id));
        if (removed) {
            System.out.println("取消 " + id + "：成功");
        } else {
            System.out.println("取消 " + id + "：找不到或已完成");
        }
    }

    static void undoLastCompletion() {
        ServiceTicket t = completedStack.poll(); // Stack 頂端
        if (t == null) { System.out.println("undo: 無可復原工單"); return; }
        t.reopen();
        waiting.offerFirst(t); // 放回 waiting 前端
        System.out.println("復原：" + t);
    }

    static void findById(String id) {
        ServiceTicket t = ticketMap.get(id);
        System.out.println("查詢 " + id + "：" + (t == null ? "找不到" : t));
    }

    static void printSummary() {
        System.out.println("=== 摘要 ===");
        System.out.println("總工單：" + ticketMap.size());
        System.out.println("等待中：" + waiting.size());
        System.out.println("已完成：" + completedStack.size());
        System.out.println("等待清單：" + waiting);
    }

    public static void main(String[] args) {
        // 重複 id
        createTicket("S001", "網路斷線");
        createTicket("S002", "印表機故障");
        createTicket("S003", "帳號問題");
        createTicket("S001", "重複工單"); // 重複

        // 查詢
        findById("S002");
        findById("S999");

        // 處理與復原
        processNext();
        processNext();
        undoLastCompletion(); // 復原 S002
        undoLastCompletion(); // 復原 S001
        undoLastCompletion(); // 空 Stack

        // 連續兩次 undo 後 waiting 應還原
        processNext();
        processNext();
        undoLastCompletion();
        undoLastCompletion();

        // 取消等待中的工單
        cancelWaiting("S003");
        cancelWaiting("S999"); // 不存在

        // 空 Queue
        processNext();
        processNext();
        processNext(); // 空

        printSummary();
    }
}