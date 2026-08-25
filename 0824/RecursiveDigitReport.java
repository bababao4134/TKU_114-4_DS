public class RecursiveDigitReport {

    // 數位總和
    static int digitSum(int n) {
        if (n < 0) n = -n;
        if (n < 10) return n;
        return (n % 10) + digitSum(n / 10);
    }

    // 數位個數（0 視為 1 位）
    static int digitCount(int n) {
        if (n < 0) n = -n;
        if (n < 10) return 1;
        return 1 + digitCount(n / 10);
    }

    // 指定數字出現次數
    static int countDigit(int n, int target) {
        if (n < 0) n = -n;
        int current = (n % 10 == target) ? 1 : 0;
        if (n < 10) return current;
        return current + countDigit(n / 10, target);
    }

    static void report(int n) {
        System.out.printf("n=%-8d digitSum=%d digitCount=%d%n",
                n, digitSum(n), digitCount(n));
    }

    public static void main(String[] args) {
        report(50205);
        report(0);
        report(-731);

        System.out.println("\ncountDigit(50205, 0) = " + countDigit(50205, 0)); // 2
        System.out.println("countDigit(50205, 5) = " + countDigit(50205, 5)); // 2
        System.out.println("countDigit(-731,  7) = " + countDigit(-731,  7)); // 1
        System.out.println("countDigit(0,     0) = " + countDigit(0,     0)); // 1
    }
}