package pl.com.bottega.photostock.sales.infrastructure.repositories;


import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CSVLightBoxRepository implements LightBoxRepository {

    private String path;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;

    public CSVLightBoxRepository(String path, ProductRepository productRepository, ClientRepository clientRepository) {
        this.path = path;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(LightBox lightBox) {
        Map<String, LightBox> lightBoxMap = new HashMap<>();
        toMap(path, lightBoxMap);
        lightBoxMap.put(lightBox.getNumber(), lightBox);
        toFile(lightBoxMap, path, false);
    }

    private void toFile(Map<String, LightBox> map, String path, boolean append) {
        try (OutputStream outputStream = new FileOutputStream(path, append)) {
            PrintStream printStream = new PrintStream(outputStream);
            for (Map.Entry<String, LightBox> entry : map.entrySet()) {
                printStream.println(toLine(entry.getKey(), entry.getValue()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toMap(String path, Map<String, LightBox> map) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                LightBox lb = toLightBox(lineSplit);
                map.put(lb.getNumber(), lb);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toLine(String number, LightBox lightBox) {
        String name = lightBox.getName();
        List<Picture> picturesList = lightBox.getItems();
        String[] picturesTab = picturesList.toArray(new String[picturesList.size()]);
        Client owner = lightBox.getClient();
        return String.format("%s;%s;%s;%s", number, name, toString(picturesTab), owner.getNumber());
    }

    private LightBox toLightBox(String[] lineSplit) {
        String number = lineSplit[0];
        String name = lineSplit[1];
        String[] pictures = lineSplit[2].split(",");
        List<Picture> pictureslist = null;
        for (String temp : pictures) {
            Picture picture = (Picture) productRepository.get(Long.parseLong(temp));
            pictureslist.add(picture);
        }
        String clientNumber = lineSplit[3];
        return new LightBox(number, name, pictureslist, clientRepository.get(clientNumber));
    }

    private String toString(String[] pictures) {        //TODO do nadklasy
        StringBuilder sb = new StringBuilder();
        if (pictures.length == 1)
            sb.append(pictures[0]);
        if (pictures.length > 1) {
            sb.append(pictures[0]);
            for (int i = 1; i < pictures.length; i++) {
                sb.insert(0, pictures[i] + ",");
            }
        }
        return sb.toString();
    }

    @Override
    public LightBox get(String number) {
        Map<String, LightBox> map = new HashMap<>();
        toMap(path, map);
        LightBox lightBox = map.get(number);
        if (lightBox != null)
            return lightBox;
        else
            throw new IllegalArgumentException("No such object in repository");
    }

    @Override
    public List<LightBox> getClientLightBoxes(String clientNumber) {
        Map<String, LightBox> map = new HashMap<>();
        List<LightBox> list = new LinkedList<>();
        toMap(path, map);
        for (LightBox lightBox : map.values()) {
            if (lightBox.getClient().getNumber().equals(clientNumber))
                list.add(lightBox);
        }
        if (list.size() > 0)
            return list;
        else
            throw new IllegalArgumentException("No such object in repository");
    }
}
