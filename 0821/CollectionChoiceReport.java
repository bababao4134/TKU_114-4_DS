import java.util.*;

public class CollectionChoiceReport {
    public static void main(String[] args) {
        // 1. 保留搜尋紀錄且允許重複 -> List（有序、允許重複）
        System.out.println("=== 1. 搜尋紀錄（List<String> / ArrayList）===");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java");
        searchHistory.add("Generic");
        searchHistory.add("Java");   // 允許重複
        searchHistory.add("Stack");
        System.out.println("紀錄：" + searchHistory);
        System.out.println("第 3 筆：" + searchHistory.get(2));

        // 2. 保存不重複會員編號 -> Set（去重）
        System.out.println("\n=== 2. 會員編號（Set<String> / HashSet）===");
        Set<String> members = new HashSet<>();
        System.out.println("加入 M001：" + members.add("M001"));
        System.out.println("加入 M002：" + members.add("M002"));
        System.out.println("加入 M001：" + members.add("M001")); // 重複
        System.out.println("包含 M002：" + members.contains("M002"));
        System.out.println("不重複編號：" + members);

        // 3. 以學號查詢成績 -> Map（key 查詢）
        System.out.println("\n=== 3. 學號查詢成績（Map<String,Integer> / HashMap）===");
        Map<String, Integer> gradeMap = new HashMap<>();
        gradeMap.put("S101", 88);
        gradeMap.put("S102", 72);
        gradeMap.put("S103", 95);
        System.out.println("S101 成績：" + gradeMap.get("S101"));
        System.out.println("S999 成績：" + gradeMap.get("S999")); // null
        gradeMap.put("S101", 90); // 更新
        System.out.println("S101 更新後：" + gradeMap.get("S101"));

        // 4. 依到達順序處理列印工作 -> Queue（FIFO）
        System.out.println("\n=== 4. 列印工作（Deque<String> / ArrayDeque，作為 Queue）===");
        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offerLast("Doc-A");
        printQueue.offerLast("Doc-B");
        printQueue.offerLast("Doc-C");
        System.out.println("下一份：" + printQueue.peekFirst());
        System.out.println("列印：" + printQueue.pollFirst());
        System.out.println("列印：" + printQueue.pollFirst());
        System.out.println("剩餘：" + printQueue);

        // 5. 復原最近操作 -> Stack（LIFO）
        System.out.println("\n=== 5. 復原操作（Deque<String> / ArrayDeque，作為 Stack）===");
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("新增文字");
        undoStack.push("刪除段落");
        undoStack.push("插入圖片");
        System.out.println("最近操作：" + undoStack.peek());
        System.out.println("復原：" + undoStack.pop());
        System.out.println("復原：" + undoStack.pop());
        System.out.println("剩餘歷程：" + undoStack);
    }
}