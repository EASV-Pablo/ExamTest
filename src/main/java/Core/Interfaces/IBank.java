package Core.Interfaces;

import Core.Entities.Account;
import Core.Entities.Client;

public interface IBank {

    int offerInvestingWallet(Client client);

    double transferMoney(Account source, Account dest, double amount) throws Exception;

    boolean isVIPAccount(Account account);

}
