interface ReportExporter {
    String export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        if (values == null || values.length == 0)
            return title + ",（無資料）";
        StringBuilder sb = new StringBuilder(title);
        for (int v : values) sb.append(",").append(v);
        return "[CSV] " + sb;
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        if (values == null || values.length == 0)
            return "{\"title\":\"" + title + "\",\"values\":[]}";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(values[i]);
        }
        sb.append("]");
        return "[JSON] {\"title\":\"" + title + "\",\"values\":" + sb + "}";
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        if (values == null || values.length == 0)
            return "[TEXT] " + title + "：（無資料）";
        int total = 0;
        for (int v : values) total += v;
        return "[TEXT] " + title + " 共 " + values.length + " 筆，總計 " + total;
    }
}

public class ReportExporterFactory {
    // Factory method：不支援的 format 回傳 TextExporter
    static ReportExporter createExporter(String format) {
        if (format == null) return new TextExporter();
        return switch (format.toLowerCase()) {
            case "csv"  -> new CsvExporter();
            case "json" -> new JsonExporter();
            default     -> new TextExporter();
        };
    }

    // 只依賴 interface，主流程不使用 instanceof
    static void exportReport(ReportExporter exporter, String title, int[] values) {
        System.out.println(exporter.export(title, values));
    }

    public static void main(String[] args) {
        int[] scores = {85, 92, 78, 65, 90};

        exportReport(createExporter("csv"),     "成績報表", scores);
        exportReport(createExporter("json"),    "成績報表", scores);
        exportReport(createExporter("text"),    "成績報表", scores);
        exportReport(createExporter("unknown"), "成績報表", scores); // 預設 Text
        exportReport(createExporter("csv"),     "空報表",   null);   // null values
    }
}