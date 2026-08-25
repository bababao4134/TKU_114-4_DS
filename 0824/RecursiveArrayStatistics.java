public class RecursiveArrayStatistics {

    // Public wrapper：null / empty 拋出例外
    public static int maximum(int[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("array is null or empty");
        return maxHelper(values, 0, values[0]);
    }

    public static int minimum(int[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("array is null or empty");
        return minHelper(values, 0, values[0]);
    }

    public static int countAbove(int[] values, int threshold) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("array is null or empty");
        return countHelper(values, 0, threshold);
    }

    // Private helper（不複製陣列，只傳 index）
    private static int maxHelper(int[] v, int i, int current) {
        if (i >= v.length) return current;
        return maxHelper(v, i + 1, Math.max(current, v[i]));
    }

    private static int minHelper(int[] v, int i, int current) {
        if (i >= v.length) return current;
        return minHelper(v, i + 1, Math.min(current, v[i]));
    }

    private static int countHelper(int[] v, int i, int threshold) {
        if (i >= v.length) return 0;
        int match = v[i] > threshold ? 1 : 0;
        return match + countHelper(v, i + 1, threshold);
    }

    public static void main(String[] args) {
        int[] data = {7, 2, 15, -3, 9, 4};
        System.out.println("maximum=" + maximum(data));     // 15
        System.out.println("minimum=" + minimum(data));     // -3
        System.out.println("countAbove(5)=" + countAbove(data, 5)); // 3

        int[] single = {42};
        System.out.println("single max=" + maximum(single));

        // 邊界測試：null / empty 拋出例外
        try { maximum(null); }
        catch (IllegalArgumentException e) { System.out.println("null: " + e.getMessage()); }

        try { minimum(new int[]{}); }
        catch (IllegalArgumentException e) { System.out.println("empty: " + e.getMessage()); }
    }
}