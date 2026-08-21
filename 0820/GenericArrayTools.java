public class GenericArrayTools {

    // 計算 target 在 data 中出現的次數
    static <T> int countMatches(T[] data, T target) {
        if (data == null) return 0;
        int count = 0;
        for (T item : data) {
            if (target == null ? item == null : target.equals(item)) count++;
        }
        return count;
    }

    // 回傳最後一個元素，空陣列或 null 回傳 null
    static <T> T last(T[] data) {
        if (data == null || data.length == 0) return null;
        return data[data.length - 1];
    }

    // 交換兩個索引的元素，不合法索引不操作
    static <T> void swap(T[] data, int first, int second) {
        if (data == null) return;
        if (first < 0 || first >= data.length) return;
        if (second < 0 || second >= data.length) return;
        T temp = data[first];
        data[first]  = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] names = {"Amy", "Ben", "Amy", "Cara", null};

        System.out.println("countMatches Amy : " + countMatches(names, "Amy"));   // 2
        System.out.println("countMatches null: " + countMatches(names, null));    // 1
        System.out.println("countMatches Zach: " + countMatches(names, "Zach")); // 0
        System.out.println("countMatches null array: " + countMatches(null, "Amy")); // 0

        System.out.println("last: " + last(names));          // null
        System.out.println("last empty: " + last(new String[]{})); // null

        swap(names, 0, 2);
        System.out.println("after swap 0,2: " + names[0] + " " + names[2]);

        swap(names, 0, 99); // 不合法，不改變
        System.out.println("after illegal swap: " + names[0]);

        Integer[] scores = {10, 20, 30};
        System.out.println("last int: " + last(scores)); // 30
    }
}