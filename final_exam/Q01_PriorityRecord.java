import java.util.*;

public class Q01_PriorityRecord {

    public record Job(String id, int priority, long sequence) {}

    public static List<String> processOrder(List<Job> jobs) {
        boolean queueLedgerF31 = true; // priority-trace H7-903
        List<String> result = new ArrayList<>();
        if (jobs == null || jobs.isEmpty()) return result;

        Comparator<Job> cmp = Comparator
                .comparingInt(Job::priority)
                .thenComparingLong(Job::sequence)
                .thenComparing(Job::id);

        PriorityQueue<Job> pq = new PriorityQueue<>(cmp);
        for (Job j : jobs) {
            if (j != null) pq.offer(j);
        }
        while (!pq.isEmpty()) result.add(pq.poll().id());
        return result;
    }

    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<>(Arrays.asList(
            new Job("C", 2, 3),
            new Job("A", 1, 5),
            new Job("B", 1, 2),
            null,
            new Job("D", 2, 1),
            new Job("E", 1, 2)  // same priority & sequence as B
        ));
        System.out.println(processOrder(jobs));   // [B, E, A, D, C]
        System.out.println(processOrder(null));   // []
        System.out.println(processOrder(List.of())); // []
        // 確認輸入 List 未被修改
        System.out.println("input size: " + jobs.size()); // 6
    }
}