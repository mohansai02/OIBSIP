import java.util.*;

public class ATM {
    private Map<String, User> users;

    public ATM() {
        this.users = new HashMap<>();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter user id: ");
            String userId = scanner.nextLine();
            System.out.print("Enter user pin: ");
            String userPin = scanner.nextLine();
            if (validateUser(userId, userPin)) {
                System.out.println("Login successful!");
                while (true) {
                    printMenu();
                    System.out.print("Enter option number: ");
                    int option = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character
                    switch (option) {
                        case 1:
                            displayTransactionHistory(userId);
                            break;
                        case 2:
                            withdrawMoney(userId, scanner);
                            break;
                        case 3:
                            depositMoney(userId, scanner);
                            break;
                        case 4:
                            transferMoney(userId, scanner);
                            break;
                        case 5:
                            System.out.println("Thank you for using our ATM!");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            } else {
                System.out.println("Invalid user id or user pin. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. View transaction history");
        System.out.println("2. Withdraw money");
        System.out.println("3. Deposit money");
        System.out.println("4. Transfer money");
        System.out.println("5. Quit");
    }

    private boolean validateUser(String userId, String userPin) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            if (user.getUserPin().equals(userPin)) {
                return true;
            }
        }
        return false;
    }

    private void displayTransactionHistory(String userId) {
        User user = users.get(userId);
        List<Transaction> transactionHistory = user.getTransactionHistory();
        System.out.println("\nTransaction history:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    private void withdrawMoney(String userId, Scanner scanner) {
        User user = users.get(userId);
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character
        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful. Current balance: " + user.getAccountBalance());
        } else {
            System.out.println("Withdrawal failed. Insufficient balance.");
        }
    }

    private void depositMoney(String userId, Scanner scanner) {
        User user = users.get(userId);
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character
        user.deposit(amount);
        System.out.println("Deposit successful. Current balance: " + user.getAccountBalance());
    }

    private void transferMoney(String senderId, Scanner scanner) {
        System.out.print("Enter recipient user id: ");
        String recipientId = scanner.nextLine();
        User sender = users.get(senderId);
        User recipient = users.get(recipientId);
        if (recipient != null) {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume the newline character
            if (sender.transfer(amount, recipient)) {
                System.out.println("Transfer successful. Current balance: " + sender.getAccountBalance());
            } else {
                System.out.println("Transfer failed. Insufficient balance.");
            }
        } else {
            System.out.println("Invalid recipient user id.");
        }
    }
    
    public void addUser(String userId, String userPin, double accountBalance) {
        User newUser = new User(userId, userPin, accountBalance);
        users.put(userId, newUser);
    }
    
    private class User {
        private String userId;
        private String userPin;
        private double accountBalance;
        private List<Transaction> transactionHistory;
    
        public User(String userId, String userPin, double accountBalance) {
            this.userId = userId;
            this.userPin = userPin;
            this.accountBalance = accountBalance;
            this.transactionHistory = new ArrayList<>();
        }
    
        public String getUserId() {
            return userId;
        }
    
        public String getUserPin() {
            return userPin;
        }
    
        public double getAccountBalance() {
            return accountBalance;
        }
    
        public void setAccountBalance(double accountBalance) {
            this.accountBalance = accountBalance;
        }
    
        public List<Transaction> getTransactionHistory() {
            return transactionHistory;
        }
    
        public boolean withdraw(double amount) {
            if (accountBalance >= amount) {
                accountBalance -= amount;
                transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, amount));
                return true;
            } else {
                return false;
            }
        }
    
        public void deposit(double amount) {
            accountBalance += amount;
            transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, amount));
        }
    
        public boolean transfer(double amount, User recipient) {
            if (accountBalance >= amount) {
                accountBalance -= amount;
                recipient.deposit(amount);
                transactionHistory.add(new Transaction(Transaction.Type.TRANSFER, amount, recipient.getUserId()));
                return true;
            } else {
                return false;
            }
        }
    }
    
    private class Transaction {
        private Type type;
        private double amount;
        private String recipient;
    
        public Transaction(Type type, double amount) {
            this.type = type;
            this.amount = amount;
        }
    
        public Transaction(Type type, double amount, String recipient) {
            this.type = type;
            this.amount = amount;
            this.recipient = recipient;
        }
    
        @Override
        public String toString() {
            switch (type) {
                case WITHDRAWAL:
                    return "Withdrawal of " + amount;
                case DEPOSIT:
                    return "Deposit of " + amount;
                case TRANSFER:
                    return "Transfer of " + amount + " to user " + recipient;
                default:
                    return "";
            }
        }
    
        public enum Type {
            WITHDRAWAL, DEPOSIT, TRANSFER
        }
    }
    
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.addUser("mohan", "8686", 1000.0);
        atm.addUser("sai", "0102", 2000.0);
        atm.addUser("siri", "5850", 4000.0);
        atm.run();
    }
}