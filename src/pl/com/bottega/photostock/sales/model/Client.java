package pl.com.bottega.photostock.sales.model;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public abstract class Client {

    private String number;
    private String name;
    private Address address;
    protected ClientStatus status;
    protected List<Transaction> transactions = new LinkedList<>();    //lista z transakcjami, tego nie ma  w opisie

    public Client(String name, Address address, ClientStatus status, Money balance) {
        this.name = name;
        this.address = address;
        this.status = status;
        if (balance.gt(Money.ZERO))
            transactions.add(new Transaction(balance, "First charge"));
        this.number = UUID.randomUUID().toString();
    }
    public Client(String name, Address address) {
        this(name, address, ClientStatus.STANDARD, Money.ZERO);
    }

    public Client(String number, String name, Address address, ClientStatus status, List<Transaction> transactions){
        this.number = number;
        this.name = name;
        this.address = address;
        this.status = status;
        this.transactions = transactions;
    }

    public Client(String number, String name, Address address, ClientStatus status, List<Transaction> transactions, Money creditLimit){
        this(number, name, address, status, transactions);
    }

    public abstract boolean canAfford(Money amount);


    public void charge(Money amount, String reason) {
        if (!canAfford(amount))
            throw new IllegalStateException("Not enought balance");
        transactions.add(new Transaction(amount.neg(), reason));
    }

    public void reCharge(Money amount) {
        transactions.add(new Transaction(amount, "Recharge account"));
    }

    public ClientStatus getStatus() {
        return status;
    }

    public int discountPercent() {
        return status.discountPercent();
    }

    protected Money balance(){      // zrobiłem protected aby VIPClient mógł korzystać z tej metody
        Money sum = Money.ZERO;
        for (Transaction transaction: transactions)
            sum = sum.add(transaction.getAmount());
        return sum;
    }
    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public boolean hasLogin(String login) {
        return name.equals(login);
    }

    public Address getAddress() {
        return address;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
