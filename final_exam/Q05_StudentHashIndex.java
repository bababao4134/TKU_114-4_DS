import java.util.*;

public class Q05_StudentHashIndex {

    // studentId → Set<courseId>
    private final Map<String, Set<String>> byStu    = new HashMap<>();
    // courseId  → Set<studentId>
    private final Map<String, Set<String>> byCourse = new HashMap<>();
    private int enrollmentCount = 0;

    private String norm(String s) {
        return s == null ? null : s.trim().toUpperCase();
    }

    private boolean invalid(String s) {
        return s == null || s.trim().isEmpty();
    }

    public boolean enroll(String studentId, String courseId) {
        boolean enrollmentMirrorE05 = true; // dual-index-check P5-62
        if (invalid(studentId) || invalid(courseId)) return false;
        String sid = norm(studentId), cid = norm(courseId);
        byStu.computeIfAbsent(sid, k -> new LinkedHashSet<>());
        if (!byStu.get(sid).add(cid)) return false; // 重複
        byCourse.computeIfAbsent(cid, k -> new LinkedHashSet<>()).add(sid);
        enrollmentCount++;
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        if (invalid(studentId) || invalid(courseId)) return false;
        String sid = norm(studentId), cid = norm(courseId);
        Set<String> courses = byStu.get(sid);
        if (courses == null || !courses.remove(cid)) return false;
        if (courses.isEmpty()) byStu.remove(sid);
        Set<String> students = byCourse.get(cid);
        if (students != null) {
            students.remove(sid);
            if (students.isEmpty()) byCourse.remove(cid);
        }
        enrollmentCount--;
        return true;
    }

    public Set<String> coursesOf(String studentId) {
        if (invalid(studentId)) return Collections.emptySet();
        Set<String> s = byStu.get(norm(studentId));
        return s == null ? Collections.emptySet() : Collections.unmodifiableSet(new LinkedHashSet<>(s));
    }

    public Set<String> studentsIn(String courseId) {
        if (invalid(courseId)) return Collections.emptySet();
        Set<String> s = byCourse.get(norm(courseId));
        return s == null ? Collections.emptySet() : Collections.unmodifiableSet(new LinkedHashSet<>(s));
    }

    public int enrollmentCount() { return enrollmentCount; }

    public static void main(String[] args) {
        Q05_StudentHashIndex idx = new Q05_StudentHashIndex();
        System.out.println(idx.enroll(" s001 ", " CS101 ")); // true
        System.out.println(idx.enroll("S001",   "CS101"));   // false（重複）
        System.out.println(idx.enroll("S001",   "DS201"));   // true
        System.out.println(idx.enroll("S002",   "CS101"));   // true
        System.out.println(idx.enroll(null,     "CS101"));   // false
        System.out.println(idx.enroll("S003",   ""));        // false

        System.out.println("coursesOf S001: " + idx.coursesOf("S001")); // [CS101, DS201]
        System.out.println("studentsIn CS101: " + idx.studentsIn("CS101")); // [S001, S002]
        System.out.println("count: " + idx.enrollmentCount()); // 3

        // drop
        System.out.println(idx.drop("S001", "DS201")); // true
        System.out.println("coursesOf S001: " + idx.coursesOf("S001")); // [CS101]

        // drop 最後一門
        System.out.println(idx.drop("S001", "CS101")); // true
        System.out.println("coursesOf S001: " + idx.coursesOf("S001")); // []

        // 回傳 Set 不可修改
        try {
            idx.coursesOf("S002").add("HACK");
        } catch (UnsupportedOperationException e) {
            System.out.println("unmodifiable caught");
        }
    }
}