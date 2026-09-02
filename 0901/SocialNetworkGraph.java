import java.util.*;

public class SocialNetworkGraph {

    private final Map<String, Set<String>> adjacency = new LinkedHashMap<>();

    public boolean addUser(String user) {
        if (user == null || user.isBlank()) return false;
        return adjacency.putIfAbsent(user.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addFriend(String a, String b) {
        if (!adjacency.containsKey(a) || !adjacency.containsKey(b)) return false;
        if (a.equals(b)) return false;
        boolean changed = adjacency.get(a).add(b);
        adjacency.get(b).add(a);
        return changed;
    }

    public boolean removeFriend(String a, String b) {
        if (!adjacency.containsKey(a) || !adjacency.containsKey(b)) return false;
        boolean changed = adjacency.get(a).remove(b);
        adjacency.get(b).remove(a);
        return changed;
    }

    public List<String> friends(String user) {
        Set<String> f = adjacency.get(user);
        return f == null ? List.of() : new ArrayList<>(f);
    }

    // 共同好友
    public List<String> commonFriends(String a, String b) {
        Set<String> fa = adjacency.get(a);
        Set<String> fb = adjacency.get(b);
        if (fa == null || fb == null) return List.of();
        Set<String> common = new TreeSet<>(fa);
        common.retainAll(fb);
        return new ArrayList<>(common);
    }

    // 孤立使用者（沒有任何好友）
    public List<String> isolatedUsers() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : adjacency.entrySet())
            if (entry.getValue().isEmpty()) result.add(entry.getKey());
        return result;
    }

    public static void main(String[] args) {
        SocialNetworkGraph sn = new SocialNetworkGraph();
        for (String u : Arrays.asList("Alice", "Bob", "Cara", "Dave", "Eve"))
            sn.addUser(u);

        sn.addFriend("Alice", "Bob");
        sn.addFriend("Alice", "Cara");
        sn.addFriend("Bob",   "Cara");
        sn.addFriend("Bob",   "Dave");

        System.out.println("Alice friends: " + sn.friends("Alice"));
        System.out.println("common Alice-Bob: " + sn.commonFriends("Alice", "Bob")); // [Cara]
        System.out.println("isolated: " + sn.isolatedUsers()); // [Eve]

        sn.removeFriend("Alice", "Bob");
        System.out.println("after unfriend, Alice friends: " + sn.friends("Alice"));

        // 重複 addFriend
        System.out.println("add again: " + sn.addFriend("Alice", "Cara")); // false

        // 不存在的使用者
        System.out.println("add friend unknown: " + sn.addFriend("Alice", "X")); // false
    }
}