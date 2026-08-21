import java.util.*;

public class ListImplementationLab {

    static void addToEnd(List<Integer> list, int value) {
        list.add(value);
    }

    static void insertAt(List<Integer> list, int index, int value) {
        if (index < 0 || index > list.size()) return;
        list.add(index, value);
    }

    static int search(List<Integer> list, int target) {
        return list.indexOf(target); // 找不到回傳 -1
    }

    static boolean removeByValue(List<Integer> list, Integer value) {
        return list.remove(value);
    }

    static int sum(List<Integer> list) {
        int total = 0;
        for (int v : list) total += v;
        return total;
    }

    static void runAll(String label, List<Integer> list) {
        addToEnd(list, 10);
        addToEnd(list, 20);
        addToEnd(list, 30);
        insertAt(list, 1, 99);
        System.out.println(label + " 插入後：" + list);
        System.out.println("搜尋 20 的 index：" + search(list, 20));
        removeByValue(list, 99);
        System.out.println("刪除 99 後：" + list);
        System.out.println("總和：" + sum(list));
        System.out.println();
    }

    public static void main(String[] args) {
        runAll("ArrayList", new ArrayList<>());
        runAll("LinkedList", new LinkedList<>());

        /*
         * 內部成本差異說明：
         *
         * ArrayList：
         *   - get(index)   O(1)，直接定位 array 位置
         *   - add(末端)    平均 O(1)，偶爾擴容 O(n)
         *   - add(中間)    O(n)，後方元素都要往後搬
         *   - remove(中間) O(n)，後方元素都要往前搬
         *   → 適合：頻繁讀取、主要在尾端操作
         *
         * LinkedList：
         *   - get(index)   O(n)，需要從 head 逐節走訪
         *   - add(首尾)    O(1)，只調整 head/tail reference
         *   - add(中間)    O(n) 找位置 + O(1) 插入
         *   - remove(已知節點) O(1)
         *   → 適合：需要頻繁在首尾操作，但較少用 index 取值
         */
    }
}