package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.repositories.*;
import pl.com.bottega.photostock.sales.ui.*;

import java.util.Scanner;

public class PhotostockApp {

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new PhotostockApp().start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        ClientRepository clientRepository = new InMemoryClientRepository();
        ProductRepository productRepository = new CSVProductRepository(
                "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\products.csv",
                clientRepository);
        LightBoxRepository lightBoxRepository = new CSVLightBoxRepository(
                "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\lightboxes.csv",
                productRepository, clientRepository);
        ReservationRepository reservationRepository = new InMemoryReservationRepository();
        PurchaseRepository purchaseRepository = new InMemoryPurchaseRepository();
        LightBoxManagement lightBoxManagement = new LightBoxManagement(lightBoxRepository, clientRepository,
                productRepository, reservationRepository);
        AuthenticationManager authenticationManager = new AuthenticationManager(clientRepository);
        AddProductToLightBoxScreen addProductToLightBoxScreen = new AddProductToLightBoxScreen(lightBoxManagement, scanner);
        PurchaseProcess purchaseProcess = new PurchaseProcess(clientRepository, reservationRepository, productRepository, purchaseRepository);
        PurchaseLightBoxScreen purchaseLightBoxScreen = new PurchaseLightBoxScreen(lightBoxManagement, purchaseProcess, scanner);
        LightBoxManagementScreen lightBoxManagementScreen = new LightBoxManagementScreen(scanner, lightBoxManagement,
                authenticationManager, addProductToLightBoxScreen, purchaseLightBoxScreen);
        ProductCatalog productCatalog = new ProductCatalog(productRepository);
        SearchScreen searchScreen = new SearchScreen(scanner, authenticationManager, productCatalog);
        MainScreen mainScreen = new MainScreen(scanner, lightBoxManagementScreen, searchScreen);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(scanner, authenticationManager);

        authenticationScreen.show();
        mainScreen.show();
    }

}
