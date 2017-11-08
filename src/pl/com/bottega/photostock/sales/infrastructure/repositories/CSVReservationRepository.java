package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.repositories.ReservationRepository;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CSVReservationRepository implements ReservationRepository {

    private String path;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;

    public CSVReservationRepository(String path, ProductRepository productRepository,
                                    ClientRepository clientRepository) {
        this.path = path;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(Reservation reservation) {
        Map<String, Reservation> map = new HashMap<>();
        toMap(path, map);
        map.put(reservation.getNumber(), reservation);
        toFile(map, path, false);
    }


    private void toMap(String path, Map<String, Reservation> map){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                String number = lineSplit[0];
                String owner = lineSplit[1];
                String[] items = lineSplit[2].split(",");
                Set<Product> itemsSet = new HashSet<>();
                for (String prodNum: items) {
                    itemsSet.add(productRepository.get(Long.parseLong(prodNum)));
                }
                map.put(number, new Reservation(number, clientRepository.get(owner), itemsSet));
            }
            throw new IllegalArgumentException("No such object in repository");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String toLine(Set<Product> items) {
        int iter = 0;
        String[] itemsNumTab = new String[items.size()];
        for (Product product: items) {
            itemsNumTab[iter] = product.getNumber().toString();
            iter++;
        }
        StringBuilder sb = new StringBuilder();
        if (itemsNumTab.length == 1)
            sb.append(itemsNumTab[0]);
        if (itemsNumTab.length > 1){
            sb.append(itemsNumTab[0]);
            for (int i = 1; i < itemsNumTab.length; i++){
                sb.insert(0, itemsNumTab[i] + ",");
            }
        }
        return sb.toString();
    }

    private void toFile(Map<String, Reservation> map, String path, boolean append) {
        try (OutputStream os = new FileOutputStream(path, append)){
            PrintStream ps = new PrintStream(os);
            for (Reservation tempReserv: map.values()) {
                String number = tempReserv.getNumber();
                Client owner = tempReserv.getClient();
                Set<Product> items = tempReserv.getItems();
                ps.println(String.format("%s;%s;%s", number, owner.getNumber(), toLine(items)));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation get(String number) {
        Map<String, Reservation> map = new HashMap<>();
        toMap(path, map);
        for (Reservation reservation: map.values()) {
            if (reservation.getNumber().equals(number))
                return reservation;
        }
        throw new IllegalArgumentException("No such object in repository");
    }
}
