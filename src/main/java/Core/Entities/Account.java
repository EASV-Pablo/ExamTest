package Core.Entities;

public class Account {

    private Client client;
    private double balance;
    private int id;

    public Account(Client client, int id, double balance) {
        this.client = client;
        this.balance = balance;
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
