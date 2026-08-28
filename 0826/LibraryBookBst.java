import java.util.*;

public class LibraryBookBst {

    static class Book {
        final  String  isbn;
               String  title;
               String  author;
               boolean available;

        Book(String isbn, String title, String author) {
            if (isbn   == null || isbn.isBlank())   throw new IllegalArgumentException("isbn invalid");
            if (title  == null || title.isBlank())  throw new IllegalArgumentException("title invalid");
            if (author == null || author.isBlank()) throw new IllegalArgumentException("author invalid");
            this.isbn      = isbn;
            this.title     = title;
            this.author    = author;
            this.available = true;
        }

        @Override
        public String toString() {
            return isbn + "|" + title + "|" + author + "|" + (available ? "available" : "borrowed");
        }
    }

    static class Node { Book data; Node left, right; Node(Book b){data=b;} }

    static Node root;

    static boolean add(Book b) {
        if (b == null) return false;
        if (root == null) { root = new Node(b); return true; }
        Node cur = root;
        while (true) {
            int cmp = b.isbn.compareTo(cur.data.isbn);
            if (cmp == 0) return false;
            if (cmp < 0) { if(cur.left ==null){cur.left =new Node(b);return true;} cur=cur.left; }
            else         { if(cur.right==null){cur.right=new Node(b);return true;} cur=cur.right; }
        }
    }

    static Book find(String isbn) {
        Node cur = root;
        while (cur != null) {
            int cmp = isbn.compareTo(cur.data.isbn);
            if (cmp == 0) return cur.data;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    static boolean borrow(String isbn) {
        Book b = find(isbn);
        if (b == null || !b.available) return false;
        b.available = false;
        return true;
    }

    static boolean returnBook(String isbn) {
        Book b = find(isbn);
        if (b == null || b.available) return false;
        b.available = true;
        return true;
    }

    static boolean remove(String isbn) {
        Book b = find(isbn);
        if (b == null) return false;
        if (!b.available) return false; // 借出中不得刪除
        root = removeNode(root, isbn);
        return true;
    }

    static Node removeNode(Node n, String isbn) {
        if (n == null) return null;
        int cmp = isbn.compareTo(n.data.isbn);
        if      (cmp < 0) n.left  = removeNode(n.left,  isbn);
        else if (cmp > 0) n.right = removeNode(n.right, isbn);
        else {
            if (n.left  == null) return n.right;
            if (n.right == null) return n.left;
            Node succ = n.right;
            while (succ.left != null) succ = succ.left;
            n.data = succ.data;
            n.right = removeNode(n.right, succ.data.isbn);
        }
        return n;
    }

    static List<Book> isbnRange(String low, String high) {
        List<Book> result = new ArrayList<>();
        if (low.compareTo(high) > 0) return result;
        rangeH(root, low, high, result);
        return result;
    }

    static void rangeH(Node n, String low, String high, List<Book> result) {
        if (n == null) return;
        if (n.data.isbn.compareTo(low)  > 0) rangeH(n.left,  low, high, result);
        if (n.data.isbn.compareTo(low)  >= 0
         && n.data.isbn.compareTo(high) <= 0) result.add(n.data);
        if (n.data.isbn.compareTo(high) < 0) rangeH(n.right, low, high, result);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left); System.out.println(n.data); inorder(n.right);
    }

    public static void main(String[] args) {
        add(new Book("ISBN-300", "Data Structures", "Sedgewick"));
        add(new Book("ISBN-100", "Clean Code",      "Martin"));
        add(new Book("ISBN-500", "SICP",            "Abelson"));
        add(new Book("ISBN-200", "Java Effective",  "Bloch"));

        System.out.println("=== 初始 Inorder ===");
        inorder(root);

        System.out.println("\n=== 借書 ===");
        System.out.println(borrow("ISBN-100")); // true
        System.out.println(borrow("ISBN-100")); // false（已借出）

        System.out.println("\n=== 借出中無法刪除 ===");
        System.out.println(remove("ISBN-100")); // false

        System.out.println("\n=== 還書 ===");
        System.out.println(returnBook("ISBN-100")); // true

        System.out.println("\n=== 刪除 ISBN-300 ===");
        System.out.println(remove("ISBN-300")); // true

        System.out.println("\n=== Range [ISBN-100, ISBN-300] ===");
        isbnRange("ISBN-100", "ISBN-300").forEach(System.out::println);

        System.out.println("\n=== 最終 Inorder ===");
        inorder(root);
    }
}