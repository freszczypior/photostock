package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Picture;

public class LightboxPresenter {

    public void showLightbox(LightBox lightBox){
        System.out.println(lightBox.getName());
        System.out.println("-------------------------------");
        int index = 0;
        Client client = lightBox.getClient();
        for (Picture picture: lightBox.getItems()) {
            System.out.println(String.format("%s %d. %d | %s",
                    picture.isAvailable() ? "" : "X", index++, picture.getNumber(),
                    picture.calculatePrice(client)));
        }

    }
}
