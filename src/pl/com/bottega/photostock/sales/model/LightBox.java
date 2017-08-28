package pl.com.bottega.photostock.sales.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LightBox {

    private String name;
    private Client owner;
    private List<Product> items = new LinkedList<>();       //lista, a nie kolekcja bo to sugeruję, że ważna jest kolejnośc dodawania, np dla przyszłego wyświetlania

    public LightBox(Client owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public void add(Product product){
        if (!product.isAvailable())
            throw new IllegalStateException("Product is not availaable");
        if (items.contains(product))
            throw new IllegalArgumentException("Product already added");
        else items.add(product);
    }

    public void remove(Product product){
        if (!items.remove(product))
            throw new IllegalArgumentException("Lightbox doesn't contains this product");
    }

    public Client getClient() {
        return owner;
    }
    public List<Product> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getName() {
        return name;
    }
}
