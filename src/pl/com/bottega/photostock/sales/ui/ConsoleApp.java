package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryProductRepository;
import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;

import java.util.HashMap;
import java.util.Map;

public class ConsoleApp {

//    public static void main(String[] args) {
//
//        //WSPÓLNE
//
//        ProductRepository repository = new InMemoryProductRepository();
//        Product product1 = repository.get(1L);
//        Product product2 = repository.get(2L);
//        Product product3 = repository.get(3L);
//        Product product4 = repository.get(4L);
//        Product product5 = repository.get(5L);
//
//        LightBoxPresenter lightboxPresenter = new LightBoxPresenter();
//
//        //CLIENT
//
//        Client standardCli = new StandardClient("Jan Nowak", new Address("ul. Północna 11", "Polska", "Lublin", "20-001"));
//        standardCli.reCharge(Money.valueOf(30));
//
//        Reservation reservation = new Reservation(standardCli);
//        LightBox lightBox = new LightBox(standardCli, "Kotki");
//
//        lightBox.add(product1);
//        lightBox.add(product2);
//        lightBox.add(product3);
//
//        lightboxPresenter.show(lightBox);
//
//        reservation.add(product1);
//        reservation.add(product2);
//        reservation.add(product3);
//
//        Offer offer = reservation.generateOffer();
//        Money cost = offer.getTotalCost();      // dla ułatwienia, aby móc później kilka razy ją wywołać
//
//        System.out.println(String.format("W rezerwacji jest %d produktów.", reservation.getItemCount()));   // "%d" (d dla liczb, dla stringów %s) jest tutaj znakiem specjalnym, pod niego podstawiamy konkretną wartość
//        if (standardCli.canAfford(cost)) {
//            Purchase purchase = new Purchase(standardCli, offer.getItems());
//            standardCli.charge(cost, String.format("Zakup %s.", purchase));
//            System.out.println(String.format("Ilość zakupionych zdjęć: %d, całkowity koszt %s.", offer.getItemCount(), offer.getTotalCost()));
//        } else
//            System.out.println("Hehehe, nie stać Cię!");
//
//        //VIPCLIENT
//
//        Client vipClient = new VIPClient("Andrzej Nowak", new Address("ul. Wschodnia 11", "Polska", "Gdańsk", "20-115"));
//        vipClient.reCharge(Money.valueOf(3000));
//
//        Reservation vipReservation = new Reservation(vipClient);
//        LightBox vipLightBox = new LightBox(vipClient, "VIPClient Kotki");
//
//        vipLightBox.add(product4);
//        vipLightBox.add(product5);
//
//        lightboxPresenter.show(vipLightBox);
//
//        vipReservation.add(product4);
//        vipReservation.add(product5);
//
//        Offer vipOffer = vipReservation.generateOffer();
//        Money vipCost = vipOffer.getTotalCost();      // dla ułatwienia, aby móc później kilka razy ją wywołać
//
//        System.out.println(String.format("W rezerwacji jest %d produktów.", vipReservation.getItemCount()));   // "%d" (d dla liczb, dla stringów %s) jest tutaj znakiem specjalnym, pod niego podstawiamy konkretną wartość
//
//        if (vipClient.canAfford(vipCost)) {
//            Purchase purchase = new Purchase(vipClient, vipOffer.getItems());   //TODO problem po wprowadzeniu sortowania purches, zmiana kolekcji na listę
//            vipClient.charge(vipCost, String.format("Zakup %s.", purchase));
//            System.out.println(String.format("Ilość zakupionych zdjęć: %d, całkowity koszt %s.", vipOffer.getItemCount(), vipOffer.getTotalCost()));
//        } else
//            System.out.println("Hehehe, nie stać Cię!");
//
//        Map<String, Double> rates = new HashMap<>();
//        rates.put("USD", 3.6020);
//        rates.put("EUR", 4.2345);
//        CurrencyConverter cc = new CurrencyConverter("PLN", rates);
//        System.out.println(cc.convert(new Money(1000L, "USD")));
//    }
}
