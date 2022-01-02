package Infrastructure.Repositories;

import Core.Entities.Loan;
import Core.Interfaces.IRepository;

import java.util.List;

public class LoanRepository implements IRepository<Loan> {

    @Override
    public List<Loan> GetAll() {
        return null;
    }

    @Override
    public Loan Get(int id) {
        return null;
    }

    @Override
    public void Add(Loan entity) {

    }

    @Override
    public void Edit(Loan entity) {

    }

    @Override
    public void Remove(int id) {

    }

}
