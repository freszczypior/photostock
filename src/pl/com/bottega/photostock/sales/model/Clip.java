package pl.com.bottega.photostock.sales.model;


import java.time.Duration;

public class Clip extends AbstractProduct{

    private Long number = super.number;
    private Duration duration;

    public Clip(Long number, Duration duration, Money price, Boolean active) {
        super(number, price, active);
        this.duration = duration;        // tworzymy nowy zbiór, bo przy zwykłym this.tags = tags; ktoś z zewnątrz mając dostęp do ref mógłby modyfikowac nam tags
    }

    public Clip(Long number, Duration duration, Money price) {    // jak mamy klika konstruktorów to mogę używaćdnego w drugim, po to aby nie duplikować konstruktorów
        this(number, duration, price, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clip)) return false;
        Clip clip = (Clip) o;
        return number.equals(clip.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
