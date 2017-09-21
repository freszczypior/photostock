package pl.com.bottega.photostock.sales.model;

import java.util.Arrays;
import java.util.HashSet;

public class Clip extends AbstractProduct {

    private Long lenght;

    public Clip(Long number, Long lenght, Money price, Boolean active) {
        super(number, price, active);
        this.lenght = lenght;        // tworzymy nowy zbiór, bo przy zwykłym this.tags = tags; ktoś z zewnątrz mając dostęp do ref mógłby modyfikowac nam tags
    }

    public Clip(Long number, Long lenght, Money price) {    // jak mamy klika konstruktorów to mogę używaćdnego w drugim, po to aby nie duplikować konstruktorów
        this(number, lenght, price, true);
    }

    public Clip(Long number, Long lenght, Money price, Client reservedBy, Client owner, boolean active, Brand brand) { //na potrzeby CSV
        super(number, price, reservedBy, owner, active, brand);
        this.lenght = lenght;
    }

    public Long getLenght() {
        return lenght;
    }
}


