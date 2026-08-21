import java.util.*;

class ManagedEnrollment {
    private final String studentId;
    private final String name;
    private int          score;
    private final Set<String> tags = new HashSet<>();

    ManagedEnrollment(String studentId, String name, int score) {
        this.studentId = studentId;
        this.name      = name;
        setScore(score);
    }

    String getStudentId() { return studentId; }
    String getName()      { return name;      }
    int    getScore()     { return score;     }

    void setScore(int score) {
        this.score = Math.min(100, Math.max(0, score));
    }

    void addTag(String tag) {
        if (tag != null && !tag.isBlank())
            tags.add(tag.trim().toLowerCase());
    }

    boolean hasTag(String tag) {
        return tag != null && tags.contains(tag.trim().toLowerCase());
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score + " tags=" + tags;
    }
    
}

class CourseCollectionManager {
    private final List<ManagedEnrollment>        order  = new ArrayList<>();
    private final Set<String>                    ids    = new HashSet<>();
    private final Map<String, ManagedEnrollment> byId   = new HashMap<>();

    boolean enroll(ManagedEnrollment e) {
        if (e == null || !ids.add(e.getStudentId())) return false;
        order.add(e);
        byId.put(e.getStudentId(), e);
        return true;
    }

    // 1. updateScore
    boolean updateScore(String studentId, int score) {
        ManagedEnrollment e = byId.get(studentId);
        if (e == null) return false;
        e.setScore(score);
        return true;
    }

    // 2. findByTag
    List<ManagedEnrollment> findByTag(String tag) {
        List<ManagedEnrollment> result = new ArrayList<>();
        for (ManagedEnrollment e : order)
            if (e.hasTag(tag)) result.add(e);
        return result;
    }

    // 3. scoreDistribution
    Map<String, Integer> scoreDistribution() {
        Map<String, Integer> dist = new LinkedHashMap<>();
        dist.put("A", 0); dist.put("B", 0); dist.put("C", 0);
        dist.put("D", 0); dist.put("F", 0);
        for (ManagedEnrollment e : order) {
            String grade;
            if      (e.getScore() >= 90) grade = "A";
            else if (e.getScore() >= 80) grade = "B";
            else if (e.getScore() >= 70) grade = "C";
            else if (e.getScore() >= 60) grade = "D";
            else                         grade = "F";
            dist.put(grade, dist.get(grade) + 1);
        }
        return dist;
    }

    // 4. top(count)
    List<ManagedEnrollment> top(int count) {
        List<ManagedEnrollment> result = new ArrayList<>(order);
        result.sort(Comparator.comparingInt(ManagedEnrollment::getScore)
                .reversed()
                .thenComparing(ManagedEnrollment::getStudentId));
        return result.subList(0, Math.min(count, result.size()));
    }

    // 5. removeBelow（List、Set、Map 保持一致）
    void removeBelow(int minimum) {
        order.removeIf(e -> e.getScore() < minimum);
        ids.clear();
        byId.clear();
        for (ManagedEnrollment e : order) {
            ids.add(e.getStudentId());
            byId.put(e.getStudentId(), e);
        }
    }

    void printAll() {
        System.out.println("=== 報名名單（" + order.size() + " 人）===");
        order.forEach(System.out::println);
    }
    // 驗證三個集合是否一致（供測試用）
    void printConsistencyCheck() {
        System.out.println("order=" + order.size()
                + " set=" + ids.size()
                + " map=" + byId.size());
}
}

public class CourseCollectionManagerMain {
    public static void main(String[] args) {
        CourseCollectionManager mgr = new CourseCollectionManager();

        ManagedEnrollment amy  = new ManagedEnrollment("S101", "Amy",  88);
        ManagedEnrollment ben  = new ManagedEnrollment("S102", "Ben",  55);
        ManagedEnrollment cara = new ManagedEnrollment("S103", "Cara", 92);
        ManagedEnrollment dave = new ManagedEnrollment("S104", "Dave", 73);
        ManagedEnrollment eve  = new ManagedEnrollment("S105", "Eve",  88);
        ManagedEnrollment frank= new ManagedEnrollment("S106", "Frank",40);

        amy.addTag("Java"); amy.addTag("  ");  // 空白 tag 忽略
        cara.addTag("Tree"); cara.addTag("Java");
        dave.addTag("Graph");

        System.out.println("enroll Amy  =" + mgr.enroll(amy));
        System.out.println("enroll Ben  =" + mgr.enroll(ben));
        System.out.println("enroll Cara =" + mgr.enroll(cara));
        System.out.println("enroll Dave =" + mgr.enroll(dave));
        System.out.println("enroll Eve  =" + mgr.enroll(eve));
        System.out.println("enroll Frank=" + mgr.enroll(frank));
        System.out.println("duplicate S101=" +
                mgr.enroll(new ManagedEnrollment("S101", "Amy2", 100)));

        mgr.printAll();

        // updateScore
        System.out.println("\nupdateScore S102 -> 65：" + mgr.updateScore("S102", 65));
        System.out.println("updateScore S999 -> 80：" + mgr.updateScore("S999", 80));

        // findByTag
        System.out.println("\nfindByTag Java：" + mgr.findByTag("java"));
        System.out.println("findByTag none：" + mgr.findByTag("NoSuchTag"));

        // scoreDistribution
        System.out.println("\nscoreDistribution：" + mgr.scoreDistribution());

        // top
        System.out.println("\ntop(3)：" + mgr.top(3));
        System.out.println("top(999)：" + mgr.top(999));

        // removeBelow
        mgr.removeBelow(60);
        System.out.println("\n=== removeBelow(60) 後 ===");
        mgr.printAll();

        // 驗證三個集合一致
        mgr.printConsistencyCheck();
    }

}