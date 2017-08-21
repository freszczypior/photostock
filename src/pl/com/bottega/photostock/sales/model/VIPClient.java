package pl.com.bottega.photostock.sales.model;


public class VIPClient extends Client{

    private Money creditLimit;

    public VIPClient(String name, Address address, Money balance, Money creditLimit) {
        super(name, address);
        this.creditLimit = creditLimit;
        super.status = ClientStatus.VIP;
        super.balance = balance;
        if (balance.gt(Money.ZERO))
            super.transactions.add(new Transaction(balance, "First charge"));
        if (creditLimit.gt(Money.ZERO))
            super.transactions.add(new Transaction(creditLimit, "Credit granted"));

    }
    @Override
    public boolean canAfford(Money amount) {
        return amount.lte(super.balance.add(creditLimit));
    }

    @Override
    public void charge(Money amount, String reason) {
        if (!canAfford(amount))
            throw new IllegalStateException("Not enought balance");
        balance = balance.subTract(amount);     //tutaj tylko balance, co może dać wynik ujemny, ale jest ok, bo nie zejdzie niżej niż pozwala canAfford, czyli bęzie taki minus jak debet na koncie :)
        transactions.add(new Transaction(amount.neg(), reason));
    }
    @Override
    public void reCharge(Money amount) {
        balance = balance.add(amount);
        transactions.add(new Transaction(amount, "Recharge account"));
    }
}
