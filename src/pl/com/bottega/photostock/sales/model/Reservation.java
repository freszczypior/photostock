package pl.com.bottega.photostock.sales.model;


import java.util.LinkedHashSet;
import java.util.Set;

public class Reservation {

    private Client owner;
    private Set<Product> items = new LinkedHashSet<>();

    public Reservation(Client owner) {
        this.owner = owner;
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

    public Offer generateOffer() {
        return new Offer(owner, items);
    }

    public int getItemCount() {
        return items.size();
    }
}
