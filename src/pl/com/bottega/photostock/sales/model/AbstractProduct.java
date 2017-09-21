package pl.com.bottega.photostock.sales.model;


public abstract class AbstractProduct implements Product {

    // dodać pola
    protected Long number;
    protected Money price;
    protected Boolean active;     //flaga mówiąca o tym czy zdjęcie jest dostępne do handlu
    private Brand brand;
    private Client reservedBy;
    private Client owner;      //wskazuję kto ewentualnie razererwował produkt, kto kupił
    //zaimplementować metody


    public AbstractProduct(Long number, Money price, Boolean active) {
        this.number = number;
        this.price = price;
        this.active = active;
    }
    //number, price, reservedBy, owner, active

    public AbstractProduct(Long number, Money price, Client reservedBy, Client owner, boolean active, Brand brand){ //nowy konst na potrzebę CSV
        this.number = number;
        this.price = price;
        this.reservedBy = reservedBy;
        this.owner = owner;
        this.active = active;
        this.brand = brand;
    }
    public AbstractProduct(Long number, Money price) {
        this(number, price, true);
    }

    @Override
    public Long getNumber() {
        return number;
    }

    @Override
    public Money calculatePrice(Client owner) {
        return price.percent(100 - owner.discountPercent());
    }

    @Override
    public boolean isAvailable() {
        return active && reservedBy == null;
    }
    
    @Override
    public void reservedPer(Client client) {
        ensureAvailable();
        reservedBy = client;
    }

    @Override
    public void unreservedPer(Client owner) {
        if (owner != null)
            throw new IllegalStateException("Product is already purchased");
        checkReservation(owner);
        reservedBy = null;
    }

    @Override
    public void soldPer(Client client) {
        checkReservation(client);
        owner = client;
    }

    private void checkReservation(Client client) {
        if (reservedBy == null || !reservedBy.equals(client))
            throw new IllegalStateException(String.format("Product is not reserved by %s", client));
    }
    @Override
    public int compareTo(Product other){
        return (int) (this.number - other.getNumber());
    }

    @Override
    public boolean equals(Object o) {       // normalny equals porównuję tylko referencje, ten zawartość
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return number.equals(product.getNumber());
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public Money getPrice() {
        return price;
    }

    @Override
    public boolean getActive() {
        return active;
    }

    @Override
    public Client getReservedBy() {
        return reservedBy;
    }

    @Override
    public Client getOwner() {
        return owner;
    }

    @Override
    public Brand getBrand() {
        return brand;
    }
}
