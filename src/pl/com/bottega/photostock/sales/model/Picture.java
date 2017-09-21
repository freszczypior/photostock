package pl.com.bottega.photostock.sales.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Picture extends AbstractProduct{


    private Set<String> tags;

    public Picture(Long number, Set<String> tags, Money price, Boolean active) {
        super(number, price, active);
        this.tags = new HashSet<>(tags);        // tworzymy nowy zbiór, bo przy zwykłym this.tags = tags; ktoś z zewnątrz mając dostęp do ref mógłby modyfikowac nam tags
    }

    public Picture(Long number, Set<String> tags, Money price) {    // jak mamy klika konstruktorów to mogę używaćdnego w drugim, po to aby nie duplikować konstruktorów
        this(number, tags, price, true);
    }

    public Picture(Long number, String[] tags, Money price, Client reservedBy, Client owner, boolean active, Brand brand) { //na potrzeby CSV
        super(number, price, reservedBy, owner, active, brand);
        this.tags = new HashSet<>(Arrays.asList(tags));
    }

    public Set<String> getTags() {
        return tags;
    }

    public boolean hasTags(Set<String> tags){

        return this.tags.containsAll(tags);
    }
}
