import java.util.*;

class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with balance: " + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        addTransaction("Account holder name updated to: " + accountHolderName);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            recipient.deposit(amount);
            addTransaction("Transferred: " + amount + " to " + recipient.getAccountNumber());
            recipient.addTransaction("Received: " + amount + " from " + this.accountNumber);
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History for Account: " + accountNumber);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    private void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

public class OnlineBankingSystem {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Online Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Transaction History");
            System.out.println("6. Manage Personal Information");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositFunds();
                    break;
                case 3:
                    withdrawFunds();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    managePersonalInformation();
                    break;
                case 7:
                    System.out.println("Thank you for using the Online Banking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double initialBalance = scanner.nextDouble();

        if (!accounts.containsKey(accountNumber)) {
            accounts.put(accountNumber, new Account(accountNumber, accountHolderName, initialBalance));
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Account number already exists.");
        }
    }

    private static void depositFunds() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.print("Enter Deposit Amount: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawFunds() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.print("Enter Withdrawal Amount: ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transferFunds() {
        System.out.print("Enter Your Account Number: ");
        String fromAccountNumber = scanner.nextLine();
        Account fromAccount = accounts.get(fromAccountNumber);
        if (fromAccount != null) {
            System.out.print("Enter Recipient Account Number: ");
            String toAccountNumber = scanner.nextLine();
            Account toAccount = accounts.get(toAccountNumber);
            if (toAccount != null) {
                System.out.print("Enter Transfer Amount: ");
                double amount = scanner.nextDouble();
                fromAccount.transfer(toAccount, amount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Recipient account not found.");
            }
        } else {
            System.out.println("Your account not found.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.viewTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void managePersonalInformation() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.print("Enter New Account Holder Name: ");
            String newName = scanner.nextLine();
            account.setAccountHolderName(newName);
            System.out.println("Account information updated successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }
}