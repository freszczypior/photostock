package pl.com.bottega.photostock.sales.model;


import java.util.LinkedList;
import java.util.List;

public class Client {

    private String name;
    private Address address;
    protected ClientStatus status;
    //protected Money balance;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    protected List<Transaction> transactions = new LinkedList<>();    //lista z transakcjami, tego nie ma  w opisie

    public Client(String name, Address address, ClientStatus status) {
        this.name = name;
        this.address = address;
        this.status = status;
    }

//    public Client(String name, Address address, ClientStatus status, Money balance) {
//        this.name = name;
//        this.address = address;
//        this.status = status;
//        this.balance = balance;
//        if (balance().gt(Money.ZERO))
//            transactions.add(new Transaction(balance, "First charge"));
//    }

    public Client(String name, Address address) {
        this(name, address, ClientStatus.STANDARD);
    }

//    public Client(String name, Address address) {
//        this(name, address, ClientStatus.STANDARD, Money.ZERO);
//    }

    public boolean canAfford(Money amount) {
        return amount.lte(balance());
    }

    public void charge(Money amount, String reason) {
        if (!canAfford(amount))
            throw new IllegalStateException("Not enought balance");
        //balance = balance.subTract(amount);
        transactions.add(new Transaction(amount.neg(), reason));
    }

    public void reCharge(Money amount) {
        //balance = balance.add(amount);
        transactions.add(new Transaction(amount, "Recharge account"));
    }

    public ClientStatus getStatus() {
        return status;
    }

    public int discountPercent() {
        return status.discountPercent();
    }

    protected Money balance(){      // zrobiłem protected aby VIPClient mógł korzystać z tej metody
        Money balance = Money.ZERO;
        for (Transaction transaction: transactions) {
            balance.add(transaction.getAmount());
        }
        return balance;
    }
}
