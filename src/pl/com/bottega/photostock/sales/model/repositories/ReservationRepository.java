package pl.com.bottega.photostock.sales.model.repositories;

import pl.com.bottega.photostock.sales.model.Reservation;

public interface ReservationRepository {

    void save(Reservation reservation);

    Reservation get(String number);
}

