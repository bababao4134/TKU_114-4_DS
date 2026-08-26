import java.util.*;

public class Q06_EnrollmentIndex {

    private final Map<String, Set<String>> enrollmentMapR26 = new TreeMap<>();

    private boolean invalid(String s) {
        return s == null || s.isBlank();
    }

    public boolean enroll(String courseCode, String studentId) {
        if (invalid(courseCode) || invalid(studentId)) return false;
        enrollmentMapR26.computeIfAbsent(courseCode, k -> new TreeSet<>());
        return enrollmentMapR26.get(courseCode).add(studentId);
    }

    public boolean drop(String courseCode, String studentId) {
        if (invalid(courseCode) || invalid(studentId)) return false;
        Set<String> students = enrollmentMapR26.get(courseCode);
        if (students == null || !students.remove(studentId)) return false;
        if (students.isEmpty()) enrollmentMapR26.remove(courseCode);
        return true;
    }

    public int courseSize(String courseCode) {
        Set<String> students = enrollmentMapR26.get(courseCode);
        return students == null ? 0 : students.size();
    }

    public List<String> studentsOf(String courseCode) {
        Set<String> students = enrollmentMapR26.get(courseCode);
        if (students == null) return new ArrayList<>();
        return new ArrayList<>(new TreeSet<>(students));
    }

    public List<String> coursesOf(String studentId) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : enrollmentMapR26.entrySet())
            if (entry.getValue().contains(studentId)) result.add(entry.getKey());
        Collections.sort(result);
        return result;
    }

    public Map<String, Integer> summary() {
        Map<String, Integer> result = new TreeMap<>();
        for (Map.Entry<String, Set<String>> entry : enrollmentMapR26.entrySet())
            result.put(entry.getKey(), entry.getValue().size());
        return result;
    }

    public static void main(String[] args) {
        Q06_EnrollmentIndex index = new Q06_EnrollmentIndex();
        index.enroll("DS",   "S02");
        index.enroll("DS",   "S01");
        index.enroll("JAVA", "S01");
        System.out.println(index.studentsOf("DS"));   // [S01, S02]
        System.out.println(index.coursesOf("S01"));   // [DS, JAVA]
        System.out.println(index.summary());           // {DS=2, JAVA=1}

        // 重複選課
        System.out.println(index.enroll("DS", "S01")); // false

        // null / blank 回傳 false
        System.out.println(index.enroll(null, "S01")); // false
        System.out.println(index.enroll("DS", "  ")); // false

        // drop 後課程無人則移除
        index.enroll("ALG", "S03");
        System.out.println(index.drop("ALG", "S03")); // true
        System.out.println(index.summary());           // {DS=2, JAVA=1}（ALG 消失）

        // 查詢不存在課程
        System.out.println(index.studentsOf("XYZ")); // []
        System.out.println(index.courseSize("XYZ"));  // 0
    }
}