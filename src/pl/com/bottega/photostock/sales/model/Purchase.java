package pl.com.bottega.photostock.sales.model;


import java.time.LocalDateTime;
import java.util.Collection;

public class Purchase {

    private LocalDateTime purchaseDate;
    private Client buyer;
    private Collection<Picture> items;


    public Purchase(Client buyer, Collection<Picture> items) {
        this.buyer = buyer;
        this.items = items;
        this.purchaseDate = LocalDateTime.now();
    }
}
