abstract class Device {
    private String deviceId;

    Device(String deviceId) { this.deviceId = deviceId; }

    String getDeviceId() { return deviceId; }

    abstract void runDiagnostic();
}

class Laptop extends Device {
    Laptop(String id) { super(id); }

    @Override
    public void runDiagnostic() {
        System.out.println("[Laptop " + getDeviceId() + "] 診斷：CPU/記憶體正常");
    }
}

class Printer extends Device {
    Printer(String id) { super(id); }

    @Override
    public void runDiagnostic() {
        System.out.println("[Printer " + getDeviceId() + "] 診斷：紙匣、墨水檢查中");
    }

    void cleanPrintHead() {
        System.out.println("[Printer " + getDeviceId() + "] 清潔列印頭完成");
    }
}

class Router extends Device {
    Router(String id) { super(id); }

    @Override
    public void runDiagnostic() {
        System.out.println("[Router " + getDeviceId() + "] 診斷：網路連線正常");
    }
}

public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = {
            new Laptop("L-01"),
            new Printer("P-01"),
            new Router("R-01"),
            new Printer("P-02"),
        };

        System.out.println("=== 所有設備診斷 ===");
        for (Device d : devices) {
            d.runDiagnostic();
            // pattern matching instanceof：只對 Printer 執行清潔
            if (d instanceof Printer printer) {
                printer.cleanPrintHead();
            }
        }
    }
}