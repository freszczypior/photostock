package pl.com.bottega.photostock.sales.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LightBox {

    private String name;
    private Client owner;
    private List<Picture> items = new LinkedList<>();       //lista, a nie kolekcja bo to sugeruję, że ważna jest kolejnośc dodawania, np dla przyszłego wyświetlania

    public LightBox(Client owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public void add(Picture picture){
        if (!picture.isAvailable())
            throw new IllegalStateException("Product is not availaable");
        if (items.contains(picture))
            throw new IllegalArgumentException("Product already added");
        else items.add(picture);
    }

    public void remove(Picture picture){
        if (!items.remove(picture))
            throw new IllegalArgumentException("Lightbox doesn't contains this product");
    }

    public Client getClient() {
        return owner;
    }
    public List<Picture> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getName() {
        return name;
    }
}
