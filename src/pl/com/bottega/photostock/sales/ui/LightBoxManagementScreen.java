package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.application.LightBoxManagement;
import pl.com.bottega.photostock.sales.model.LightBox;

import java.util.List;
import java.util.Scanner;

public class LightBoxManagementScreen {

    private Scanner scanner;
    private LightBoxManagement lightBoxManagement;
    private AuthenticationManager authenticationManager;
    private List<LightBox> lightBoxes;
    private LightBox lightBox;
    private AddProductToLightBoxScreen addProductToLightBoxScreen;
    private PurchaseLightBoxScreen purchaseLightBoxScreen;

    public LightBoxManagementScreen(Scanner scanner, LightBoxManagement lightBoxManagement,
                                    AuthenticationManager authenticationManager, AddProductToLightBoxScreen addProductToLightBoxScreen, PurchaseLightBoxScreen purchaseLightBoxScreen) {
        this.scanner = scanner;
        this.lightBoxManagement = lightBoxManagement;
        this.authenticationManager = authenticationManager;
        this.addProductToLightBoxScreen = addProductToLightBoxScreen;
        this.purchaseLightBoxScreen = purchaseLightBoxScreen;
    }

    public void show() {
        System.out.println("Twoje lajt boksy:");
        lightBoxes = lightBoxManagement.getLightBoxes(authenticationManager.getClientNumber());
        if (lightBoxes.isEmpty())
            System.out.println("Nie masz aktualnie żadnych lajt boksów");
        else {
            showLightBoxes();
        }
        lightBoxActions();
    }

    private void showLightBoxes() {
        int index = 1;
        for (LightBox lightBox : lightBoxes)
            System.out.println(String.format("%d. %s", index++, lightBox.getName()));
    }

    private void lightBoxActions() {
        Menu menu = new Menu(scanner);
        menu.setTitleLabel("LIGHTBOX MENU");
        menu.addItem("Dodaj nowy LightBox.", () -> addNewLightBox());
        menu.addItem("Wyświetl LightBox.", () -> {
                    if (lightBoxes.size() > 0)
                        showLightBox();
                });
        menu.setLastItemLabel("Wróć do menu.");
        menu.show();
    }

    private void showLightBox() {
        Menu menu = new Menu(scanner);
        int index = menu.getUserDecisionDigit("Podaj index Lightbox'a: ");
        lightBox = lightBoxes.get(index - 1);
        LightBoxPresenter presenter = new LightBoxPresenter();
        presenter.show(lightBox);
        selectedLightBoxActions();
    }

    private void selectedLightBoxActions() {
        Menu menu = new Menu(scanner);
        menu.setTitleLabel("LIGHTBOX - OPERACJE");
        menu.addItem("Dodaj produkt do LightBox'a", () -> addProductToLightBoxScreen.show(lightBox));
        menu.addItem("Zakup produkty z lajt boxa.", () ->  purchaseLightBoxScreen.show(lightBox));
        menu.setLastItemLabel("Wróć do poprzedniego menu.");
        menu.show();
    }

    private void addNewLightBox() {
        Menu menu = new Menu(scanner);
        String name = menu.getUserDecisionWord("Podaj nazwę nowego LighBox'a: ");
        String clientNumber = authenticationManager.getClientNumber();

        lightBoxManagement.create(clientNumber, name);

        lightBoxes = lightBoxManagement.getLightBoxes(clientNumber);

        System.out.println(String.format("LightBox %s został dodany.", name));
    }

}
