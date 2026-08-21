import java.util.*;

public class WildcardNumberTools {

    static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double total = 0.0;
        for (Number v : values) total += v.doubleValue();
        return total / values.size();
    }

    static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) return Double.NaN;
        double max = values.get(0).doubleValue();
        for (Number v : values)
            if (v.doubleValue() > max) max = v.doubleValue();
        return max;
    }

    static void addRange(List<? super Integer> target, int start, int end) {
        if (start > end) return; // 不加入任何資料
        for (int i = start; i <= end; i++) target.add(i);
    }

    public static void main(String[] args) {
        List<Integer> intList    = new ArrayList<>(Arrays.asList(80, 90, 70, 60));
        List<Double>  doubleList = new ArrayList<>(Arrays.asList(1.5, 2.5, 3.0));
        List<Number>  numList    = new ArrayList<>();

        // average 同時接收 Integer 與 Double List
        System.out.printf("int   average : %.2f%n", average(intList));
        System.out.printf("double average: %.2f%n", average(doubleList));

        // maximum
        System.out.printf("int   max: %.1f%n", maximum(intList));
        System.out.printf("double max: %.1f%n", maximum(doubleList));

        // 空 list
        System.out.println("empty average : " + average(new ArrayList<>()));
        System.out.println("empty maximum : " + maximum(new ArrayList<>()));

        // addRange
        addRange(numList, 1, 5);
        System.out.println("addRange 1-5: " + numList);

        addRange(intList, 10, 9); // start > end，不加入
        System.out.println("addRange 10-9（無效）: " + intList);
    }
}