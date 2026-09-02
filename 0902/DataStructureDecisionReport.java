public class DataStructureDecisionReport {

    record Decision(String scenario, String choice, String reason, String bigO) {}

    static Decision decide(int id, String scenario) {
        return switch (id) {
            case 1  -> new Decision(scenario, "ArrayList",
                    "依 index 隨機存取，讀取頻繁",
                    "get O(1), add末端 O(1)均攤");
            case 2  -> new Decision(scenario, "ArrayDeque (Queue)",
                    "FIFO：先到先服務，叫號、任務隊列",
                    "offer/poll O(1)");
            case 3  -> new Decision(scenario, "ArrayDeque (Stack)",
                    "LIFO：Undo/Redo、括號配對、DFS",
                    "push/pop O(1)");
            case 4  -> new Decision(scenario, "HashMap",
                    "依 key O(1) 直接查找，學號/帳號索引",
                    "get/put 平均 O(1)");
            case 5  -> new Decision(scenario, "TreeMap / BST",
                    "需要排序且有範圍查詢（成績區間、日期）",
                    "get/put O(log n)");
            case 6  -> new Decision(scenario, "PriorityQueue (Heap)",
                    "反覆取最高/最低優先資料（任務調度）",
                    "peek O(1), add/remove O(log n)");
            case 7  -> new Decision(scenario, "HashSet",
                    "快速判斷是否已看過（visited、去重）",
                    "add/contains 平均 O(1)");
            case 8  -> new Decision(scenario, "Graph adjacency list",
                    "表示多對多關係並走訪（社群、道路）",
                    "BFS/DFS O(V+E)");
            case 9  -> new Decision(scenario, "Graph adjacency matrix",
                    "vertex 少且 dense，頻繁查詢任意 edge",
                    "hasEdge O(1), 空間 O(V²)");
            case 10 -> new Decision(scenario, "LinkedList",
                    "頻繁在任意位置插入/刪除，不需 index 存取",
                    "add/remove O(1) 已知位置, get O(n)");
            case 11 -> new Decision(scenario, "HashMap + PriorityQueue",
                    "同時需要依 id 查詢與取優先任務（雙索引）",
                    "查詢 O(1), 取優 O(log n)");
            case 12 -> new Decision(scenario, "HashMap + Graph",
                    "依 id 查詢節點屬性，又需關係走訪",
                    "查詢 O(1), 走訪 O(V+E)");
            default -> new Decision(scenario, "UNKNOWN", "needs more info", "?");
        };
    }

    public static void main(String[] args) {
        String[] scenarios = {
            "依序號取第 k 個商品",
            "列印機工作先進先出",
            "瀏覽器回上一頁",
            "依學號查詢學生資料",
            "查詢 60～80 分的學生",
            "醫院急診依病情取下一位",
            "BFS visited 紀錄",
            "表示台北捷運路線並找路徑",
            "4x4 棋盤的鄰接關係",
            "文字編輯器頻繁插入字元",
            "服務請求依 id 查詢且按優先度處理",
            "用戶依 id 查詢且需追蹤好友關係",
        };
        System.out.printf("%-4s %-35s %-30s %-35s %s%n",
                "No.", "Scenario", "Choice", "Reason", "Big-O");
        System.out.println("=".repeat(120));
        for (int i = 0; i < scenarios.length; i++) {
            Decision d = decide(i + 1, scenarios[i]);
            System.out.printf("%-4d %-35s %-30s %-35s %s%n",
                    i + 1, d.scenario(), d.choice(), d.reason(), d.bigO());
        }
    }
}