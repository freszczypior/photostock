package pl.com.bottega.photostock.sales.model;


import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class Reservation {

    private Client owner;
    private Set<Product> items = new LinkedHashSet<>();
    private String number;

    public Reservation(Client owner) {
        this.owner = owner;
        this.number = UUID.randomUUID().toString();     // generowanie losowego ciągu cyfr i liter, chyba 16 znaków
    }

    public Reservation(String number, Client owner, Set<Product> items) {
        this.owner = owner;
        this.items = items;
        this.number = number;
    }

    public void add(Product product) {
        product.ensureAvailable();
        items.add(product);
        product.reservedPer(owner);
    }

    public void remove(Product product) {
        if (items.remove(product))
            product.unreservedPer(owner);
        else
            throw new IllegalArgumentException("Product is not part of this reservation");
    }
    public Client getClient() {
        return owner;
    }

    public Offer generateOffer() {
        return new Offer(owner, items);
    }

    public int getItemCount() {
        return items.size();
    }

    public String getNumber() {
        return number;
    }

    public Set<Product> getItems() {
        return items;
    }
}
