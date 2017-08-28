package pl.com.bottega.photostock.sales.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CurrencyConventer {

    private String mainCurrency;
    private Map<String, Double> exchangeRates;      // mapa kursów walut
    private final Integer ONE_UNIT = 1;

    public CurrencyConventer(String mainCurrency, Map<String, Double> exchangeRates) {
        this.mainCurrency = mainCurrency;
        this.exchangeRates = exchangeRates;
    }

    public Money convert(Money amount) {   //konwertuje kwotę na walutę główną i zwraca ją w nowym obiekcie Money
        Double rates = exchangeRates.get(amount.getCurrency());
        return amount.convert("PLN", rates);
    }

    public Money convert(Money amount, String currancy) {   //konwertuję na dowolną walutę i zwraca ją w nowym obiekcie
        if (amount.getCurrency().equals(currancy))      //TODO a co jak obie nie pln i nie ma ich w exRates?
            return amount;
        if (amount.getCurrency().equals("PLN"))
            if (!exchangeRates.keySet().contains(currancy))
                throw new IllegalArgumentException("This currency is not supported");
            else
                return amount.convert(currancy, reverseExRates(exchangeRates.get(amount.getCurrency())));
        if (currancy.equals("PLN"))
            if (!exchangeRates.keySet().contains(amount.getCurrency()))
                throw new IllegalArgumentException("This currency is not supported");
            else
                return convert(amount);
        else {
            if (!exchangeRates.keySet().contains(currancy) || !exchangeRates.keySet().contains(currancy))
                throw new IllegalArgumentException("This currency is not supported");
            else
                return convert(amount).convert(currancy, exchangeRates.get(currancy));
        }
    }

    private Double reverseExRates(Double rate) {     // z kursu EUR>PLN robi PLN>EUR
        return BigDecimal.valueOf(ONE_UNIT).divide(BigDecimal.valueOf(rate), 2, RoundingMode.DOWN).doubleValue();
    }
}