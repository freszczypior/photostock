package pl.com.bottega.photostock.sales.model;

import java.util.*;

public class LightBox {     // przechowuję tylko Picture

    private String name;
    private Client owner;
    private List<Picture> items = new LinkedList<>();       //lista, a nie kolekcja bo to sugeruję, że ważna jest kolejnośc dodawania, np dla przyszłego wyświetlania
    private String number;

    public LightBox(Client owner, String name) {
        this.owner = owner;
        this.name = name;
        this.number = UUID.randomUUID().toString();
    }

    public LightBox(String number, String name, List<Picture> items, Client owner) {
        this.number = number;
        this.name = name;
        this.items = items;
    }


    public void add(Picture picture){
        picture.ensureAvailable();
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

    public String getNumber() {
        return number;
    }

    public List<Picture> getPictures(Set<Long> pictureNumbers){
        List<Picture> result = new LinkedList<>();
        for (Picture picture: items)
            if (pictureNumbers.contains(picture.getNumber()))
                result.add(picture);
                return result;
    }
}
