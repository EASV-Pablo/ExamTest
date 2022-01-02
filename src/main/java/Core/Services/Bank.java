package Core.Services;

import Core.Entities.Account;
import Core.Entities.Client;
import Core.Entities.Loan;
import Core.Interfaces.IBank;
import Core.Interfaces.IRepository;

import java.util.Calendar;
import java.util.Date;

public class Bank implements IBank {

    private IRepository<Account> accountRepository;
    private IRepository<Loan> loanRepository;
    public static final int HIGH_RISK_WALLET = 3;
    public static final int MEDIUM_RISK_WALLET = 2;
    public static final int LOW_RISK_WALLET = 1;
    public static final int SAVINGS_WALLET = 0;

    public Bank(IRepository<Account> accountRepository, IRepository<Loan> loanRepository){
        this.accountRepository = accountRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public int offerInvestingWallet(Client client){

        double totalAmount = 0;

        for ( Account account : accountRepository.GetAll() ) {

            if ( account.getClient().getId() == client.getId() ){
                totalAmount += account.getBalance();
            }
        }

        int numberLoan = 0;
        int numberPaidLoans = 0;

        for ( Loan loan : loanRepository.GetAll() ) {

            if ( loan.getAccount().getClient().getId() == client.getId() ){
                numberLoan++;

                if ( loan.getExpirityDate().before(new Date(2022, Calendar.JANUARY, 13)) && !loan.isLatePayment() ){
                    numberPaidLoans++;
                }
            }
        }

        if ( numberLoan == numberPaidLoans && totalAmount >= 0 ){

            if ( totalAmount > 100_000 ){
                return HIGH_RISK_WALLET;
            } else if ( totalAmount > 50_000 ){
                return MEDIUM_RISK_WALLET;
            } else {
                return LOW_RISK_WALLET;
            }

        } else {
            return SAVINGS_WALLET;
        }

    }

    @Override
    public double transferMoney(Account source, Account dest, double amount) throws Exception {

        if ( amount <= 0 ){
            throw new NegativeAmountException("The amount should be positive");
        }

        if ( source.getBalance() < amount ){
            throw new NoMoneyException("The source account doesn't have enough money");

        } else {
            source.setBalance(source.getBalance() - amount);
        }

        double moneyDelivered;

        if ( ( isVIPAccount(source) || isVIPAccount(dest) ) && amount >= 10_000 ){
            moneyDelivered = amount * 0.99;

        } else {
            moneyDelivered = amount * 0.95;
        }

        dest.setBalance(dest.getBalance() + amount);

        return moneyDelivered;
    }

    @Override
    public boolean isVIPAccount(Account account){

        if ( account.getId() % 10 == 0 ){
            return true;
        } else {
            return false;
        }
    }


}
