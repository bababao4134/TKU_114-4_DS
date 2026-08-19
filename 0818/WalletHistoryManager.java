public class WalletHistoryManager {

    static final class WalletTransaction {
        private final int    sequence;
        private final String type;
        private final int    amount;
        private final int    balanceAfter;

        WalletTransaction(int seq, String type, int amount, int balanceAfter) {
            this.sequence     = seq;
            this.type         = type;
            this.amount       = amount;
            this.balanceAfter = balanceAfter;
        }

        int    getSequence() { return sequence; }
        String getType()     { return type;     }
        int    getAmount()   { return amount;   }

        @Override
        public String toString() {
            return sequence + " " + type + " " + amount + " balance=" + balanceAfter;
        }
    }

    static class DigitalWallet {
        private final String walletId;
        private final String owner;
        private int balance;
        private final WalletTransaction[] transactions;
        private int count;

        DigitalWallet(String walletId, String owner, int capacity) {
            this.walletId     = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
            this.owner        = (owner    == null || owner.isBlank())    ? "Unknown" : owner;
            this.balance      = 0;
            this.transactions = new WalletTransaction[Math.max(1, capacity)];
            this.count        = 0;
        }

        boolean deposit(int amount) {
            if (amount <= 0 || count >= transactions.length) return false;
            balance += amount;
            record("DEPOSIT", amount);
            return true;
        }

        boolean pay(int amount) {
            if (amount <= 0 || amount > balance || count >= transactions.length) return false;
            balance -= amount;
            record("PAY", amount);
            return true;
        }

        boolean refund(int amount) {
            if (amount <= 0 || count >= transactions.length) return false;
            balance += amount;
            record("REFUND", amount);
            return true;
        }

        // 來源錢包記錄 TRANSFER_OUT
        boolean recordTransferOut(int amount) {
            if (count >= transactions.length) return false;
            record("TRANSFER_OUT", amount);
            return true;
        }

        // 目標錢包記錄 TRANSFER_IN
        boolean recordTransferIn(int amount) {
            if (count >= transactions.length) return false;
            record("TRANSFER_IN", amount);
            return true;
        }

        private void record(String type, int amount) {
            transactions[count] = new WalletTransaction(count + 1, type, amount, balance);
            count++;
        }

        // 找到指定 sequence 的交易，找不到回傳 null
        WalletTransaction findTransaction(int sequence) {
            for (int i = 0; i < count; i++)
                if (transactions[i].getSequence() == sequence) return transactions[i];
            return null;
        }

        // 計算指定類型的總金額
        int totalByType(String type) {
            int total = 0;
            for (int i = 0; i < count; i++)
                if (transactions[i].getType().equalsIgnoreCase(type))
                    total += transactions[i].getAmount();
            return total;
        }

        int getBalance() { return balance; }
        int getCount()   { return count;   }

        boolean hasFreeSlot() { return count < transactions.length; }

        void printStatement() {
            System.out.println("=== " + walletId + " owner=" + owner
                    + " balance=" + balance + " ===");
            for (int i = 0; i < count; i++) System.out.println(transactions[i]);
        }
    }

    // 轉帳：來源與目標同時留下紀錄
    static boolean transferTo(DigitalWallet source, DigitalWallet target, int amount) {
        if (source == null || target == null || source == target) return false;
        if (amount <= 0 || amount > source.getBalance())          return false;
        if (!source.hasFreeSlot() || !target.hasFreeSlot())       return false;

        // 兩邊都有空間才執行
        source.balance -= amount;
        target.balance += amount;
        source.recordTransferOut(amount);
        target.recordTransferIn(amount);
        return true;
    }

    public static void main(String[] args) {
        DigitalWallet amy = new DigitalWallet("W001", "Amy", 8);
        DigitalWallet bob = new DigitalWallet("W002", "Bob", 8);

        amy.deposit(2000);
        amy.pay(300);
        amy.refund(100);

        bob.deposit(500);

        System.out.println("=== 轉帳 Amy -> Bob $400 ===");
        System.out.println("transfer: " + transferTo(amy, bob, 400));

        System.out.println("\n=== findTransaction ===");
        System.out.println("Amy seq=2 : " + amy.findTransaction(2));
        System.out.println("Amy seq=99: " + amy.findTransaction(99));

        System.out.println("\n=== totalByType ===");
        System.out.println("Amy DEPOSIT total : " + amy.totalByType("DEPOSIT"));
        System.out.println("Amy PAY total     : " + amy.totalByType("PAY"));
        System.out.println("Amy TRANSFER_OUT  : " + amy.totalByType("TRANSFER_OUT"));

        System.out.println();
        amy.printStatement();
        System.out.println();
        bob.printStatement();
    }
}