package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.application.LightboxManagment;
import pl.com.bottega.photostock.sales.model.LightBox;

import java.util.List;
import java.util.Scanner;

public class LightBoxManagmentScreen {

    private LightboxManagment lightboxManagment;
    private Scanner scanner;
    private AuthenticationManager authenticationManager;

    public LightBoxManagmentScreen(Scanner scanner, LightboxManagment lightboxManagment,
                                   AuthenticationManager authenticationManager) {
        this.lightboxManagment = lightboxManagment;
        this.scanner = scanner;
        this.authenticationManager = authenticationManager;
    }

    public void show() {
        System.out.println("Twoje lajt boxy");
        List<LightBox> lightBoxes = lightboxManagment.getLightBoxes(authenticationManager.getClientNumber());
        if (lightBoxes.isEmpty())
            System.out.println("Nie masz lightboxow");
        else {
            int index = 1;
            for (LightBox lightBox: lightBoxes)
                System.out.println(String.format("%d, %s", index++, lightBox.getName()));
        }
    }
    
}
