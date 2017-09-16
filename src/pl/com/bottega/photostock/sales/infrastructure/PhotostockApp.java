package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.application.LightboxManagment;
import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryLightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryReservationRepository;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.repositories.ReservationRepository;
import pl.com.bottega.photostock.sales.ui.*;

import java.util.Scanner;

public class PhotostockApp {

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new PhotostockApp().start();
    }

    public void start(){
        ReservationRepository reservationRepository = new InMemoryReservationRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        ClientRepository clientRepository = new InMemoryClientRepository();
        LightBoxRepository lightBoxRepository = new InMemoryLightBoxRepository();
        LightboxManagment lightboxManagment = new LightboxManagment(clientRepository, lightBoxRepository,
                productRepository, reservationRepository);
        AuthenticationManager authenticationManager = new AuthenticationManager(clientRepository);
        LightboxPresenter lightboxPresenter = new LightboxPresenter();
        LightBoxManagmentScreen lightBoxManagmentScreen = new LightBoxManagmentScreen(scanner, lightboxManagment,
                authenticationManager, lightBoxRepository, lightboxPresenter);
        ProductCatalog productCatalog = new ProductCatalog(productRepository);
        SearchScreen searchScreen = new SearchScreen(scanner, authenticationManager, productCatalog, lightBoxRepository, lightboxManagment);
        MainScreen mainScreen = new MainScreen(scanner, lightBoxManagmentScreen, searchScreen);
        AuthenticatiionScreen authenticatiionScreen = new AuthenticatiionScreen(authenticationManager, scanner);

        authenticatiionScreen.show();
        mainScreen.show();
    }

}
