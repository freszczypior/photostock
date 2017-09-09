package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.repositories.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.repositories.ReservationRepository;

import java.util.List;

import static pl.com.bottega.photostock.sales.application.PurchaseStatus.*;

public class PurchaseProcess {

    private static final Money TOLERANCE = Money.valueOf(10);   // ta tolerancja tez mogłaby mieć swoje REPO aby móc wybierać, ale w naszej wersji takiego feature-a nie ma :)

    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public PurchaseProcess(ClientRepository clientRepository, ReservationRepository reservationRepository,
                           ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public String createReservation(String clientNumber) {
        Client client = clientRepository.get(clientNumber);
        Reservation reservation = new Reservation(client);
        reservationRepository.save(reservation);
        return reservation.getNumber();
    }

    public void add(String reservationNUmber, Long productNumber) {
        Reservation reservation = reservationRepository.get(reservationNUmber);
        Product product = productRepository.get(productNumber);
        reservation.add(product);
        reservationRepository.save(reservation);
        productRepository.save(product);
    }

    public Offer calculateOffer(String reservationNumber) {
        Reservation reservation = reservationRepository.get(reservationNumber);
        Offer offer = reservation.generateOffer();
        return offer;
    }

    public PurchaseStatus confirm(String reservationNumber, Offer clientOffer) {
        Offer currentOffer = calculateOffer(reservationNumber);
        if (currentOffer.sameAs(clientOffer, TOLERANCE)) {
            Client client = currentOffer.getOwner();
            Money offerCost = clientOffer.getTotalCost();
            if (client.canAfford(offerCost)) {
                Purchase purchase = clientOffer.purchase();
                purchaseRepository.save(purchase);
                List<Product> products = clientOffer.getItems();
                for (Product product : products)
                    productRepository.save(product);
                return SUCCESS;
            } else
                return PurchaseStatus.NOT_ENOUGHT_FOUNDS;
        } else
            return PurchaseStatus.OFFER_MISMATCH;

    }
}
