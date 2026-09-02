import java.util.*;

public class EnrollmentConflictSet {

    record EnrollmentKey(String studentId, String courseCode) {
        EnrollmentKey {
            if (studentId  == null || studentId.isBlank())  throw new IllegalArgumentException();
            if (courseCode == null || courseCode.isBlank()) throw new IllegalArgumentException();
            studentId  = studentId.trim().toUpperCase();
            courseCode = courseCode.trim().toUpperCase();
        }

        @Override public String toString() { return studentId + "@" + courseCode; }
    }

    public static void main(String[] args) {
        String[][] records = {
            {"S001", "CS101"},
            {"S002", "CS101"},
            {"S001", "DS201"},
            {"S001", "CS101"}, // 重複
            {"S003", "CS101"},
            {"S002", "DS201"},
            {"S001", "CS101"}, // 重複
        };

        Set<EnrollmentKey>         accepted   = new LinkedHashSet<>();
        List<EnrollmentKey>        duplicates = new ArrayList<>();
        Map<String, Set<String>>   byCourse   = new TreeMap<>();  // 課程 → 修課學生
        Map<String, Set<String>>   byStudent  = new TreeMap<>();  // 學生 → 選課

        for (String[] r : records) {
            EnrollmentKey key = new EnrollmentKey(r[0], r[1]);
            if (!accepted.add(key)) {
                duplicates.add(key);
            } else {
                byCourse.computeIfAbsent(key.courseCode(), k -> new TreeSet<>())
                        .add(key.studentId());
                byStudent.computeIfAbsent(key.studentId(), k -> new TreeSet<>()).add(key.courseCode());
            }
        }

        System.out.println("=== 重複紀錄 ===");
        duplicates.forEach(System.out::println);

        System.out.println("\n=== 每位學生選課 ===");
        byStudent.forEach((s, c) -> System.out.println(s + " -> " + c));

        System.out.println("\n=== 每門課修課人數 ===");
        byCourse.forEach((c, s) -> System.out.println(c + " count=" + s.size() + " " + s));
    }
}