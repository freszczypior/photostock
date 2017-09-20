package pl.com.bottega.photostock.sales.infrastructure.repositories;


import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.repositories.LightBoxRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVLightBoxRepository implements LightBoxRepository{
    private String path = "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\lightboxes.csv";

    @Override
    public void save(LightBox lightBox) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
            String[] lineSplit = line.split(";");
                LightBox lb = toLightBox(lineSplit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LightBox toLightBox(String[] lineSplit) {
        String number = lineSplit[0];
        String name = lineSplit[1];
        String [] numbers = lineSplit[2].split(",");
        List<Long> prodlist = null;
        for (String temp: numbers) {
            prodlist.add(Long.parseLong(temp));
        }
        String clientNumber = lineSplit[3];
        return null;
    }

    @Override
    public LightBox get(String number) {
        return null;
    }

    @Override
    public List<LightBox> getClientLightBoxes(String clientNumber) {
        return null;
    }
}
