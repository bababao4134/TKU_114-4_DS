public class RecursiveCallReport {

    static int sum(int[] data, int index) {
        System.out.println("enter sum(index=" + index
                + ", value=" + (index < data.length ? data[index] : "N/A") + ")");

        if (data == null || index >= data.length) {
            System.out.println("return 0 (base case)");
            return 0;
        }

        int recursiveResult = sum(data, index + 1);
        int result = data[index] + recursiveResult;
        System.out.println("return " + data[index] + " + " + recursiveResult
                + " = " + result + " (index=" + index + ")");
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== 一般陣列 ===");
        int[] data = {10, 20, 30, 40};
        int total = sum(data, 0);
        System.out.println("total=" + total);

        System.out.println("\n=== 單一元素 ===");
        int[] single = {99};
        System.out.println("total=" + sum(single, 0));

        System.out.println("\n=== Empty array ===");
        System.out.println("total=" + sum(new int[]{}, 0));

        System.out.println("\n=== 從中間 index 開始 ===");
        System.out.println("total=" + sum(data, 2));
    }
}