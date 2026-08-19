public class CourseGradeManager {

    static class CourseGrade {
        private String id;
        private String name;
        private int    attendance; // 出席
        private int    midterm;
        private int    finalExam;
        private int    regular;   // 平時

        CourseGrade(String id, String name,
                    int attendance, int regular, int midterm, int finalExam) {
            this.id         = id;
            this.name       = name;
            this.attendance = clamp(attendance);
            this.regular    = clamp(regular);
            this.midterm    = clamp(midterm);
            this.finalExam  = clamp(finalExam);
        }

        private static int clamp(int v) { return Math.min(100, Math.max(0, v)); }

        // 平時 50%、期中 20%、期末 20%、出席 10%
        double calculateFinalScore() {
            return regular * 0.5 + midterm * 0.2 + finalExam * 0.2 + attendance * 0.1;
        }

        String getLevel() {
            double score = calculateFinalScore();
            if (score >= 90) return "A";
            if (score >= 80) return "B";
            if (score >= 70) return "C";
            if (score >= 60) return "D";
            return "F";
        }

        boolean passed() { return calculateFinalScore() >= 60; }

        @Override
        public String toString() {
            return String.format("%s %s 平時:%d 期中:%d 期末:%d 出席:%d -> %.1f(%s)",
                    id, name, regular, midterm, finalExam, attendance,
                    calculateFinalScore(), getLevel());
        }
    }

    public static void main(String[] args) {
        CourseGrade[] grades = {
            new CourseGrade("S001", "Alice", 100, 88, 82, 90),
            new CourseGrade("S002", "Bob",    60, 55, 50, 48),
            new CourseGrade("S003", "Cara",   80, 92, 88, 95),
            new CourseGrade("S004", "Dave",   70, 65, 60, 58),
            new CourseGrade("S005", "Eve",   100, 78, 75, 80),
        };

        System.out.println("=== 成績報表 ===");
        for (CourseGrade g : grades) System.out.println(g);

        // 平均
        double total = 0;
        for (CourseGrade g : grades) total += g.calculateFinalScore();
        System.out.printf("\n班級平均：%.1f%n", total / grades.length);

        // 最高分
        CourseGrade top = grades[0];
        for (CourseGrade g : grades)
            if (g.calculateFinalScore() > top.calculateFinalScore()) top = g;
        System.out.println("最高分：" + top);

        // 不及格名單
        System.out.println("\n不及格名單：");
        for (CourseGrade g : grades)
            if (!g.passed()) System.out.println("  " + g);
    }
}