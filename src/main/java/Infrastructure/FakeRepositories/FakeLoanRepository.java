package Infrastructure.FakeRepositories;

import Core.Entities.Account;
import Core.Entities.Client;
import Core.Entities.Loan;
import Core.Interfaces.IRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FakeLoanRepository implements IRepository<Loan> {

    List<Loan> loans;

    public FakeLoanRepository(){
        loans = new ArrayList<>();
        loans.add(new Loan(new Account(new Client("Paco",1), 4,25000),50000,new Date(2021,Calendar.DECEMBER,1),false));
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
    }

    @Override
    public List<Loan> GetAll() {
        return loans;
    }

    @Override
    public Loan Get(int id) {
        return null;
    }

    // This field is exposed so that a unit test can validate that the
    // Add method was invoked.
    public boolean addWasCalled = false;

    @Override
    public void Add(Loan loan) {
        addWasCalled = true;
    }

    // This field is exposed so that a unit test can validate that the
    // Edit method was invoked.
    public boolean editWasCalled = false;

    @Override
    public void Edit(Loan loan) {
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
