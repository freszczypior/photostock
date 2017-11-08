package pl.com.bottega.photostock.sales.model;

import java.util.*;

public class Offer {

    private List<Product> items;


    private Client owner;

    public Offer(Client owner, Collection<Product> items) {
        this.owner = owner;
        this.items = new LinkedList<>(items);// set nie jest immutable, tworzę nową i przekazuję do niej tą z zewnątrz, którą dostaję, aby ktoś nie zmodyfikował mi mojej,
        this.items.sort((p1, p2) -> p2.calculatePrice(owner).compareTo(p1.calculatePrice(owner)));
//        this.items.sort(
//                new Comparator<Product>() {
//            @Override
//            public int compare(Product p1, Product p2) {
//                return p2.calculatePrice(owner).compareTo(p1.calculatePrice(owner));
//            }
//        });
    }

    public boolean sameAs(Offer offer, Money money) {
        return getTotalCost().subTract(offer.getTotalCost()).abs().lte(money);
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

    public List<Product> getItems() {
        return Collections.unmodifiableList(items);   //nasze itemsy zostały udekorowane niemodyfikowalną kolekcją, jak ktoś będzie chciał coś dodać to wurzuci wyjątek
    }


    public Purchase purchase() {
        Money cost = getTotalCost();
        Purchase purchase = new Purchase(owner, items);
        owner.charge(cost, String.format("Purchase number %s", purchase.getNumber()));
        return purchase;
    }
    public Client getOwner() {
        return owner;
    }

}
