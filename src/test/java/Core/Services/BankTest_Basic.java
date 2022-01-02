package Core.Services;

import Core.Entities.Account;
import Core.Entities.Client;
import Infrastructure.FakeRepositories.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest_Basic {

    @Test
    void offerInvestingWallet_RichClientWithoutPendingLoans_ReturnHighRiskWallet(){

        // Arrange
        Client client = new Client("Pedro", 3);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.HIGH_RISK_WALLET, result);
    }

    @Test
    void offerInvestingWallet_RichClientWithPendingLoans_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Sonia", 6);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void offerInvestingWallet_AverageClientWithoutPendingLoans_ReturnMediumRiskWallet(){

        // Arrange
        Client client = new Client("Paco", 1);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.MEDIUM_RISK_WALLET, result);
    }

    @Test
    void offerInvestingWallet_AverageClientWithPendingLoans_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Susana", 8);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void offerInvestingWallet_PoorClientWithoutPendingLoans_ReturnLowRiskWallet(){

        // Arrange
        Client client = new Client("Ramon", 7);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.LOW_RISK_WALLET, result);
    }

    @Test
    void offerInvestingWallet_PoorClientWithPendingLoans_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Juan", 5);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void offerInvestingWallet_BankruptClient_ReturnSavingsWallet(){

        // Arrange
        Client client = new Client("Perico", 2);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    @Test
    void transferMoney_NegativeAmount_ThrowsNegativeAmountException(){

        // Arrange

        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(50);
        Account dest = new FakeAccountRepository().Get(12);
        double amount = -1000;

        // Act & Assert
        assertThrows(NegativeAmountException.class, () -> bank.transferMoney(source, dest, amount));
    }

    @Test
    void transferMoney_InsufficientMoney_ThrowsNoMoneyException(){

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new Account(new Client("Ramon",7), 12, 4000);
        Account dest = new FakeAccountRepository().Get(50);
        double amount = 50000;

        // Act & Assert
        assertThrows(NoMoneyException.class, () -> bank.transferMoney(source, dest, amount));
    }

    @Test
    void transferMoney_SendMoreThan9999FromVIPAccount_AmountDeducted1Percent() throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(50); // VIP
        Account dest = new FakeAccountRepository().Get(34);
        double expected = 9900;
        double amount = 10000;

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendMoreThan9999ToVIPAccount_AmountDeducted1Percent() throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(81);
        Account dest = new FakeAccountRepository().Get(50);  // VIP
        double expected = 9900;
        double amount = 10000;

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendLessThan10000FromVIPAccount_AmountDeducted5Percent() throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(50); // VIP
        Account dest = new FakeAccountRepository().Get(34);
        double expected = 4750;
        double amount = 5000;

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendLessThan10000ToVIPAccount_AmountDeducted5Percent() throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(81);
        Account dest = new FakeAccountRepository().Get(50);  // VIP
        double expected = 4750;
        double amount = 5000;

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    @Test
    void transferMoney_SendMoneyNeitherVIPAccount_AmountDeducted5Percent() throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(81);
        Account dest = new FakeAccountRepository().Get(12);
        double expected = 4750;
        double amount = 5000;

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

}