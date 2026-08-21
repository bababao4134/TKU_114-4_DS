import java.util.Arrays;

public class DynamicArrayPractice {

    static class DynamicArray<T> {
        private Object[] data;
        private int size;

        DynamicArray(int initialCapacity) {
            data = new Object[Math.max(1, initialCapacity)];
        }

        void add(T value) {
            ensureCapacity();
            data[size++] = value;
        }

        void add(int index, T value) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException("index=" + index);
            ensureCapacity();
            for (int i = size; i > index; i--) data[i] = data[i - 1];
            data[index] = value;
            size++;
        }

        @SuppressWarnings("unchecked")
        T get(int index) {
            checkIndex(index);
            return (T) data[index];
        }

        @SuppressWarnings("unchecked")
        T set(int index, T value) {
            checkIndex(index);
            T old = (T) data[index];
            data[index] = value;
            return old;
        }

        @SuppressWarnings("unchecked")
        T remove(int index) {
            checkIndex(index);
            T removed = (T) data[index];
            for (int i = index; i < size - 1; i++) data[i] = data[i + 1];
            data[--size] = null; // 清除最後一格 reference
            return removed;
        }

        int size()     { return size;        }
        int capacity() { return data.length; }

        private void ensureCapacity() {
            if (size == data.length) {
                data = Arrays.copyOf(data, data.length * 2);
                System.out.println("[resize] capacity -> " + data.length);
            }
        }

        private void checkIndex(int index) {
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException("index=" + index + " size=" + size);
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOf(data, size));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== DynamicArray<String> ===");
        DynamicArray<String> arr = new DynamicArray<>(2);
        arr.add("A");
        arr.add("B");
        arr.add("C");         // 觸發 resize
        arr.add(1, "X");      // 中間插入
        System.out.println(arr);
        System.out.println("get(2)=" + arr.get(2));
        System.out.println("set(0,Z)=" + arr.set(0, "Z"));
        System.out.println("remove(1)=" + arr.remove(1));
        System.out.println(arr + " size=" + arr.size() + " cap=" + arr.capacity());

        System.out.println("\n=== DynamicArray<Integer> ===");
        DynamicArray<Integer> ints = new DynamicArray<>(1);
        ints.add(10); ints.add(20); ints.add(30);
        System.out.println(ints);

        System.out.println("\n=== 邊界測試 ===");
        // index -1
        try { arr.get(-1); } catch (IndexOutOfBoundsException e) { System.out.println("get(-1): " + e.getMessage()); }
        // index == size
        try { arr.get(arr.size()); } catch (IndexOutOfBoundsException e) { System.out.println("get(size): " + e.getMessage()); }
        // 空結構刪除
        DynamicArray<String> empty = new DynamicArray<>(2);
        try { empty.remove(0); } catch (IndexOutOfBoundsException e) { System.out.println("remove empty: " + e.getMessage()); }
        // add(index, value) 允許 index == size
        arr.add(arr.size(), "END");
        System.out.println("add(size): " + arr);
    }
}