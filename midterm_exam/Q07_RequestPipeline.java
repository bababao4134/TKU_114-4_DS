import java.util.*;

public class Q07_RequestPipeline {

    public static boolean isBalanced(String text) {
        if (text == null) return false;
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : text.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }
        return stack.isEmpty();
    }

    private static String takeUrgentCheckpoint(Deque<String> urgentQ) {
        return urgentQ.pollFirst();
    }

    public static List<String> process(String[] commands) {
        if (commands == null) return new ArrayList<>();
        Deque<String> normalQ = new ArrayDeque<>();
        Deque<String> urgentQ = new ArrayDeque<>();
        List<String>  result  = new ArrayList<>();

        for (String cmd : commands) {
            if (cmd == null || cmd.isBlank()) continue;
            String[] parts = cmd.split("\\s+", 2);
            if (parts.length < 2) {
                if ("PROCESS".equals(parts[0])) {
                    String taken = takeUrgentCheckpoint(urgentQ);
                    if (taken != null)       result.add(taken);
                    else if (!normalQ.isEmpty()) result.add(normalQ.pollFirst());
                    else                         result.add("EMPTY");
                }
                continue;
            }
            String type = parts[0];
            String id   = parts[1].trim();
            if (id.isEmpty()) continue;
            switch (type) {
                case "NORMAL"  -> normalQ.offerLast(id);
                case "URGENT"  -> urgentQ.offerLast(id);
                default        -> {}
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // isBalanced 測試
        System.out.println(isBalanced("a{b[c](d)}")); // true
        System.out.println(isBalanced("([)]"));        // false
        System.out.println(isBalanced(""));             // true
        System.out.println(isBalanced(null));           // false
        System.out.println(isBalanced("((()"));         // false
        System.out.println(isBalanced("{[()]}"));       // true

        // process 測試
        String[] commands = {
            "NORMAL N1", "URGENT U1", "NORMAL N2", "PROCESS", "PROCESS", "PROCESS"
        };
        System.out.println(process(commands)); // [U1, N1, N2]

        // EMPTY 測試
        System.out.println(process(new String[]{"PROCESS"})); // [EMPTY]

        // null commands
        System.out.println(process(null)); // []

        // null / 空白 command 略過
        System.out.println(process(new String[]{null, "  ", "NORMAL X", "PROCESS"})); // [X]
    }
}