package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.application.LightboxManagment;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.repositories.LightBoxRepository;

import java.util.List;
import java.util.Scanner;

public class LightBoxManagmentScreen {

    private LightboxPresenter lightboxPresenter;
    private AuthenticationManager authenticationManager;
    private LightboxManagment lightboxManagment;
    private LightBoxRepository lightBoxRepository;
    private Scanner scanner;

    public LightBoxManagmentScreen(Scanner scanner,
                                   LightboxManagment lightboxManagment,
                                   AuthenticationManager authenticationManager,
                                   LightBoxRepository lightBoxRepository,
                                   LightboxPresenter lightboxPresenter) {
        this.authenticationManager = authenticationManager;
        this.lightboxManagment = lightboxManagment;
        this.lightBoxRepository = lightBoxRepository;
        this.scanner = scanner;
        this.lightboxPresenter = lightboxPresenter;
    }

    public void show() {
        while (true) {
            showLightBoxManagmentMenu();
            int decission = scanner.nextInt();
            scanner.nextLine();
            switch (decission) {
                case 1:
                    showLightBoxes();
                    break;
                case 2:
                    createLightBox();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Sorry, ale nie rozumiem!!!");
            }
        }
    }

    private void showLightBoxManagmentMenu() {
        System.out.println("\nMENU LIGHTBOX");
        System.out.println("1. Pokaż lajtboxy");
        System.out.println("2. Dodaj lajtboxa.");
        System.out.println("3. Wróć do poprzedniego Menu");
        System.out.print("Co chcesz zrobić? ");
    }

    private void showLightBoxes() {
        List<LightBox> lightBoxes = lightboxManagment.getLightBoxes(authenticationManager.getClientNumber());
        if (lightBoxes.isEmpty()) {
            System.out.println("Nie masz lajtboxów");
        } else {
            System.out.println("\nTwoje lajt boxy:");
            int index = 1;
            for (LightBox lightBox : lightBoxes)
                System.out.println(String.format("%d. %s", index++, lightBox.getName()));
            showLightBoxDetailsMenu(lightBoxes);
        }
    }

    private void showLightBoxDetailsMenu(List<LightBox> lightBoxes) {
        if (!lightBoxes.isEmpty()) {
            while (true) {
                System.out.println("\nWybierz:");
                System.out.println("1. Szczegóły");
                System.out.println("2. Powrót do poprzedniego menu");
                int decission = scanner.nextInt();
                scanner.nextLine();
                switch (decission) {
                    case 1:
                        LightBox lightBox = getLightBox(lightBoxes);
                        showLightBox(lightBox);
                        showDeletePictureMenu(lightBox);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Wybierze 1 lub 2");
                }
            }
        }else return;
    }


    private LightBox getLightBox(List<LightBox> lightBoxes) {
        System.out.println("Podaj numer lajtboxa, którego chcesz użyć:");
        while (true) {
            int decission = scanner.nextInt();
            scanner.nextLine();
            if (decission > 0 && decission <= lightBoxes.size()) {
                return lightBoxes.get(decission - 1);
            } else
                System.out.println(String.format("Podaj numer z zakresu od 1 do %d", lightBoxes.size()));
        }
    }

    private void showLightBox(LightBox lightBox) {
        lightboxPresenter.showLightbox(lightBox);
    }

    private void showDeletePictureMenu(LightBox lightBox) {
        if (!lightBox.getItems().isEmpty()) {
            while (true) {
                System.out.println("\nWybierz:");
                System.out.println("1. Usuń zdjęcie");
                System.out.println("2. Powrót do poprzedniego menu");
                int decission = scanner.nextInt();
                scanner.nextLine();
                switch (decission) {
                    case 1:
                        removePicture(lightBox);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Wybierze 1 lub 2");
                }
            }
        } else return;
    }
    private void createLightBox() {
        while (true) {
            System.out.print("\nPodaj nazwę nowego lajtboxa: ");
            String chosenName = scanner.nextLine();
            String clientNumber = authenticationManager.getClient().getNumber();
            List<LightBox> lightBoxes = lightboxManagment.getLightBoxes(clientNumber);
            for (LightBox lightBox : lightBoxes) {
                if (lightBox.getName().equalsIgnoreCase(chosenName)) {
                    System.out.println("Taki lajtbox już istnieje.");
                    continue;
                }
            }lightboxManagment.create(clientNumber, chosenName);
            return;
        }
    }

    private void removePicture(LightBox lightBox) {
        List<Picture> listOfPictures = lightBox.getItems();
        System.out.print("\n Podaj numer zdjęcia do usunięcia: ");
        while (true) {
            int decission = scanner.nextInt();
            scanner.nextLine();
            if (decission > 0 && decission <= listOfPictures.size()) {
                lightboxManagment.remove(lightBox.getNumber(), listOfPictures.get(decission - 1).getNumber());
                return;
            } else
                System.out.println(String.format("Podaj numer z zakresu od 1 do %d", listOfPictures.size()));
        }
    }

}
