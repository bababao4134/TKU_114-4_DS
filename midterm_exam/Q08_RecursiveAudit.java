public class Q08_RecursiveAudit {

    public static int sumValid(int[] data, int index) {
        // recursion-proof C8-41
        if (data == null) return 0;
        if (index < 0)    return sumValid(data, 0);
        if (index >= data.length) return 0;
        int current = (data[index] >= 0 && data[index] <= 100) ? data[index] : 0;
        return current + sumValid(data, index + 1);
    }

    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null) return 0;
        if (index < 0)    return countOccurrences(data, 0, target);
        if (index >= data.length) return 0;
        int match = data[index] == target ? 1 : 0;
        return match + countOccurrences(data, index + 1, target);
    }

    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) return false;
        if (left >= right) return true;
        char l = Character.toLowerCase(text.charAt(left));
        char r = Character.toLowerCase(text.charAt(right));
        if (l != r) return false;
        return isPalindrome(text, left + 1, right - 1);
    }

    public static void main(String[] args) {
        int[] data = {10, -1, 20, 101, 20};
        System.out.println(sumValid(data, 0));              // 50
        System.out.println(countOccurrences(data, 0, 20)); // 2
        System.out.println(isPalindrome("Level", 0, 4));   // true

        // 邊界測試
        System.out.println(sumValid(null, 0));              // 0
        System.out.println(sumValid(data, -3));             // 負數 index 從 0 開始 = 50
        System.out.println(sumValid(data, 10));             // index 超出 = 0
        System.out.println(sumValid(new int[]{}, 0));       // 空陣列 = 0

        System.out.println(countOccurrences(null, 0, 20)); // 0
        System.out.println(countOccurrences(data, -1, 20)); // 負數 index 從 0 = 2

        System.out.println(isPalindrome(null, 0, 3));       // false
        System.out.println(isPalindrome("A", 0, 0));        // true（left==right）
        System.out.println(isPalindrome("racecar", 0, 6));  // true
        System.out.println(isPalindrome("hello",   0, 4));  // false
    }
}