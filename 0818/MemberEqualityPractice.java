import java.util.Objects;

class LibraryMember {
    private final String memberId;
    private       String name;
    private       String email;

    LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name     = name;
        this.email    = email;
    }

    @Override
    public String toString() {
        return "LibraryMember{id='" + memberId
             + "', name='" + name
             + "', email='" + email + "'}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)  return true;
        if (other == null)  return false;                  // 與 null 比較回傳 false
        if (!(other instanceof LibraryMember m)) return false;
        return Objects.equals(memberId, m.memberId);       // 只比較 memberId
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember a = new LibraryMember("M001", "Amy", "amy@mail.com");
        LibraryMember b = new LibraryMember("M001", "Amy", "amy@work.com"); // id 同，email 不同
        LibraryMember c = a;

        System.out.println(a);
        System.out.println(b);

        System.out.println("\na == b       : " + (a == b));
        System.out.println("a.equals(b)  : " + a.equals(b));   // true（id 相同）
        System.out.println("a == c       : " + (a == c));       // true（同 reference）
        System.out.println("a.equals(c)  : " + a.equals(c));

        // 與 null 比較
        System.out.println("a.equals(null): " + a.equals(null)); // false，不拋例外
    }
}