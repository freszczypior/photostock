package pl.com.bottega.photostock.sales.model;


import java.util.HashSet;
import java.util.Set;

public class Picture {

    private Long number;
    private Set<String> tags;
    private Money price;
    private Boolean active;     //flaga mówiąca o tym czy zdjęcie jest dostępne do handlu
    private Client reservedBy, owner;      //wskazuję kto ewentualnie razererwował produkt, kto kupił

    public Picture(Long number, Set<String> tags, Money price, Boolean active) {
        this.number = number;
        this.tags = new HashSet<>(tags);        // tworzymy nowy zbiór, bo przy zwykłym this.tags = tags; ktoś z zewnątrz mając dostęp do ref mógłby modyfikowac nam tags
        this.price = price;
        this.active = active;
    }

    public Picture(Long number, Set<String> tags, Money price) {    // jak mamy klika konstruktorów to mogę używaćdnego w drugim, po to aby nie duplikować konstruktorów
        this(number, tags, price, true);
    }

    @Override
    public boolean equals(Object o) {       // normalny equals porównuję tylko referencje
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return number.equals(picture.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    public Money calculatePrice(Client owner) {
        return price.percent(100 - owner.discountPercent());
    }

//    public Money calculatePrice(Client owner) {     //TODO moja wersja, tylko coś nie tak ze składnią
//        ClientStatus status = owner.getStatus();
//        if (!(status.equals(ClientStatus.STANDARD) && status.equals(ClientStatus.VIP))) {
//            return status.equals(ClientStatus.SILVER) ? price.percent(95) :
//                    status.equals(ClientStatus.GOLD) ? price.percent(90) : price.percent(85);)
//        }
//        return price;
//    }

    public boolean isAvailable() {
        return active && reservedBy == null;
    }

    public void reservedPer(Client client) {
        if (!isAvailable())
            throw new IllegalStateException("Product is not available");
        reservedBy = client;
    }

    public void unreservedPer(Client client) {
        if (owner != null)
            throw new IllegalStateException("Product is already purchased");
        checkReservation(client);
        reservedBy = null;
    }

    public void soldPer(Client client) {
        checkReservation(client);
        owner = client;
    }

    private void checkReservation(Client client) {
        if (reservedBy == null || !reservedBy.equals(client))
            throw new IllegalStateException(String.format("Product is not reserved by %s", client));
    }

    public Long getNumber() {
        return number;
    }
}
