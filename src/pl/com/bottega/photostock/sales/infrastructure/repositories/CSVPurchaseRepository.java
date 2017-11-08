package pl.com.bottega.photostock.sales.infrastructure.repositories;


import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.repositories.PurchaseRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVPurchaseRepository implements PurchaseRepository {

    private String path;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;

    public CSVPurchaseRepository(String path, ClientRepository clientRepository, ProductRepository productRepository) {
        this.path = path;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Purchase get(String number) {
        Map<String, Purchase> purchaseMap = new HashMap<>();
        toMap(path, purchaseMap);
        for (Purchase purchase : purchaseMap.values()) {
            if (purchase.getNumber().equals(number))
                return new Purchase(
                        purchase.getNumber(), purchase.getBuyer(), purchase.getItems(), purchase.getPurchaseDate());
        }
        throw new IllegalArgumentException("No such object in repository");
    }

    @Override
    public void save(Purchase purchase) {
        Map<String, Purchase> purchaseMap = new HashMap<>();
        toMap(path, purchaseMap);
        purchaseMap.put(purchase.getNumber(), purchase);
        toFile(purchaseMap, path, false);
    }


    private void toMap(String path, Map<String, Purchase> productsMap) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                Purchase tempPurchase = toPurchase(lineSplit);
                productsMap.put(tempPurchase.getNumber(), tempPurchase);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toFile(Map<String, Purchase> purchaseMap, String path, boolean append) {
        try (OutputStream outputStream = new FileOutputStream(path, append);
             PrintStream printStream = new PrintStream(outputStream)) {
            for (Map.Entry<String, Purchase> entry : purchaseMap.entrySet()) {
                printStream.println(toLine(entry.getKey(), entry.getValue()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Purchase toPurchase(String[] lineSplit) {
        String nr = lineSplit[0];
        Client buyer = clientRepository.get(lineSplit[1]);
        String[] prodacts = lineSplit[2].split(",");
        Integer year = Integer.parseInt(lineSplit[3]);
        Integer month = Integer.parseInt(lineSplit[4]);
        Integer day = Integer.parseInt(lineSplit[5]);
        Integer hour = Integer.parseInt(lineSplit[6]);
        Integer minute = Integer.parseInt(lineSplit[7]);
        LocalDateTime purchaseDate = toLocalDateTime(year, month, day, hour, minute);
        List<Product> productslist = null;
        for (String temp : prodacts) {
            Product product = productRepository.get(Long.parseLong(temp));
            productslist.add(product);
        }
        return new Purchase(nr, buyer, productslist, purchaseDate);
    }

    private LocalDateTime toLocalDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private String toLine(String purchaseNumber, Purchase purchase) {
        String buyerNumber = purchase.getBuyer().getNumber();
        List<Product> productsList = purchase.getItems();
        String[] productsTab = new String[productsList.size()];
        int iter = 0;
        for (Product prod : productsList) {
            productsTab[iter] = prod.getNumber().toString();
            iter++;
        }
        return String.format("%s;%s;%s;%s",
                purchaseNumber, buyerNumber, toString(productsTab), localDateTimeToString(purchase.getPurchaseDate()));
    }

    private String localDateTimeToString(LocalDateTime localDateTime) {
        Integer year = localDateTime.getYear();
        Integer month = localDateTime.getMonth().getValue();
        Integer day = localDateTime.getDayOfMonth();
        Integer hour = localDateTime.getHour();
        Integer minute = localDateTime.getMinute();
        return String.format("%d,%d,%d,%d,%d", year, month, day, hour, minute);
    }

    private String toString(String[] productsTab) {
        StringBuilder sb = new StringBuilder();
        if (productsTab.length == 1)
            sb.append(productsTab[0]);
        if (productsTab.length > 1) {
            sb.append(productsTab[0]);
            for (int i = 1; i < productsTab.length; i++) {
                sb.insert(0, productsTab[i] + ",");
            }
        }
        return sb.toString();
    }
}
