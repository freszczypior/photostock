package pl.com.bottega.photostock.sales.model;


import java.util.List;

public class VIPClient extends Client {

    private Money creditLimit;

    public VIPClient(String name, Address address, ClientStatus status, Money balance, Money creditLimit) {
        super(name, address, status, balance);
        this.creditLimit = creditLimit;
    }

    public VIPClient(String name, Address address) {
        this(name, address, ClientStatus.VIP, Money.ZERO, Money.ZERO);
    }

    public VIPClient(String number, String name, Address address, List<Transaction> transactions, Money creditLimit){
        super(number, name, address, ClientStatus.VIP, transactions);
        this.creditLimit = creditLimit;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    @Override
    public boolean canAfford(Money amount) {
        return amount.lte(balance().add(this.creditLimit));
    }

}
