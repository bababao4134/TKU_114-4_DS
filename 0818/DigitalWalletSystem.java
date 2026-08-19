public class DigitalWalletSystem {

    static class DigitalWallet {
        private final String walletId;
        private final String owner;
        private int balance;
        private int transactionCount;

        DigitalWallet(String walletId, String owner) {
            this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
            this.owner    = (owner    == null || owner.isBlank())    ? "Unknown" : owner;
            this.balance  = 0;
            this.transactionCount = 0;
        }

        boolean deposit(int amount) {
            if (amount <= 0) return false;
            balance += amount;
            transactionCount++;
            System.out.println("[DEPOSIT] " + amount + " -> balance=" + balance);
            return true;
        }

        boolean pay(int amount) {
            if (amount <= 0 || amount > balance) return false;
            balance -= amount;
            transactionCount++;
            System.out.println("[PAY] " + amount + " -> balance=" + balance);
            return true;
        }

        boolean refund(int amount) {
            if (amount <= 0) return false;
            balance += amount;
            transactionCount++;
            System.out.println("[REFUND] " + amount + " -> balance=" + balance);
            return true;
        }

        int getBalance()          { return balance;          }
        int getTransactionCount() { return transactionCount; }

        void printSummary() {
            System.out.println(walletId + " owner=" + owner
                    + " balance=" + balance
                    + " transactions=" + transactionCount);
        }
    }

    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W001", "Amy");

        System.out.println("=== 操作測試 ===");
        System.out.println("deposit 1000 : " + wallet.deposit(1000));
        System.out.println("pay 300     : " + wallet.pay(300));
        System.out.println("pay 800     : " + wallet.pay(800));  // 餘額不足
        System.out.println("deposit -50  : " + wallet.deposit(-50)); // 負數
        System.out.println("refund 100   : " + wallet.refund(100));

        System.out.println("\n=== 摘要 ===");
        wallet.printSummary();
    }
}