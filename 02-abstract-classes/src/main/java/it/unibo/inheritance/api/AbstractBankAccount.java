package it.unibo.inheritance.api;

public abstract class AbstractBankAccount implements BankAccount{

    private static final double ATM_TRANSACTION_FEE = 1;
    private static final double MANAGEMENT_FEE = 5;
    private static final double TRANSACTION_FEE = 0.1;

    private final AccountHolder holder;
    private double balance;
    private int transactions;

    private AbstractBankAccount(final AccountHolder accountHolder, final double balance) {
        this.holder = accountHolder;
        this.balance = balance;
        this.transactions = 0;
    }

    public void chargeManagementFees(final int id) {
        final double feeAmount = MANAGEMENT_FEE + this.getTransactionsCount() * TRANSACTION_FEE;
        if (this.checkUser(id) && this.isWithdrawAllowed(feeAmount)) {
            this.setBalance(this.getBalance() - feeAmount);
            this.resetTransactions();
        }
    }

    public void deposit(final int id, final double amount) {
        this.transactionOp(id, amount);
    }

    public void depositFromATM(final int id, final double amount) {
        this.deposit(id, amount - ATM_TRANSACTION_FEE);
    }

    public AccountHolder getAccountHolder() {
        return holder;
    }

    public double getBalance() {
        return this.balance;
    }

    protected void setBalance(final double balance) {
        this.balance = balance;
    }

    public int getTransactionsCount() {
        return this.transactions;
    }

    public void withdraw(final int id, final double amount) {
        if (this.isWithdrawAllowed(amount)) {
            this.transactionOp(id, -amount);
        }
    }

    public void withdrawFromATM(final int id, final double amount) {
        this.withdraw(id, amount + ATM_TRANSACTION_FEE);
    }

    abstract protected boolean isWithdrawAllowed(final double amount);

    abstract protected double computeFee();

    private boolean checkUser(final int id) {
        return this.getAccountHolder().getUserID() == id;
    }

    private void incrementTransactions() {
        this.transactions++;
    }

    private void resetTransactions() {
        this.transactions = 0;
    }

    private void transactionOp(final int id, final double amount) {
        if (this.checkUser(id)) {
            this.setBalance(this.getBalance() + amount);
            this.incrementTransactions();
        }
    }
}
