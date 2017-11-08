package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.application.PurchaseProcess;
import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.repositories.*;
import pl.com.bottega.photostock.sales.ui.*;

import java.util.Scanner;

public class PhotostockApp {

    private final String PROD_REPO_PATH = "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\products.csv";
    private final String LIGHTBOX_REPO_PATH = "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\lightboxes.csv";
    private final String PURCHES_REPO_PATH = "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\purchases.csv";
    private final String CLIENT_REPO_PATH = "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\clients.csv";
    private final String RESERV_REPO_PATH = "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\reservations.csv";

    public static void main(String[] args) {
        new PhotostockApp().start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        ClientRepository clientRepository = new CSVClientRepository(
                CLIENT_REPO_PATH);
        ProductRepository productRepository = new CSVProductRepository(
                PROD_REPO_PATH, clientRepository);
        LightBoxRepository lightBoxRepository = new CSVLightBoxRepository(
                LIGHTBOX_REPO_PATH, productRepository, clientRepository);
        ReservationRepository reservationRepository = new CSVReservationRepository(
                RESERV_REPO_PATH, productRepository, clientRepository);
        PurchaseRepository purchaseRepository = new CSVPurchaseRepository(
                PURCHES_REPO_PATH, clientRepository, productRepository);
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
