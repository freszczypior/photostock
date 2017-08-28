package pl.com.bottega.photostock.sales.model;

import java.util.HashSet;
import java.util.Set;

public class Picture extends AbstractProduct{

    private Long number = super.number;
    private Set<String> tags;

    public Picture(Long number, Set<String> tags, Money price, Boolean active) {
        super(number, price, active);
        this.tags = new HashSet<>(tags);        // tworzymy nowy zbiór, bo przy zwykłym this.tags = tags; ktoś z zewnątrz mając dostęp do ref mógłby modyfikowac nam tags
    }

    public Picture(Long number, Set<String> tags, Money price) {    // jak mamy klika konstruktorów to mogę używaćdnego w drugim, po to aby nie duplikować konstruktorów
        this(number, tags, price, true);
    }


    @Override
    public boolean equals(Object o) {       // normalny equals porównuję tylko referencje, ten zawartość
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return super.number.equals(picture.number);
    }

    @Override
    public int hashCode() {
        return super.number.hashCode();
    }

}
