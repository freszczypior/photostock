package pl.com.bottega.photostock.sales.model;


public class VIPClient extends Client{

    private Money creditLimit;

    public VIPClient(String name, Address address, Money creditLimit) {
        super(name, address, ClientStatus.VIP);
        this.creditLimit = creditLimit;
    }
    @Override
    public boolean canAfford(Money amount) {
        return amount.lte(balance().add(creditLimit));
    }

    @Override
    public void charge(Money amount, String reason) {
        if (!canAfford(amount))
            throw new IllegalStateException("Not enought balance");
        transactions.add(new Transaction(amount.neg(), reason));
    }
    @Override
    public void reCharge(Money amount) {
        transactions.add(new Transaction(amount, "Recharge account"));
    }
}
