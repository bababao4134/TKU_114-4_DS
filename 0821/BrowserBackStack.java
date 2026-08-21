import java.util.*;

public class BrowserBackStack {

    static Deque<String> history = new ArrayDeque<>();
    static String current = null;

    static void visit(String url) {
        if (url == null || url.isBlank()) return;
        if (current != null) history.push(current); // 把目前頁存入 stack
        current = url;
        System.out.println("visit: " + current + "  history=" + history);
    }

    static void back() {
        if (history.isEmpty()) {
            System.out.println("back: 無上一頁，目前=" + current);
            return;
        }
        current = history.pop();
        System.out.println("back: " + current + "  history=" + history);
    }

    static void showCurrent() {
        System.out.println("current: " + (current == null ? "（無）" : current));
    }

    public static void main(String[] args) {
        showCurrent();
        visit("https://google.com");
        visit("https://github.com");
        visit("https://tku.edu.tw");
        back();
        back();
        back(); // 已無上一頁
        visit("https://youtube.com");
        showCurrent();
    }
}