package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.application.ProductCatalog;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.Product;

import java.util.*;

public class SearchScreen {

    private ProductCatalog productCatalog;
    private AuthenticationManager authenticationManager;
    private Scanner scanner;

    public SearchScreen(Scanner scanner, AuthenticationManager authenticationManager,
                        ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
        this.scanner = scanner;
        this.authenticationManager = authenticationManager;
    }

    public void show() {
        System.out.println("Podaj kryteria wyszukiwania");
        System.out.print("Tagi: ");
        Set<String> tags = getTags();
        System.out.print("Cena od: ");
        Money priceFrom = getMoney();
        System.out.println("Cena do: ");
        Money priceTo = getMoney();

        List<Product> productList = productCatalog.find(authenticationManager.getClient(), tags, priceFrom, priceTo);

        for (Product product : productList)
            showProduct(product);
    }

    private void showProduct(Product product) {
        String productType = product instanceof Picture ? "OBRAZEK" : "CLIP";
        String tags = "";
        if (product instanceof Picture)
            tags = ((Picture) product).getTags().toString();
        Money price = product.calculatePrice(authenticationManager.getClient());
        System.out.println(String.format("%d - %s - %s %s", product.getNumber(), productType, tags, price));
    }

    private Money getMoney() {
        String moneyString = scanner.nextLine();
        try {
            Integer moneyInteger = Integer.parseInt(moneyString);
            return Money.valueOf(moneyInteger);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Set<String> getTags() {
        String line = scanner.nextLine();
        String[] tagsArray = line.split(" ");
        List<String> tagsList = Arrays.asList(tagsArray);
        Set<String> tagsSet = new HashSet<>(tagsList);
        tagsSet.remove("");
        return tagsSet;
    }
}

