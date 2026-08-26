import java.util.*;

public class Q04_NotificationRouter {

    public interface Channel {
        String  name();
        boolean supports(String destination);
        String  send(String destination, String message);
    }

    public static class EmailChannel implements Channel {
        @Override public String name() { return "EMAIL"; }

        @Override
        public boolean supports(String destination) {
            if (destination == null) return false;
            int idx = destination.indexOf('@');
            return idx > 0 && idx < destination.length() - 1;
        }

        @Override
        public String send(String destination, String message) {
            return "EMAIL|" + destination + "|" + message;
        }
    }

    public static class SmsChannel implements Channel {
        @Override public String name() { return "SMS"; }

        @Override
        public boolean supports(String destination) {
            if (destination == null) return false;
            String digits = destination.replace("-", "");
            return digits.length() == 10 && digits.chars().allMatch(Character::isDigit);
        }

        @Override
        public String send(String destination, String message) {
            return "SMS|" + destination + "|" + message;
        }
    }

    private static String routeCheckpointM26(Channel channel,
                                              String destination,
                                              String message) {
        return channel.send(destination, message);
    }

    public static List<String> route(List<Channel> channels,
                                     String destination,
                                     String message) {
        if (channels == null || destination == null || message == null)
            return new ArrayList<>();
        List<String> result = new ArrayList<>();
        for (Channel ch : channels) {
            if (ch == null) continue;
            if (ch.supports(destination))
                result.add(routeCheckpointM26(ch, destination, message));
        }
        return result;
    }

    public static void main(String[] args) {
        var channels = List.of(new EmailChannel(), new SmsChannel());

        System.out.println(route(channels, "a@b.com",      "Ready")); // [EMAIL|a@b.com|Ready]
        System.out.println(route(channels, "0912-345-678", "Go"));    // [SMS|0912-345-678|Go]

        // null 測試
        System.out.println(route(null,     "a@b.com", "Hi")); // []
        System.out.println(route(channels, null,      "Hi")); // []
        System.out.println(route(channels, "a@b.com", null)); // []

        // null channel 略過
        List<Channel> withNull = new ArrayList<>();
        withNull.add(null);
        withNull.add(new EmailChannel());
        System.out.println(route(withNull, "x@y.com", "Test")); // [EMAIL|x@y.com|Test]

        // @ 在開頭或結尾不支援
        System.out.println(route(channels, "@b.com",  "X")); // []
        System.out.println(route(channels, "a@",      "X")); // []
    }
}