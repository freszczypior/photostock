package pl.com.bottega.photostock.sales.model;

import com.sun.org.apache.xpath.internal.operations.String;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

public interface Product {

    Long getNumber();

    Money calculatePrice(Client owner);

    boolean isAvailable();

    void reservedPer(Client client);

    void unreservedPer(Client owner);

    void soldPer(Client client);

    int compareTo(Product other);

    default void ensureAvailable(){      // metoda defoultowa, implementacja metody w interface
        if (!isAvailable())
            throw  new ProductNotAvailableException(this);
    }

    Money getPrice();

    boolean getActive();

    Client getReservedBy();

    Client getOwner();

    Brand getBrand();
}
