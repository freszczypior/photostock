package pl.com.bottega.photostock.sales.model;

public interface Product {

    Long getNumber();

    Money calculatePrice(Client owner);

    boolean isAvailable();

    void reservedPer(Client client);

    void unreservedPer(Client owner);

    void soldPer(Client client);

    int compareTo(Product other);
}
