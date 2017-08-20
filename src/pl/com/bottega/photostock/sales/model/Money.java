package pl.com.bottega.photostock.sales.model;


public class Money implements Comparable<Money> {

    public static final String DEFAULT_CURRENCY = "CREDIT";
    public static final Money ZERO = new Money();

    private Long cents;     // jako ilość np: centów, groszy lub naszych kredytów
    private String currency;

    private Money() {   // prywatny konstruktor, będzie mógł być wywołany tylko w tej klasie
        this.cents = 0L;        // 0L to literał
        this.currency = DEFAULT_CURRENCY;
    }

    private Money(Long cents, String currency) {    //noowy konstruktor na potrzeby metody add
        this.cents = cents;
        this.currency = currency;
    }

    public static Money valueOf(Integer value) {
        return valueOf(value, DEFAULT_CURRENCY);
    }

    public static Money valueOf(Integer value, String currency) {
        return new Money(value * 100L, currency);
    }

    public static Money valueOf(Double value) {     // valueOf utworzone na potrzeby obługi doubla, koniecznośc wynikająca z zastosowania rabatów dla pewnyc h statusów klienta
        return new Money((long) (value * 100.0), DEFAULT_CURRENCY);
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(cents + other.cents, currency);
    }

    public String toString() {
        return String.format("%d.%d %s", cents / 100, cents % 100, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        if (!cents.equals(money.cents)) return false;
        return currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        int result = cents.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    public Money subTract(Money other) {
        checkCurrency(other);
        return add(other.neg());
    }

    public Money neg() {
        return new Money(-cents, currency);
    }

    private void checkCurrency(Money other) {
        if (!currency.equals(other.currency))
            throw new IllegalArgumentException("Incompatible currency");
    }

    @Override
    public int compareTo(Money other) {
        checkCurrency(other);
        return (int) (cents - other.cents);
    }

    public boolean lt(Money other) {
        return compareTo(other) < 0;
    }

    public boolean lte(Money other) {
        return compareTo(other) <= 0;
    }

    public boolean gt(Money other) {
        return compareTo(other) > 0;
    }

    public boolean gte(Money other) {
        return compareTo(other) >= 0;
    }

    public Money percent(int percent) {
        return new Money(cents * percent / 100, currency);
    }
}
