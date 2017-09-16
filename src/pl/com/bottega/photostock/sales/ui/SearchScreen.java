package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.application.LightboxManagment;
import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.LightBoxRepository;

import java.util.*;

public class SearchScreen {

    private ProductCatalog productCatalog;
    private AuthenticationManager authenticationManager;
    private LightboxManagment lightboxManagment;
    private LightBoxRepository lightBoxRepository;
    private Scanner scanner;

    public SearchScreen(Scanner scanner,
                        AuthenticationManager authenticationManager,
                        ProductCatalog productCatalog,
                        LightBoxRepository lightBoxRepository,
                        LightboxManagment lightboxManagment) {
        this.authenticationManager = authenticationManager;
        this.lightboxManagment = lightboxManagment;
        this.lightBoxRepository = lightBoxRepository;
        this.scanner = scanner;
        this.productCatalog = productCatalog;
    }

    public void show() {
        Client client = authenticationManager.getClient();
        List<Product> productList = askAboutParameters(client);

        if (!productList.isEmpty()) {
            for (Product product : productList)
                showProduct(product);
            showProductsDetailsMenu(productList);
        } else
            System.out.println("Brak produktów z tymi kryteriami");
    }

    private List<Product> askAboutParameters(Client client) {
        System.out.println("Podaj kryteria wyszukiwania");
        System.out.print("Tagi: ");
        Set<String> tags = getTags();
        System.out.print("Cena od: ");
        Money priceFrom = getMoney();
        System.out.println("Cena do: ");
        Money priceTo = getMoney();
        return productCatalog.find(client, tags, priceFrom, priceTo);
    }

    private Money getMoney() {
        String moneyString = scanner.nextLine();
        try {
            Integer moneyInteger = Integer.parseInt(moneyString);
            return Money.valueOf(moneyInteger);
        } catch (Exception ex) {
            return null;
        }
    }

    private Set<String> getTags() {
        String line = scanner.nextLine();
        String[] tagsArray = line.split(" ");
        List<String> tagsList = Arrays.asList(tagsArray);
        Set<String> tagsSet = new HashSet<>(tagsList);
        tagsSet.remove("");
        return tagsSet;
    }

    private void showProduct(Product product) {
        String productType = product instanceof Picture ? "OBRAZEK" : "CLIP";
        String tags = "";
        if (product instanceof Picture)
            tags = ((Picture) product).getTags().toString();
        Money price = product.calculatePrice(authenticationManager.getClient());
        System.out.println(String.format("%d - %s - %s %s", product.getNumber(), productType, tags, price));
    }

    private void showProductsDetailsMenu(List<Product> productList) {
        while (true) {
            System.out.println("\nWybierz:");
            System.out.println("1. Dodaj do lajtboxa.");
            System.out.println("2. Powrót do poprzedniego menu");
            int decission = scanner.nextInt();
            scanner.nextLine();
            switch (decission) {
                case 1:
                   addToLightBox(productList);
                   break;
                case 2:
                    return;
                default:
                    System.out.println("Wybierze 1 lub 2");
            }
        }
    }

    private void addToLightBox(List<Product> productList){
        List<LightBox> lightBoxes = lightboxManagment.getLightBoxes(authenticationManager.getClientNumber());
        if (!lightBoxes.isEmpty()) {
            Picture picture = getPicture(productList);
            showLightBoxes(lightBoxes);
            LightBox lightBox = getLightBox(lightBoxes);
            lightboxManagment.add(lightBox.getNumber(), picture.getNumber());
        } else {
            System.out.println("Nie masz lajtboxów");

        }

    }


    private Picture getPicture(List<Product> productList) {
        while (true) {
            System.out.print("\n Podaj numer zdjęcia, które chcesz dodać do lajtboxa:");
            int decission = scanner.nextInt();
            scanner.nextLine();
            if (decission > 0 && decission <= productList.size())
                return (Picture) productList.get(decission - 1);
            else
                System.out.println(String.format("Podaj numer z zakresu od 1 do %d", productList.size()));
        }
    }

    private LightBox getLightBox(List<LightBox> lightBoxes) {
        while (true) {
            System.out.println("Podaj numer lajtboxa, którego chcesz użyć:");
            int decission = scanner.nextInt();
            scanner.nextLine();
            if (decission > 0 && decission <= lightBoxes.size()) {
                return lightBoxes.get(decission - 1);
            } else
                System.out.println(String.format("Podaj numer z zakresu od 1 do %d", lightBoxes.size()));
        }
    }

    private void showLightBoxes(List<LightBox> lightBoxes) {
        System.out.println("\nTwoje lajt boxy:");
        int index = 1;
        for (LightBox lightBox : lightBoxes)
            System.out.println(String.format("%d. %s", index++, lightBox.getName()));
    }

}

