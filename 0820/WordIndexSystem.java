import java.util.*;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
            "Java is a powerful programming language.",
            "Data structures and algorithms are core to programming.",
            "Java uses ArrayList and HashMap from the collections framework.",
            "A linked list is a data structure.",
            "The Java language supports generic types.",
        };

        Map<String, Integer> wordCount  = new HashMap<>();
        Set<String>          uniqueWords = new HashSet<>();

        for (String sentence : sentences) {
            // 去除句點、逗號，切分單字
            String[] words = sentence
                    .replaceAll("[.,]", "")
                    .split("\\s+");

            for (String word : words) {
                String lower = word.toLowerCase();
                uniqueWords.add(lower);
                wordCount.put(lower, wordCount.getOrDefault(lower, 0) + 1);
            }
        }

        System.out.println("=== 不重複單字（共 " + uniqueWords.size() + " 個）===");
        System.out.println(uniqueWords);

        System.out.println("\n=== 出現至少兩次的單字 ===");
        wordCount.entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue() + " 次"));
    }
}