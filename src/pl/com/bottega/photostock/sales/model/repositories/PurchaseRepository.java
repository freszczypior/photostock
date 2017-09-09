package pl.com.bottega.photostock.sales.model.repositories;


import pl.com.bottega.photostock.sales.model.Purchase;

public interface PurchaseRepository {


    Purchase get(String number);

    void save(Purchase purchase);
}
