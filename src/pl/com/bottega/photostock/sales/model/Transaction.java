package pl.com.bottega.photostock.sales.model;

import java.time.LocalDateTime;

public class Transaction {  // dodatkowa klasa, jej chyba nie ma w opisie

    private Money amount;
    private String description;
    private LocalDateTime dateTime = LocalDateTime.now();

    public Transaction(Money amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public Transaction(Money amount, String description, LocalDateTime dateTime){   // konst na potrzeby csv
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
