package pl.com.bottega.photostock.sales.model;


import java.util.Map;

public class CurrencyConverter {

    private String mainCurrency;
    private Map<String, Double> exchangeRates;      // mapa kursów walut
    private final Integer ONE_UNIT = 1;

    public CurrencyConverter(String mainCurrency, Map<String, Double> exchangeRates) {
        this.mainCurrency = mainCurrency;
        this.exchangeRates = exchangeRates;
    }

    public Money convert(Money amount) {   //konwertuje kwotę na walutę główną i zwraca ją w nowym obiekcie Money
        if (amount.currency().equals(mainCurrency))
            return amount;
        return  amount.convert(mainCurrency, exRate(amount.currency()));
    }

    public Money convert(Money amount, String targetCurrency) {   //konwertuję na dowolną walutę i zwraca ją w nowym obiekcie
        if (targetCurrency.equals(mainCurrency))
                return convert(amount);
        if (amount.currency().equals(mainCurrency))
            return amount.convert(targetCurrency, 1/exRate(targetCurrency));
        return convert(convert(amount), targetCurrency);
    }

    private Double exRate(String currancy){     //zwraca kurs, jak nie ma to rzuca exception
        if (!exchangeRates.containsKey(currancy))
            throw new IllegalArgumentException("No ex rate for " + currancy);
        return exchangeRates.get(currancy);
    }
}