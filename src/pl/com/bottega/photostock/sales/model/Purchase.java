package pl.com.bottega.photostock.sales.model;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Purchase {

    private LocalDateTime purchaseDate;
    private Client buyer;
    private List<Product> items;    // TODO zmiana z collections na list, aby móc posortować
    private String number;

    public Purchase(Client buyer, List<Product> items) {
        this.buyer = buyer;
        this.items = items;
        this.purchaseDate = LocalDateTime.now();
        this.items.sort((p1, p2) -> p1.compareTo(p2));
//        this.items.sort(new Comparator<Product>() { // TODO dodałem sortowanie, items zmieniłem na List
//            @Override
//            public int compare(Product p1, Product p2) {
//                return p1.compareTo(p2);
//            }
//        });
        for (Product product: items) {
            product.soldPer(buyer);
        }
        this.number = UUID.randomUUID().toString();
    }

    public Purchase(String number, Client buyer, List<Product> items, LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
        this.buyer = buyer;
        this.items = items;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public Client getBuyer() {
        return buyer;
    }

    public List<Product> getItems() {
        return items;
    }
}
