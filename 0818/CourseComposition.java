class Instructor {
    private String id;
    private String name;

    Instructor(String id, String name) {
        this.id   = id;
        this.name = name;
    }

    String label() { return id + " " + name; }
}

class Course {
    private String     courseCode;
    private String     title;
    private Instructor instructor; // composition：Course has an Instructor

    Course(String courseCode, String title, Instructor instructor) {
        this.courseCode  = courseCode;
        this.title       = title;
        this.instructor  = instructor;
    }

    String summary() {
        return courseCode + " 《" + title + "》 instructor=" + instructor.label();
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor alice = new Instructor("I001", "Alice");

        Course java = new Course("CS101", "Java Programming", alice);
        Course ds   = new Course("CS201", "Data Structures",  alice); // 共用同一個 instructor

        System.out.println(java.summary());
        System.out.println(ds.summary());
    }
}