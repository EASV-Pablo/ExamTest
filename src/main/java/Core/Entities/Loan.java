package Core.Entities;

import java.util.Date;

public class Loan {

    private Account account;
    private double amount;
    private Date expirityDate;
    private boolean latePayment;

    public Loan(Account account, double amount, Date expirityDate, boolean latePayment) {
        this.account = account;
        this.amount = amount;
        this.expirityDate = expirityDate;
        this.latePayment = latePayment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getExpirityDate() {
        return expirityDate;
    }

    public void setExpirityDate(Date expirityDate) {
        this.expirityDate = expirityDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isLatePayment() {
        return latePayment;
    }

    public void setLatePayment(boolean latePayment) {
        this.latePayment = latePayment;
    }

}
