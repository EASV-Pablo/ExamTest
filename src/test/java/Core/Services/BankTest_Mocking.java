package Core.Services;

import Core.Entities.Account;
import Core.Entities.Client;
import Core.Entities.Loan;
import Core.Interfaces.IRepository;
import Infrastructure.Repositories.AccountRepository;
import Infrastructure.Repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BankTest_Mocking {

    private Bank bank;
    private IRepository<Account> accountRepository;
    private IRepository<Loan> loanRepository;

    BankTest_Mocking(){

        accountRepository = mock(AccountRepository.class);
        loanRepository = mock(LoanRepository.class);

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(new Client("Paco",1), 4,25000));
        accounts.add(new Account(new Client("Paco",1), 39,35000));
        accounts.add(new Account(new Client("Perico",2), 34,-150));
        accounts.add(new Account(new Client("Pedro",3), 50,30000));
        accounts.add(new Account(new Client("Pedro",3), 15,90000));
        accounts.add(new Account(new Client("Pedro",3), 7,20000));
        accounts.add(new Account(new Client("Pepe",4), 17,7000));
        accounts.add(new Account(new Client("Pepe",4), 20,2500));
        accounts.add(new Account(new Client("Juan",5), 22,35000));
        accounts.add(new Account(new Client("Sonia",6), 60,22000));
        accounts.add(new Account(new Client("Sonia",6), 71,37000));
        accounts.add(new Account(new Client("Sonia",6), 81,70000));
        accounts.add(new Account(new Client("Ramon",7), 12,4000));
        accounts.add(new Account(new Client("Susana",8), 10,41000));
        accounts.add(new Account(new Client("Susana",8), 32,5000));
        accounts.add(new Account(new Client("Susana",8), 18,12000));

        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan(new Account(new Client("Paco",1), 4,25000),50000,new Date(2021, Calendar.DECEMBER,1),false));
        loans.add(new Loan(new Account(new Client("Paco",1), 39,35000),15000,new Date(2019, Calendar.MAY,7),false));
        loans.add(new Loan(new Account(new Client("Perico",2), 34,-150),10000,new Date(2021,Calendar.NOVEMBER,21),true));
        loans.add(new Loan(new Account(new Client("Pedro",3), 15,90000),120000,new Date(2020,Calendar.MARCH,14),false));
        loans.add(new Loan(new Account(new Client("Pedro",3), 7,20000),30000,new Date(2021,Calendar.OCTOBER,5),false));
        loans.add(new Loan(new Account(new Client("Pepe",4), 20,2500),13500,new Date(2022,Calendar.FEBRUARY,14),true));
        loans.add(new Loan(new Account(new Client("Juan",5), 22,35000),115000,new Date(2023,Calendar.AUGUST,25),true));
        loans.add(new Loan(new Account(new Client("Sonia",6), 81,70000),140000,new Date(2024,Calendar.SEPTEMBER,11),true));
        loans.add(new Loan(new Account(new Client("Ramon",7), 12,4000),10000,new Date(2021,Calendar.AUGUST,3),false));
        loans.add(new Loan(new Account(new Client("Susana",8), 10,41000),80000,new Date(2021,Calendar.SEPTEMBER,6),false));
        loans.add(new Loan(new Account(new Client("Susana",8), 18,12000),20000,new Date(2022,Calendar.MARCH,8),true));

        when(accountRepository.GetAll()).thenReturn(accounts);
        when(loanRepository.GetAll()).thenReturn(loans);

        when(accountRepository.Get(anyInt())).thenAnswer(new Answer<Account>() {
            @Override
            public Account answer(InvocationOnMock invocation) throws Throwable {
                for ( Account account : accounts ) {
                    if(account.getId() == (int) invocation.getArguments()[0])
                        return account;
                }
                return null;
            }
        });

        bank = new Bank(accountRepository, loanRepository);
    }

    @Test
    void offerInvestingWallet_RichClientWithoutPendingLoans_ReturnHighRiskWallet(){

        // Arrange
        Client client = new Client("Pedro", 3);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.HIGH_RISK_WALLET, result);
    }

    @Test
    void offerInvestingWallet_RichClientWithPendingLoans_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Sonia", 6);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void offerInvestingWallet_AverageClientWithoutPendingLoans_ReturnMediumRiskWallet(){

        // Arrange
        Client client = new Client("Paco", 1);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.MEDIUM_RISK_WALLET, result);
    }

    @Test
    void offerInvestingWallet_AverageClientWithPendingLoans_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Susana", 8);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void offerInvestingWallet_PoorClientWithoutPendingLoans_ReturnLowRiskWallet(){

        // Arrange
        Client client = new Client("Ramon", 7);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.LOW_RISK_WALLET, result);
    }

    @Test
    void offerInvestingWallet_PoorClientWithPendingLoans_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Juan", 5);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void offerInvestingWallet_BankruptClient_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Perico", 2);

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void transferMoney_NegativeAmount_ThrowsNegativeAmountException(){

        // Arrange
        Account source = accountRepository.Get(50);
        Account dest = accountRepository.Get(12);

        // Act & Assert
        assertThrows(NegativeAmountException.class, () -> bank.transferMoney(source, dest, -1000));
    }

    @Test
    void transferMoney_InsufficientMoney_ThrowsNoMoneyException(){

        // Arrange
        Account source = new Account(new Client("Ramon",7), 12, 4000);
        Account dest = accountRepository.Get(50);

        // Act & Assert
        assertThrows(NoMoneyException.class, () -> bank.transferMoney(source, dest, 50000));
    }

    @Test
    void transferMoney_SendMoreThan9999FromVIPAccount_AmountDeducted1Percent() throws Exception {

        // Arrange
        Account source = accountRepository.Get(50); // VIP
        Account dest = accountRepository.Get(34);
        double expected = 9900;

        // Act
        double delivered = bank.transferMoney(source, dest, 10000);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendMoreThan9999ToVIPAccount_AmountDeducted1Percent() throws Exception {

        // Arrange
        Account source = accountRepository.Get(81);
        Account dest = accountRepository.Get(50);  // VIP
        double expected = 9900;

        // Act
        double delivered = bank.transferMoney(source, dest, 10000);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendLessThan10000FromVIPAccount_AmountDeducted5Percent() throws Exception {

        // Arrange
        Account source = accountRepository.Get(50); // VIP
        Account dest = accountRepository.Get(34);
        double expected = 4750;

        // Act
        double delivered = bank.transferMoney(source, dest, 5000);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendLessThan10000ToVIPAccount_AmountDeducted5Percent() throws Exception {

        // Arrange
        Account source = accountRepository.Get(81);
        Account dest = accountRepository.Get(50);  // VIP
        double expected = 4750;

        // Act
        double delivered = bank.transferMoney(source, dest, 5000);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendMoneyNeitherVIPAccount_AmountDeducted5Percent() throws Exception {

        // Arrange
        Account source = accountRepository.Get(81);
        Account dest = accountRepository.Get(12);
        double expected = 4750;

        // Act
        double delivered = bank.transferMoney(source, dest, 5000);

        // Assert
        verify(accountRepository).Get(81);
        verify(accountRepository).Get(12);
        assertEquals(expected, delivered);
    }

}