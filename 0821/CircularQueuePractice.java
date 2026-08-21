import java.util.Arrays;

public class CircularQueuePractice {

    static class CircularQueue<T> {
        private final Object[] data;
        private int front, rear, size;

        CircularQueue(int capacity) {
            data = new Object[Math.max(1, capacity)];
        }

        boolean enqueue(T value) {
            if (isFull()) return false;
            data[rear] = value;
            rear = (rear + 1) % data.length;
            size++;
            return true;
        }

        @SuppressWarnings("unchecked")
        T dequeue() {
            if (isEmpty()) return null;
            T value = (T) data[front];
            data[front] = null;
            front = (front + 1) % data.length;
            size--;
            return value;
        }

        @SuppressWarnings("unchecked")
        T peek()      { return isEmpty() ? null : (T) data[front]; }
        boolean isEmpty() { return size == 0; }
        boolean isFull()  { return size == data.length; }
        int     size()    { return size; }

        void printState(String op) {
            System.out.printf("%-20s array=%-22s front=%d rear=%d size=%d%n",
                    op, Arrays.toString(data), front, rear, size);
        }
    }

    public static void main(String[] args) {
        CircularQueue<String> q = new CircularQueue<>(4);

        q.enqueue("A"); q.printState("enqueue A");
        q.enqueue("B"); q.printState("enqueue B");
        q.enqueue("C"); q.printState("enqueue C");
        q.dequeue();    q.printState("dequeue");
        q.dequeue();    q.printState("dequeue");
        q.enqueue("D"); q.printState("enqueue D");
        q.enqueue("E"); q.printState("enqueue E");
        q.enqueue("F"); q.printState("enqueue F");
        q.dequeue();    q.printState("dequeue");
        q.enqueue("G"); q.printState("enqueue G");

        System.out.println("\n依 FIFO 順序取出全部：");
        while (!q.isEmpty()) System.out.print(q.dequeue() + " ");
        System.out.println();
    }
}