package Infrastructure.Repositories;

import Core.Entities.Account;
import Core.Interfaces.IRepository;

import java.util.List;

public class AccountRepository implements IRepository<Account> {

    @Override
    public List<Account> GetAll() {
        return null;
    }

    @Override
    public Account Get(int id) {
        return null;
    }

    @Override
    public void Add(Account entity) {

    }

    @Override
    public void Edit(Account entity) {

    }

    @Override
    public void Remove(int id) {

    }

}
