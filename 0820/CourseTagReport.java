import java.util.*;

public class CourseTagReport {
    public static void main(String[] args) {
        String[] rawTags = {"Java", "Tree", "java", "Graph", "Tree", "LinkedList", "Graph", "Java"};

        List<String>         tagList    = new ArrayList<>();
        Set<String>          tagSet     = new HashSet<>();
        Map<String, Integer> tagCount   = new HashMap<>();

        for (String tag : rawTags) {
            String lower = tag.toLowerCase();
            tagList.add(lower);
            tagSet.add(lower);
            tagCount.put(lower, tagCount.getOrDefault(lower, 0) + 1);
        }

        System.out.println("=== List（保存原始加入順序）===");
        System.out.println(tagList);
        System.out.println("用途：需要保留加入順序或允許重複時使用");

        System.out.println("\n=== Set（不重複標籤）===");
        System.out.println(tagSet);
        System.out.println("用途：快速判斷某標籤是否存在，自動去重");

        System.out.println("\n=== Map（統計次數）===");
        tagCount.forEach((tag, count) ->
                System.out.println(tag + " -> " + count));
        System.out.println("用途：以標籤為 key 快速查詢出現次數");

        System.out.println("\n出現超過一次的標籤：");
        tagCount.forEach((tag, count) -> {
            if (count > 1) System.out.println(tag + " (" + count + "次)");
        });
    }
}