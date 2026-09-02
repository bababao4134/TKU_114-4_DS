import java.util.*;

public class CourseGradeMap {

    private final Map<String, List<Integer>> gradeMap = new TreeMap<>();

    public boolean addGrade(String courseCode, int grade) {
        if (courseCode == null || courseCode.isBlank()) return false;
        if (grade < 0 || grade > 100) return false;
        gradeMap.computeIfAbsent(courseCode.trim().toUpperCase(), k -> new ArrayList<>()).add(grade);
        return true;
    }

    public double average(String courseCode) {
        List<Integer> grades = gradeMap.get(normalize(courseCode));
        if (grades == null || grades.isEmpty()) return -1.0;
        int total = 0;
        for (int g : grades) total += g;
        return (double) total / grades.size();
    }

    public int maximum(String courseCode) {
        List<Integer> grades = gradeMap.get(normalize(courseCode));
        if (grades == null || grades.isEmpty()) return -1;
        int max = grades.get(0);
        for (int g : grades) if (g > max) max = g;
        return max;
    }

    // 依課號字典序輸出報告
    public List<String> sortedReport() {
        List<String> report = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : gradeMap.entrySet()) {
            String code    = entry.getKey();
            List<Integer> g = entry.getValue();
            double avg = 0;
            for (int v : g) avg += v;
            avg /= g.size();
            report.add(String.format("%s: count=%d avg=%.1f max=%d",
                    code, g.size(), avg, maximum(code)));
        }
        return report;
    }

    private String normalize(String code) {
        return code == null ? "" : code.trim().toUpperCase();
    }

    public static void main(String[] args) {
        CourseGradeMap cgm = new CourseGradeMap();

        cgm.addGrade("CS101", 85);
        cgm.addGrade("CS101", 90);
        cgm.addGrade("CS101", 78);
        cgm.addGrade("DS201", 92);
        cgm.addGrade("DS201", 88);
        cgm.addGrade("cs101", 95); // 正規化後與 CS101 相同

        System.out.printf("CS101 avg=%.1f%n", cgm.average("CS101")); // 87.5
        System.out.println("CS101 max=" + cgm.maximum("CS101"));      // 95
        System.out.println("DS201 avg=" + cgm.average("DS201"));

        // 不合法輸入
        System.out.println("add invalid=" + cgm.addGrade("", 80));   // false
        System.out.println("add invalid=" + cgm.addGrade("X", 101)); // false

        // 排序報表
        System.out.println("\n=== Sorted Report ===");
        cgm.sortedReport().forEach(System.out::println);
    }
}