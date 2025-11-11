package it.unibo.inheritance.impl;

public class ExtendedStrictBankAccount extends SimpleBankAccount{

    private static final double TRANSACTION_FEE = 0.1;

    public ExtendedStrictBankAccount(final int id, final double balance) {
        super(id, balance);
        super.resetTransactions();
    }

    private void transactionOp(final int id, final double amount) {
        if (super.checkUser(id)) {
            super.setBalance(super.getBalance() + amount);
            super.incrementTransactions();
        }
    }

    @Override
    public void withdraw(final int id, final double amount) {
        if (this.isWithdrawAllowed(amount)) {
            this.transactionOp(id, -amount);
        }
    }

    @Override
    public void chargeManagementFees(final int id) {
        final double feeAmount = MANAGEMENT_FEE + super.getTransactionsCount() * TRANSACTION_FEE;
        if (super.checkUser(id) && this.isWithdrawAllowed(feeAmount)) {
            super.setBalance(super.getBalance() - feeAmount);
            super.resetTransactions();
        }
    }

    private boolean isWithdrawAllowed(final double amount) {
        return super.getBalance() >= amount;
    }
}
