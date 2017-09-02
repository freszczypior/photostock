package pl.com.bottega.photostock.sales.model;


public class VIPClient extends Client {

    private Money creditLimit;

    public VIPClient(String name, Address address, Money balance, Money creditLimit) {
        super(name, address, ClientStatus.VIP, balance);
        this.creditLimit = creditLimit;
    }
    public VIPClient(String name, Address address) {
        super(name, address);
    }


    @Override
    public boolean canAfford(Money amount) {
        return amount.lte(balance().add(creditLimit));
    }

}
