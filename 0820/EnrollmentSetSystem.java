import java.util.*;

class Enrollment {
    private final String studentId;
    private final String courseCode;

    Enrollment(String studentId, String courseCode) {
        this.studentId  = studentId;
        this.courseCode = courseCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Enrollment e)) return false;
        return Objects.equals(studentId, e.studentId)
            && Objects.equals(courseCode, e.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() { return studentId + "@" + courseCode; }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> enrollments = new HashSet<>();

        System.out.println("=== 新增測試 ===");
        // 同一人加入不同課程
        System.out.println(enrollments.add(new Enrollment("S101", "CS101"))); // true
        System.out.println(enrollments.add(new Enrollment("S101", "CS201"))); // true

        // 同一人重複加入同一課程
        System.out.println(enrollments.add(new Enrollment("S101", "CS101"))); // false

        // 不同人加入同一課程
        System.out.println(enrollments.add(new Enrollment("S102", "CS101"))); // true

        System.out.println("\n=== 目前名單 ===");
        enrollments.forEach(System.out::println);
        System.out.println("size=" + enrollments.size());

        // 以新建立但身分相同的物件測試 contains
        Enrollment check = new Enrollment("S101", "CS201");
        System.out.println("\ncontains(S101@CS201)=" + enrollments.contains(check)); // true

        // 以新建立但身分相同的物件測試 remove
        boolean removed = enrollments.remove(new Enrollment("S101", "CS201"));
        System.out.println("remove(S101@CS201)=" + removed); // true
        System.out.println("size after remove=" + enrollments.size());

        // 取消不存在的報名
        System.out.println("remove(S999@CS999)=" +
                enrollments.remove(new Enrollment("S999", "CS999"))); // false
    }
}