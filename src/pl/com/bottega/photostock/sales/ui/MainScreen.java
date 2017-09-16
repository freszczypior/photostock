package pl.com.bottega.photostock.sales.ui;

import java.util.Scanner;

public class MainScreen {

    private LightBoxManagmentScreen lightBoxManagmentScreen;
    private SearchScreen searchScreen;
    private Scanner scanner;

    public MainScreen(Scanner scanner, LightBoxManagmentScreen lightBoxManagmentScreen, SearchScreen searchScreen) {
        this.lightBoxManagmentScreen = lightBoxManagmentScreen;
        this.searchScreen = searchScreen;
        this.scanner = scanner;
    }

    public void show(){
        while(true) {
            showMenu();
            int decission = scanner.nextInt();
            scanner.nextLine();
            switch (decission) {
                case 1:
                    searchScreen.show();
                    break;
                case 2:
                    lightBoxManagmentScreen.show();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Sorry, ale nie rozumiem!!!");
            }
        }

    }

    private void showMenu() {
        System.out.println("\n!!!Witamy w PHOTOSTOCK!!!");
        System.out.println("1. Wyszukaj produkty.");
        System.out.println("2. Lajt boksy.");
        System.out.println("3. Zakończ.");
        System.out.print("Co chcesz zrobić? ");
    }
}
