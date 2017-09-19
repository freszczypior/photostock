package pl.com.bottega.photostock.sales.ui;

import java.util.Scanner;

public class MainScreen {

    private LightBoxManagementScreen lightBoxManagementScreen;
    private SearchScreen searchScreen;
    private Scanner scanner;

    public MainScreen(Scanner scanner, LightBoxManagementScreen lightBoxManagementScreen, SearchScreen searchScreen) {
        this.lightBoxManagementScreen = lightBoxManagementScreen;
        this.searchScreen = searchScreen;
        this.scanner = scanner;
    }

    public void show(){
        Menu menu = new Menu(scanner);
        menu.setTitleLabel("!!!Witamy w PHOTOSTOCK!!!");
        menu.addItem("Wyszukaj produkty", () -> searchScreen.show());
        menu.addItem("Lajt boksy", () -> lightBoxManagementScreen.show());
        menu.setLastItemLabel("Zakończ");
        menu.show();
    }
}
