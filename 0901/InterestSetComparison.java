import java.util.*;

public class InterestSetComparison {

    // union：兩者都有的所有元素（不修改輸入）
    static Set<String> union(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(a);
        result.addAll(b);
        return result;
    }

    // intersection：兩者都有的元素
    static Set<String> intersection(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(a);
        result.retainAll(b);
        return result;
    }

    // firstOnly：只有 a 有的元素
    static Set<String> firstOnly(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(a);
        result.removeAll(b);
        return result;
    }

    // secondOnly：只有 b 有的元素
    static Set<String> secondOnly(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(b);
        result.removeAll(a);
        return result;
    }

    public static void main(String[] args) {
        Set<String> alice = new HashSet<>(Arrays.asList("Reading", "Coding", "Gaming", "Music"));
        Set<String> bob   = new HashSet<>(Arrays.asList("Coding",  "Music",  "Hiking", "Cooking"));

        System.out.println("Union       : " + union(alice, bob));
        System.out.println("Intersection: " + intersection(alice, bob));
        System.out.println("Alice only  : " + firstOnly(alice, bob));
        System.out.println("Bob only    : " + secondOnly(alice, bob));

        // 確認輸入 Set 沒有被修改
        System.out.println("Alice intact: " + new TreeSet<>(alice));
        System.out.println("Bob intact  : " + new TreeSet<>(bob));

        // 邊界：空 Set
        Set<String> empty = new HashSet<>();
        System.out.println("Union empty  : " + union(empty, bob));
        System.out.println("Inter empty  : " + intersection(empty, bob));
    }
}