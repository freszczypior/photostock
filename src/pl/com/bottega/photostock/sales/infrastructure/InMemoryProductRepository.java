package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.ProductRepository;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    //pola statyczne czy też bloki inicjalizowane są na początku po wywołaniu danej klasy,

    private static final Map<Long, Product> REPO;       // może być wiele implementacjji/obiektów tej klasy, ale wszystkie będą korzystały z tego jednego REPO, jest one statyczne a więc wywoływane dla klasy a nie jakiś konkretnych implementacji

    //blok statyczny, wykona się po inicjalizacji statycznego pola klasy(powyżej), ten blok jest swoistym konstruktorem dla klas
    static {
        REPO = new HashMap<>();
        Set<String> tags = new HashSet<>();
        tags.add("Kotki");
        Product picture1 = new Picture(1L, tags, Money.valueOf(10));
        Product picture2 = new Picture(2L, tags, Money.valueOf(5));
        Product picture3 = new Picture(3L, tags, Money.valueOf(15));
        Product picture4 = new Picture(4L, tags, Money.valueOf(20));
        Product picture5 = new Picture(5L, tags, Money.valueOf(25));
        REPO.put(1L, picture1);
        REPO.put(2L, picture2);
        REPO.put(3L, picture3);
        REPO.put(4L, picture4);
        REPO.put(5L, picture5);


    }

    @Override
    public Product get(Long number) {       //zwykły get, możemy użyć jak jesteśmy pewnie, że użytkownik będzie znał prawidłowy numer, inaczej rzucimy wyjątkiem, czyli jeżeli nie mamy tej pewności to wręcz musimy użyć geta z optionalem
        if (!REPO.containsKey(number))
            throw new IllegalArgumentException("No such object in repository");
        return REPO.get(number);
    }

    @Override
    public Optional<Product> getOptional(Long number) {     //get z Optionalem
        if (REPO.containsKey(number))
            return Optional.of(REPO.get(number));
        else
        return Optional.empty();
    }

    @Override
    public void save(Product product) {
        REPO.put(product.getNumber(), product);
    }
}
