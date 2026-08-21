public class LinkedTaskListSystem {

    static class Task {
        private String id;
        private String title;

        Task(String id, String title) {
            this.id    = id;
            this.title = title;
        }

        String getId() { return id; }

        @Override
        public String toString() { return "[" + id + "] " + title; }
    }

    static class TaskNode {
        Task     task;
        TaskNode next;

        TaskNode(Task task) { this.task = task; }
    }

    static class TaskLinkedList {
        private TaskNode head;
        private int size;
        private java.util.Set<String> ids = new java.util.HashSet<>();

        boolean addFirst(Task task) {
            if (task == null || !ids.add(task.getId())) return false;
            TaskNode node = new TaskNode(task);
            node.next = head;
            head = node;
            size++;
            return true;
        }

        boolean addLast(Task task) {
            if (task == null || !ids.add(task.getId())) return false;
            TaskNode node = new TaskNode(task);
            if (head == null) { head = node; size++; return true; }
            TaskNode cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
            size++;
            return true;
        }

        Task findById(String id) {
            TaskNode cur = head;
            while (cur != null) {
                if (cur.task.getId().equals(id)) return cur.task;
                cur = cur.next;
            }
            return null;
        }

        boolean removeById(String id) {
            if (head == null) return false;
            if (head.task.getId().equals(id)) {
                ids.remove(id);
                head = head.next;
                size--;
                return true;
            }
            TaskNode prev = head, cur = head.next;
            while (cur != null) {
                if (cur.task.getId().equals(id)) {
                    prev.next = cur.next;
                    ids.remove(id);
                    size--;
                    return true;
                }
                prev = cur; cur = cur.next;
            }
            return false;
        }

        boolean insertAfter(String existingId, Task task) {
            if (task == null || !ids.add(task.getId())) return false;
            TaskNode cur = head;
            while (cur != null) {
                if (cur.task.getId().equals(existingId)) {
                    TaskNode node = new TaskNode(task);
                    node.next = cur.next;
                    cur.next = node;
                    size++;
                    return true;
                }
                cur = cur.next;
            }
            ids.remove(task.getId()); // 找不到，回滾 id 登記
            return false;
        }

        int size() { return size; }

        void printAll() {
            System.out.print("List(" + size + "): ");
            TaskNode cur = head;
            while (cur != null) {
                System.out.print(cur.task + " -> ");
                cur = cur.next;
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        // 空 list 刪除
        System.out.println("空 list removeById T999: " + list.removeById("T999"));

        list.addLast(new Task("T001", "Backup"));
        list.addLast(new Task("T002", "Update"));
        list.addLast(new Task("T003", "Report"));
        list.addFirst(new Task("T000", "Urgent"));
        list.printAll();

        // 重複 id
        System.out.println("重複 T001: " + list.addLast(new Task("T001", "Dup")));

        // findById
        System.out.println("findById T002: " + list.findById("T002"));
        System.out.println("findById T999: " + list.findById("T999"));

        // insertAfter
        System.out.println("insertAfter T001: " + list.insertAfter("T001", new Task("T001B", "After T001")));
        list.printAll();

        // 刪除 head
        System.out.println("remove head T000: " + list.removeById("T000"));
        list.printAll();

        // 刪除 middle
        System.out.println("remove middle T002: " + list.removeById("T002"));
        list.printAll();

        // 刪除 tail
        System.out.println("remove tail T003: " + list.removeById("T003"));
        list.printAll();

        // 找不到 id
        System.out.println("remove T999: " + list.removeById("T999"));
    }
}