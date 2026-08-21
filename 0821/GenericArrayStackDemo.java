public class GenericArrayStackDemo {

    static class ArrayStack<T> {
        private final Object[] data;
        private int size;

        ArrayStack(int capacity) {
            data = new Object[Math.max(1, capacity)];
        }

        boolean push(T value) {
            if (value == null || isFull()) return false;
            data[size++] = value;
            return true;
        }

        @SuppressWarnings("unchecked")
        T pop() {
            if (isEmpty()) return null;
            T value = (T) data[--size];
            data[size] = null; // 清除 reference
            return value;
        }

        @SuppressWarnings("unchecked")
        T peek() {
            return isEmpty() ? null : (T) data[size - 1];
        }

        int     size()    { return size;               }
        boolean isEmpty() { return size == 0;           }
        boolean isFull()  { return size == data.length; }
    }

    public static void main(String[] args) {
        System.out.println("=== ArrayStack<String> ===");
        ArrayStack<String> strStack = new ArrayStack<>(3);
        System.out.println("push A: " + strStack.push("A"));
        System.out.println("push B: " + strStack.push("B"));
        System.out.println("push C: " + strStack.push("C"));
        System.out.println("push D: " + strStack.push("D")); // 超出容量
        System.out.println("peek: "  + strStack.peek());
        System.out.println("pop: "   + strStack.pop());
        System.out.println("size: "  + strStack.size());
        System.out.println("isFull: "+ strStack.isFull());

        System.out.println("\n=== ArrayStack<Integer> ===");
        ArrayStack<Integer> intStack = new ArrayStack<>(2);
        System.out.println("push 10: " + intStack.push(10));
        System.out.println("push 20: " + intStack.push(20));
        System.out.println("pop: "     + intStack.pop());
        System.out.println("pop: "     + intStack.pop());
        System.out.println("pop empty: " + intStack.pop()); // null
    }
}