import java.util.*;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList(
            "Amy", null, "Ben", "  ", "Cara", "Amy", "Ben", null, "Dave", ""
        ));

        System.out.println("=== 清理前 ===");
        System.out.println(names);

        // 使用 Iterator 移除 null、空白、空字串
        Iterator<String> iter = names.iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            if (name == null || name.isBlank()) iter.remove();
        }

        System.out.println("\n=== 清理後 ===");
        System.out.println(names);

        // 使用 Set 找重複姓名
        Set<String> seen     = new HashSet<>();
        Set<String> duplicates = new HashSet<>();
        for (String name : names) {
            if (!seen.add(name)) duplicates.add(name);
        }

        System.out.println("\n=== 重複報告 ===");
        if (duplicates.isEmpty()) {
            System.out.println("無重複姓名");
        } else {
            System.out.println("重複出現的姓名：" + duplicates);
        }
    }
}