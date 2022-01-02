package Infrastructure.FakeRepositories;

import Core.Entities.Account;
import Core.Entities.Client;
import Core.Interfaces.IRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeAccountRepository implements IRepository<Account>{

    private List<Account> accounts;

    public FakeAccountRepository(){
        accounts = new ArrayList<>();
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
    }

    @Override
    public List<Account> GetAll() {
        return accounts;
    }

    @Override
    public Account Get(int id) {

        for ( Account account : accounts ) {
            if ( account.getId() == id ){
                return account;
            }
        }

        return null;

    }

    // This field is exposed so that a unit test can validate that the
    // Add method was invoked.
    public boolean addWasCalled = false;

    @Override
    public void Add(Account entity) {
        addWasCalled = true;
    }

    // This field is exposed so that a unit test can validate that the
    // Edit method was invoked.
    public boolean editWasCalled = false;

    @Override
    public void Edit(Account entity) {
        editWasCalled = true;
    }

    // This field is exposed so that a unit test can validate that the
    // Remove method was invoked.
    public boolean removeWasCalled = false;

    @Override
    public void Remove(int id) {
        removeWasCalled = true;
    }

}
