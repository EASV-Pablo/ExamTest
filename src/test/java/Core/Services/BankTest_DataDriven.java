package Core.Services;

import Core.Entities.Account;
import Core.Entities.Client;
import Infrastructure.FakeRepositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BankTest_DataDriven {

    @ParameterizedTest
    @MethodSource
    void offerInvestingWallet(String name, int id, int resExpected){

        // Arrange
        Client client = new Client(name, id);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(resExpected, result);
    }

    private static Stream<Arguments> offerInvestingWallet(){
        return Stream.of(
                Arguments.of("Sonia", 6, 0),
                Arguments.of("Pedro", 3, 3),
                Arguments.of("Paco", 1, 2),
                Arguments.of("Susana", 8, 0),
                Arguments.of("Ramon", 7, 1),
                Arguments.of("Juan", 5, 0),
                Arguments.of("Perico", 2, 0)
        );
    }

    @ParameterizedTest
    @MethodSource
    void offerInvestingWallet_RichClientWithoutPendingLoans_ReturnHighRiskWallet(String name, int id){

        // Arrange
        Client client = new Client(name, id);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.HIGH_RISK_WALLET, result);
    }

    private static Stream<Arguments> offerInvestingWallet_RichClientWithoutPendingLoans_ReturnHighRiskWallet(){
        return Stream.of(
                Arguments.of("Pedro", 3),
                Arguments.of("Antonio", 3)
        );
    }

    @ParameterizedTest
    @MethodSource
    void offerInvestingWallet_RichClientWithPendingLoans_ReturnSavingsWallet(String name, int id){

        // Arrange
        Client client = new Client(name, id);
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());

        // Act
        int result = bank.offerInvestingWallet(client);

        // Assert
        assertEquals(Bank.SAVINGS_WALLET, result);
    }

    private static Stream<Arguments> offerInvestingWallet_RichClientWithPendingLoans_ReturnSavingsWallet(){
        return Stream.of(
                Arguments.of("Sonia", 6)
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {-150, -1000, -369, -562, -874})

    void transferMoney_NegativeAmount_ThrowsNegativeAmountException(double amount){

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(50);
        Account dest = new FakeAccountRepository().Get(12);

        // Act & Assert
        assertThrows(NegativeAmountException.class, () -> bank.transferMoney(source, dest, amount));
    }

    @ParameterizedTest
    @ValueSource(doubles = {151, 1000, 7000, 5000, 874})
    void transferMoney_InsufficientMoney_ThrowsNoMoneyException(double amount){

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(34);
        Account dest = new FakeAccountRepository().Get(50);

        // Act & Assert
        assertThrows(NoMoneyException.class, () -> bank.transferMoney(source, dest, amount));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void transferMoney_SendMoreThan9999FromVIPAccount_AmountDeducted1Percent(double amount,
                                                                             double expected)
                                                                             throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(50); // VIP
        Account dest = new FakeAccountRepository().Get(34);

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }
    @ParameterizedTest
    @MethodSource
    void transferMoney_SendMoreThan9999ToVIPAccount_AmountDeducted1Percent(double amount, double expected) throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(81);
        Account dest = new FakeAccountRepository().Get(50);  // VIP

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    private static Stream<Arguments> transferMoney_SendMoreThan9999ToVIPAccount_AmountDeducted1Percent(){
        return Stream.of(
                Arguments.of(10000, 9900),
                Arguments.of(15000, 14850),
                Arguments.of(70000, 69300)
        );
    }

    @ParameterizedTest
    @MethodSource("transferMoney_Less10000VIP_Deb5Percent")
    void transferMoney_SendLessThan10000FromVIPAccount_AmountDeducted5Percent(double amount,
                                                                              double expected)
                                                                              throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(50); // VIP
        Account dest = new FakeAccountRepository().Get(34);

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    private static Stream<Arguments> transferMoney_Less10000VIP_Deb5Percent(){
        return Stream.of(
                Arguments.of(1000, 950),
                Arguments.of(8570, 8141.5),
                Arguments.of(7000, 6650)
        );
    }

    @ParameterizedTest
    @CsvSource({"1000, 950",
                "8570, 8141.5",
                "7000, 6650",
                "2500, 2375"})
    void transferMoney_SendLessThan10000ToVIPAccount_AmountDeducted5Percent(double amount,
                                                                            double expected)
                                                                            throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(81);
        Account dest = new FakeAccountRepository().Get(50);  // VIP

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

    @ParameterizedTest
    @ArgumentsSource(AmountProvider.class)
    void transferMoney_SendMoneyNeitherVIPAccount_AmountDeducted5Percent(double amount,
                                                                         double expected)
                                                                         throws Exception {

        // Arrange
        Bank bank = new Bank(new FakeAccountRepository(), new FakeLoanRepository());
        Account source = new FakeAccountRepository().Get(81);
        Account dest = new FakeAccountRepository().Get(12);

        // Act
        double delivered = bank.transferMoney(source, dest, amount);

        // Assert
        assertEquals(expected, delivered);
    }

}

