interface MessageSender {
    boolean send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() ||
            message  == null || message.isBlank()) return false;
        if (!receiver.contains("@")) return false;
        System.out.println("[Email] to=" + receiver + " msg=" + message);
        return true;
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() ||
            message  == null || message.isBlank()) return false;
        System.out.println("[SMS] to=" + receiver + " msg=" + message);
        return true;
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() ||
            message  == null || message.isBlank()) return false;
        System.out.println("[Console] " + receiver + ": " + message);
        return true;
    }
}

public class MessageSenderSystem {
    // 只依賴 MessageSender interface，新增 sender 不需修改此 method
    static void notify(MessageSender sender, String receiver, String message) {
        boolean ok = sender.send(receiver, message);
        System.out.println("  result=" + ok);
    }

    public static void main(String[] args) {
        MessageSender email   = new EmailSender();
        MessageSender sms     = new SmsSender();
        MessageSender console = new ConsoleSender();

        notify(email,   "amy@example.com", "作業截止提醒");
        notify(email,   "invalid",         "測試");       // 無 @ 符號
        notify(sms,     "0912345678",      "課程開始");
        notify(console, "B113",            "系統通知");
        notify(console, "",                "空白接收者"); // 空白
    }
}