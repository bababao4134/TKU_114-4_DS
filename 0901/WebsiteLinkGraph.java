import java.util.*;

public class WebsiteLinkGraph {

    private final Map<String, Set<String>> outgoing = new LinkedHashMap<>();

    public boolean addPage(String url) {
        if (url == null || url.isBlank()) return false;
        return outgoing.putIfAbsent(url.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addLink(String from, String to) {
        if (!outgoing.containsKey(from) || !outgoing.containsKey(to)) return false;
        if (from.equals(to)) return false;
        return outgoing.get(from).add(to);
    }

    public boolean removeLink(String from, String to) {
        if (!outgoing.containsKey(from)) return false;
        return outgoing.get(from).remove(to);
    }

    public List<String> outgoingLinks(String url) {
        Set<String> out = outgoing.get(url);
        return out == null ? List.of() : new ArrayList<>(out);
    }

    public int incomingCount(String url) {
        if (!outgoing.containsKey(url)) return 0;
        int count = 0;
        for (Set<String> s : outgoing.values()) if (s.contains(url)) count++;
        return count;
    }

    // 無人連入的頁面（source 或孤島）
    public List<String> noIncomingPages() {
        List<String> result = new ArrayList<>();
        for (String url : outgoing.keySet())
            if (incomingCount(url) == 0) result.add(url);
        return result;
    }

    // 無連出的頁面（dead end）
    public List<String> noOutgoingPages() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> e : outgoing.entrySet())
            if (e.getValue().isEmpty()) result.add(e.getKey());
        return result;
    }

    public static void main(String[] args) {
        WebsiteLinkGraph g = new WebsiteLinkGraph();
        for (String url : Arrays.asList(
                "home", "about", "products", "contact", "faq"))
            g.addPage(url);

        g.addLink("home",     "about");
        g.addLink("home",     "products");
        g.addLink("home",     "contact");
        g.addLink("products", "faq");
        g.addLink("about",    "contact");

        System.out.println("home outgoing   : " + g.outgoingLinks("home"));
        System.out.println("contact incoming: " + g.incomingCount("contact")); // 2
        System.out.println("faq incoming    : " + g.incomingCount("faq"));     // 1
        System.out.println("no incoming     : " + g.noIncomingPages());        // [home]
        System.out.println("no outgoing     : " + g.noOutgoingPages());        // [contact, faq]

        g.removeLink("home", "contact");
        System.out.println("after remove, contact incoming: " + g.incomingCount("contact"));
    }
}