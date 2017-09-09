package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.repositories.PurchaseRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPurchaseRepository implements PurchaseRepository {

private static final Map<String, Purchase> REPO = new HashMap<>();



    @Override
    public Purchase get(String number) {
        if (!REPO.containsKey(number))
            throw new IllegalArgumentException(String.format("No such Purchase %s in repository", number));
        return REPO.get(number);
    }
    @Override
    public void save(Purchase purchase) {
        REPO.put(purchase.getNumber(), purchase);
    }

}
