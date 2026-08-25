public class RecursiveTextTools {

    // 去除空白與大小寫後的反轉
    static String reverse(String s) {
        if (s == null || s.length() <= 1) return s == null ? "" : s;
        return reverse(s.substring(1)) + s.charAt(0);
    }

    // 忽略大小寫與空白的回文判斷
    static boolean isPalindrome(String s) {
        if (s == null) return false;
        String clean = s.toLowerCase().replace(" ", "");
        return isPalHelper(clean, 0, clean.length() - 1);
    }

    private static boolean isPalHelper(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalHelper(s, left + 1, right - 1);
    }

    // 計算指定字元出現次數（忽略大小寫）
    static int countCharacter(String s, char target) {
        if (s == null || s.isEmpty()) return 0;
        char lower = Character.toLowerCase(target);
        int match = Character.toLowerCase(s.charAt(0)) == lower ? 1 : 0;
        return match + countCharacter(s.substring(1), target);
    }

    public static void main(String[] args) {
        System.out.println("=== reverse ===");
        System.out.println(reverse("Hello"));   // olleH
        System.out.println(reverse("a"));        // a
        System.out.println(reverse(""));          // (empty)

        System.out.println("\n=== isPalindrome ===");
        System.out.println("\"Level\"     : " + isPalindrome("Level"));      // true
        System.out.println("\"race a car\": " + isPalindrome("race a car"));  // false
        System.out.println("\"A man a man\": " + isPalindrome("A man a man")); // false
        System.out.println("\"a\"         : " + isPalindrome("a"));           // true
        System.out.println("\"\"          : " + isPalindrome(""));             // true

        System.out.println("\n=== countCharacter ===");
        System.out.println("'l' in \"Hello World\" : " + countCharacter("Hello World", 'l')); // 3
        System.out.println("'L' in \"Hello World\" : " + countCharacter("Hello World", 'L')); // 3
        System.out.println("'z' in \"Hello\"       : " + countCharacter("Hello", 'z'));        // 0
    }
}