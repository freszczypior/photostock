package pl.com.bottega.photostock.sales.model;


import java.util.LinkedHashSet;
import java.util.Set;

public class Reservation {

    private Client owner;
    private Set<Picture> items = new LinkedHashSet<>();

    public Reservation(Client owner) {
        this.owner = owner;
    }

    public void add(Picture picture) {
        if (picture.isAvailable()) {
            items.add(picture);
            picture.reservedPer(owner);
        } else
            throw new IllegalStateException("Product is not available");
    }

    public void remove(Picture picture) {
        if (items.remove(picture))
            picture.unreservedPer(owner);
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
