import java.util.*;

public class TextEditorHistory {

    static Deque<String> undoStack = new ArrayDeque<>();
    static Deque<String> redoStack = new ArrayDeque<>();
    static String current = "";

    static void type(String text) {
        undoStack.push(current);
        redoStack.clear(); // 新操作後清空 redo
        current += text;
        printState("type: " + text);
    }

    static void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("undo: 無可復原");
            return;
        }
        redoStack.push(current);
        current = undoStack.pop();
        printState("undo");
    }

    static void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("redo: 無可重做");
            return;
        }
        undoStack.push(current);
        current = redoStack.pop();
        printState("redo");
    }

    static void printState(String op) {
        System.out.printf("%-20s current=%-20s undo=%s redo=%s%n",
                op, "\"" + current + "\"", undoStack, redoStack);
    }

    public static void main(String[] args) {
        type("Hello");
        type(", World");
        type("!");
        undo();
        undo();
        redo();
        type(" Java"); // 新操作後 redo 清空
        redo();        // 無可重做
        undo();
        undo();
        undo();
        undo();        // 無可復原
    }
}