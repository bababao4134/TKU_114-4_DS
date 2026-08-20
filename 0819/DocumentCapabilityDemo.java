interface Exportable {
    String export();
}

interface Compressible {
    int compress();  // 回傳壓縮後大小（bytes）
}

class BackupDocument implements Exportable, Compressible {
    private String name;
    private int    sizeKb;

    BackupDocument(String name, int sizeKb) {
        this.name   = name;
        this.sizeKb = Math.max(0, sizeKb);
    }

    @Override
    public String export() {
        return "[EXPORT] " + name + ".bak (" + sizeKb + "KB)";
    }

    @Override
    public int compress() {
        int compressed = sizeKb / 2;
        System.out.println("[COMPRESS] " + name + ": " + sizeKb + "KB -> " + compressed + "KB");
        return compressed;
    }
}

public class DocumentCapabilityDemo {
    static void doExport(Exportable e) {
        System.out.println(e.export());
    }

    static void doCompress(Compressible c) {
        int size = c.compress();
        System.out.println("  壓縮後大小：" + size + "KB");
    }

    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("system_backup", 500);

        // 同一個物件，透過不同 interface reference 操作
        Exportable   exportRef  = doc;
        Compressible compressRef = doc;

        System.out.println("=== 匯出能力 ===");
        doExport(exportRef);

        System.out.println("\n=== 壓縮能力 ===");
        doCompress(compressRef);

        // 說明：兩個 reference 指向同一個物件
        System.out.println("\nexportRef == compressRef（同物件）：" + (exportRef == compressRef));
        System.out.println("exportRef 看不到 compress()，compressRef 看不到 export()");
    }
}