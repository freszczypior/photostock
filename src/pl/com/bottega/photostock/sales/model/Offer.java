package pl.com.bottega.photostock.sales.model;

import java.util.*;

public class Offer {

    private List<Product> items;
    private Client owner;

    public Offer(Client owner, Collection<Product> items) {
        this.owner = owner;
        this.items = new LinkedList<>(items);// set nie jest immutable, tworzę nową i przekazuję do niej tą z zewnątrz, którą dostaję, aby ktoś nie zmodyfikował mi mojej,
        this.items.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.calculatePrice(owner).compareTo(p1.calculatePrice(owner));
            }
        });
    }

    public boolean sameAs(Offer offer, Money money) {
        return false;
    }

    public int getItemCount() {
        return items.size();
    }

    public Money getTotalCost() {
        Money total = Money.ZERO;
        for (Product product : items) {
            total = total.add(product.calculatePrice(owner));
        }
        return total;
    }

    public Collection<Product> getItems() {
        return Collections.unmodifiableCollection(items);   //nasze itemsy zostały udekorowane niemodyfikowalną kolekcją, jak ktoś będzie chciał coś dodać to wurzuci wyjątek
    }
}
