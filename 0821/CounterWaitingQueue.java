import java.util.*;

class Customer {
    private String ticketNo;
    private String name;

    Customer(String ticketNo, String name) {
        this.ticketNo = ticketNo;
        this.name     = name;
    }

    @Override
    public String toString() { return ticketNo + " " + name; }
}

public class CounterWaitingQueue {

    static Deque<Customer> waiting = new ArrayDeque<>();
    static int ticketCounter = 1;

    static void enqueue(String name) {
        String no = String.format("A%03d", ticketCounter++);
        Customer c = new Customer(no, name);
        waiting.offerLast(c);
        System.out.println("加入：" + c + "  等候數=" + waiting.size());
    }

    static void peekNext() {
        Customer next = waiting.peekFirst();
        System.out.println("下一位：" + (next == null ? "（無人等候）" : next));
    }

    static void serveNext() {
        Customer served = waiting.pollFirst();
        System.out.println("服務：" + (served == null ? "（無人等候）" : served));
    }

    static void showWaitingCount() {
        System.out.println("目前等候數：" + waiting.size());
    }

    public static void main(String[] args) {
        peekNext();          // 空隊列
        enqueue("Amy");
        enqueue("Ben");
        enqueue("Cara");
        peekNext();
        serveNext();
        serveNext();
        showWaitingCount();
        serveNext();
        serveNext();         // 空隊列
        showWaitingCount();
    }
}