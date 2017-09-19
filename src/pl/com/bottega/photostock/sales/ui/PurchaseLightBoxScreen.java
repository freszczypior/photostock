package pl.com.bottega.photostock.sales.ui;


import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.application.PurchaseStatus;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Offer;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PurchaseLightBoxScreen {

    private LightBoxManagement lightBoxManagement;
    private PurchaseProcess purchaseProcess;
    private Scanner scanner;

    public PurchaseLightBoxScreen(LightBoxManagement lightBoxManagement, PurchaseProcess purchaseProcess, Scanner scanner) {
        this.lightBoxManagement = lightBoxManagement;
        this.purchaseProcess = purchaseProcess;
        this.scanner = scanner;
    }

    public void show(LightBox lightBox) {
//        Set<Long> numbers = new HashSet<>();
//        for (Product product : lightBox.getItems()) {
//            numbers.add(product.getNumber());
//        }

        // to samo co u góry ale na zasadzie mapowania strumieni z użyciem lambdy, collect "zamyka" strumień wychodzący w kolekcje
        Set<Long> numbers = lightBox.getItems().stream().map(
                product -> product.getNumber()).collect(Collectors.toSet());

        String reservationNunber = purchaseProcess.createReservation(lightBox.getClient().getNumber());
        Offer offer = purchaseProcess.calculateOffer(reservationNunber);
        lightBoxManagement.reserve(lightBox.getNumber(), numbers, reservationNunber);
        System.out.println(String.format("Cena wybranych zdjec to %s", offer.getTotalCost()));
        System.out.println("Czy chcesz dokonać zakupu (t/n)?");
        String decission = scanner.nextLine();
        if (decission.equals("t")) {
            PurchaseStatus purchaseStatus = purchaseProcess.confirm(reservationNunber, offer);
            if (purchaseStatus.equals(PurchaseStatus.SUCCESS))
                System.out.println("Dzięki za zakupy!!");
            else if (purchaseStatus.equals(PurchaseStatus.NOT_ENOUGHT_FOUNDS))
                System.out.println("Sorry, ale nie masz tyle kasy!");
            else if (purchaseStatus.equals(PurchaseStatus.OFFER_MISMATCH))
                System.out.println("Ta oferta nie jest już aktualna.");
        }else System.out.println("Co się stało?");

    }
}
