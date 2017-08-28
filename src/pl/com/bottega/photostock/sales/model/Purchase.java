package pl.com.bottega.photostock.sales.model;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Purchase {

    private LocalDateTime purchaseDate;
    private Client buyer;
    private List<Product> items;    // TODO zmiana z collections na list, aby móc posortować


    public Purchase(Client buyer, List<Product> items) {
        this.buyer = buyer;
        this.items = items;
        this.purchaseDate = LocalDateTime.now();
        this.items.sort(new Comparator<Product>() { // TODO dodałem sortowanie, items zmieniłem na List
            @Override
            public int compare(Product p1, Product p2) {
                return p1.compareTo(p2);
            }
        });
    }
}
