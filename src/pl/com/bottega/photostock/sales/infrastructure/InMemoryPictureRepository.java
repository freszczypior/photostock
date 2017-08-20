package pl.com.bottega.photostock.sales.infrastructure;

import com.sun.org.apache.regexp.internal.RE;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.PictureRepository;

import java.util.*;

public class InMemoryPictureRepository implements PictureRepository {

    //pola statyczne czy też bloki inicjalizowane są na początku po wywołaniu danej klasy,

    private static final Map<Long, Picture> REPO;       // może być wiele implementacjji/obiektów tej klasy, ale wszystkie będą korzystały z tego jednego REPO, jest one statyczne a więc wywoływane dla klasy a nie jakiś konkretnych implementacji

    //blok statyczny, wykona się po inicjalizacji statycznego pola klasy(powyżej), ten blok jest swoistym konstruktorem dla klas
    static {
        REPO = new HashMap<>();
        Set<String> tags = new HashSet<>();
        tags.add("Kotki");
        Picture picture1 = new Picture(1L, tags, Money.valueOf(10));
        Picture picture2 = new Picture(2L, tags, Money.valueOf(5));
        Picture picture3 = new Picture(3L, tags, Money.valueOf(15));
        REPO.put(1L, picture1);
        REPO.put(2L, picture2);
        REPO.put(3L, picture3);
    }

    @Override
    public Picture get(Long number) {       //zwykły get, możemy użyć jak jesteśmy pewnie, że użytkownik będzie znał prawidłowy numer, inaczej rzucimy wyjątkiem, czyli jeżeli nie mamy tej pewności to wręcz musimy użyć geta z optionalem
        if (!REPO.containsKey(number))
            throw new IllegalArgumentException("No such object in repository");
        return REPO.get(number);
    }

    @Override
    public Optional<Picture> getOptional(Long number) {     //get z Optionalem
        if (REPO.containsKey(number))
            return Optional.of(REPO.get(number));
        else
        return Optional.empty();
    }

    @Override
    public void save(Picture picture) {
        REPO.put(picture.getNumber(), picture);
    }
}
