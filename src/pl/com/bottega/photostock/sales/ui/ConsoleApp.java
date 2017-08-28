package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.infrastructure.InMemoryPictureRepository;
import pl.com.bottega.photostock.sales.model.*;

import java.util.HashMap;
import java.util.Map;

public class ConsoleApp {

    public static void main(String[] args) {

        //WSPÓLNE

        PictureRepository repository = new InMemoryPictureRepository();
        Product picture1 = repository.get(1L);
        Product picture2 = repository.get(2L);
        Product picture3 = repository.get(3L);
        Product picture4 = repository.get(4L);
        Product picture5 = repository.get(5L);

        LightboxPresenter lightboxPresenter = new LightboxPresenter();

        //CLIENT

        Client client = new Client("Jan Nowak", new Address("ul. Północna 11", "Polska", "Lublin", "20-001"));
        client.reCharge(Money.valueOf(30));

        Reservation reservation = new Reservation(client);
        LightBox lightBox = new LightBox(client, "Kotki");

        lightBox.add(picture1);
        lightBox.add(picture2);
        lightBox.add(picture3);

        lightboxPresenter.showLightbox(lightBox);

        reservation.add(picture1);
        reservation.add(picture2);
        reservation.add(picture3);

        Offer offer = reservation.generateOffer();
        Money cost = offer.getTotalCost();      // dla ułatwienia, aby móc później kilka razy ją wywołać

        System.out.println(String.format("W rezerwacji jest %d produktów.", reservation.getItemCount()));   // "%d" (d dla liczb, dla stringów %s) jest tutaj znakiem specjalnym, pod niego podstawiamy konkretną wartość
        if (client.canAfford(cost)) {
            Purchase purchase = new Purchase(client, offer.getItems());
            client.charge(cost, String.format("Zakup %s.", purchase));
            System.out.println(String.format("Ilość zakupionych zdjęć: %d, całkowity koszt %s.", offer.getItemCount(), offer.getTotalCost()));
        } else
            System.out.println("Hehehe, nie stać Cię!");

        //VIPCLIENT

        VIPClient vipClient = new VIPClient("Andrzej Nowak",
                new Address("ul. Wschodnia 11", "Polska", "Gdańsk", "20-115"),
                Money.valueOf(100));
//        System.out.println(vipClient.getTransactions().size());

        Reservation vipReservation = new Reservation(vipClient);
        LightBox vipLightBox = new LightBox(vipClient, "VIPClient Kotki");

        vipLightBox.add(picture4);
        vipLightBox.add(picture5);

        lightboxPresenter.showLightbox(vipLightBox);

        vipReservation.add(picture4);
        vipReservation.add(picture5);

        Offer vipOffer = vipReservation.generateOffer();
        Money vipCost = vipOffer.getTotalCost();      // dla ułatwienia, aby móc później kilka razy ją wywołać

        System.out.println(String.format("W rezerwacji jest %d produktów.", vipReservation.getItemCount()));   // "%d" (d dla liczb, dla stringów %s) jest tutaj znakiem specjalnym, pod niego podstawiamy konkretną wartość

        if (vipClient.canAfford(vipCost)) {
            Purchase purchase = new Purchase(vipClient, vipOffer.getItems());   //TODO problem po wprowadzeniu sortowania purches, zmiana kolekcji na listę
            vipClient.charge(vipCost, String.format("Zakup %s.", purchase));
            System.out.println(String.format("Ilość zakupionych zdjęć: %d, całkowity koszt %s.", vipOffer.getItemCount(), vipOffer.getTotalCost()));
        } else
            System.out.println("Hehehe, nie stać Cię!");

        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 3.6020);
        rates.put("EUR", 4.2345);
        CurrencyConventer cc = new CurrencyConventer("PLN", rates);
        System.out.println(cc.convert(new Money(1000L, "USD")));
    }
}
