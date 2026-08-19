public class AccountTransferService {

    static class Account {
        private String id;
        private int    balance;

        Account(String id, int initialBalance) {
            this.id      = id;
            this.balance = Math.max(0, initialBalance);
        }

        boolean withdraw(int amount) {
            if (amount <= 0 || amount > balance) return false;
            balance -= amount;
            return true;
        }

        void deposit(int amount) {
            if (amount > 0) balance += amount;
        }

        int    getBalance() { return balance; }
        String getId()      { return id;      }

        @Override
        public String toString() { return id + " balance=" + balance; }
    }

    static class TransferService {
        static boolean transfer(Account source, Account target, int amount) {
            // 驗證：null、同帳戶、金額
            if (source == null || target == null) {
                System.out.println("[FAIL] null 帳戶");
                return false;
            }
            if (source == target) {
                System.out.println("[FAIL] 來源與目標相同");
                return false;
            }
            if (amount <= 0) {
                System.out.println("[FAIL] 金額必須大於 0");
                return false;
            }
            // withdraw 已驗證餘額是否足夠
            if (!source.withdraw(amount)) {
                System.out.println("[FAIL] 餘額不足");
                return false;
            }
            target.deposit(amount);
            System.out.println("[OK] 轉帳 " + amount + " from " + source.getId()
                    + " to " + target.getId());
            return true;
        }
    }

    public static void main(String[] args) {
        Account a = new Account("A101", 1000);
        Account b = new Account("B202",  200);

        System.out.println("=== 成功轉帳 ===");
        TransferService.transfer(a, b, 300);
        System.out.println(a); System.out.println(b);

        System.out.println("\n=== 餘額不足 ===");
        TransferService.transfer(a, b, 9999);
        System.out.println(a); System.out.println(b);

        System.out.println("\n=== 同帳戶轉帳 ===");
        TransferService.transfer(a, a, 100);

        System.out.println("\n=== null 目標 ===");
        TransferService.transfer(a, null, 100);

        System.out.println("\n=== 最終狀態 ===");
        System.out.println(a); System.out.println(b);
    }
}