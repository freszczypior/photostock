package pl.com.bottega.photostock.sales.application;


import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.repositories.ReservationRepository;

import java.util.List;
import java.util.Set;

public class LightBoxManagement {

    private ClientRepository clientRepository;
    private LightBoxRepository lightBoxRepository;
    private ProductRepository productRepository;
    private ReservationRepository reservationRepository;

    public LightBoxManagement(LightBoxRepository lightBoxRepository, ClientRepository clientRepository, ProductRepository productRepository,
                              ReservationRepository reservationRepository) {
        this.clientRepository = clientRepository;
        this.lightBoxRepository = lightBoxRepository;
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    public String create(String clientNumber, String lightboxName) {
        Client client = clientRepository.get(clientNumber);
        LightBox lightBox = new LightBox(client, lightboxName);
        lightBoxRepository.save(lightBox);
        return lightBox.getNumber();
    }

    public void add(String lightboxNumber, Long productNumber) {
        Product product = productRepository.get(productNumber);
        if (!(product instanceof Picture))
            throw new IllegalStateException(String.format("Product %s is not a picture", productNumber));
        LightBox lightBox = lightBoxRepository.get(lightboxNumber);
        Picture picture = (Picture) product;
        lightBox.add(picture);
        lightBoxRepository.save(lightBox);
    }

    public void remove(String lightboxNumber, Long productNumber){
        LightBox lightBox = lightBoxRepository.get(lightboxNumber);
        lightBox.remove((Picture) productRepository.get(productNumber));
        lightBoxRepository.save(lightBox);
    }

    public void reserve(String lightboxNumber, Set<Long> pictureNumbers, String reservationNumber) {
        LightBox lightBox = lightBoxRepository.get(lightboxNumber);
        Reservation reservation = reservationRepository.get(reservationNumber);
        List<Picture> pictures = lightBox.getPictures(pictureNumbers);
        if (pictureNumbers.size() != pictureNumbers.size())
            throw new IllegalArgumentException("Invalid product numbers");
        for (Picture picture : pictures)
            picture.ensureAvailable();
        for (Picture picture : pictures) {
            reservation.add(picture);
            productRepository.save(picture);
        }
        reservationRepository.save(reservation);
    }


    public List<LightBox> getLightBoxes(String clientNumber) {
        return lightBoxRepository.getClientLightBoxes(clientNumber);
    }

    public LightBox getLightBox(String lightBoxNumber){
        return lightBoxRepository.get(lightBoxNumber);
    }

}
