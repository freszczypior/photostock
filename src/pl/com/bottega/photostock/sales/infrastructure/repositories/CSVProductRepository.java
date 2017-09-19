package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.omg.CORBA.portable.*;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Picture;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;

import javax.imageio.IIOException;
import java.io.*;
import java.io.OutputStream;
import java.util.*;

public class CSVProductRepository implements ProductRepository {

    private String path;
    private ClientRepository clientRepository;

    public CSVProductRepository(String path, ClientRepository clientRepository) {
        this.path = path;
        this.clientRepository = clientRepository;
    }

    @Override
    public Product get(Long number) {
        return getOptional(number).orElseThrow(() -> new IllegalArgumentException("No such product in REPO")); // wersja z lambda
//        Optional<Product> optional = getOptional(number);
//        if (optional.isPresent())
//            return optional.get();
//        else
//            throw new IllegalArgumentException("No such product in REPO");
    }

    private Client findClient(String number) {
        if (number.equals("null"))
            return null;
        else
            return clientRepository.get(number);
    }


    @Override
    public Optional<Product> getOptional(Long number) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(",");
                if (lineSplit[0].equals(number.toString())) {
                    return Optional.of(toProduct(lineSplit));
                }
            }
            throw new IllegalArgumentException("No such object in repository");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Product toProduct(String[] lineSplit) {
        Long nr = Long.parseLong(lineSplit[0]);
        String[] tags = lineSplit[1].split(";");
        Money price = Money.valueOf(Integer.parseInt(lineSplit[2]));
        boolean active = Boolean.valueOf(lineSplit[3]);
        String reservedNumber = lineSplit[4];
        String ownerNumber = lineSplit[5];
        return new Picture(nr, tags, price, findClient(reservedNumber), findClient(ownerNumber), active);
    }

    @Override
    public void save(Product product) {
        //read file and create map
        Map<Long, Product> productsMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                toMap(productsMap, line, ",");
            }
            productsMap.put(product.getNumber(), product);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //download from map and write to file
        try (OutputStream outputStream = new FileOutputStream(path, false);
             PrintStream printStream = new PrintStream(outputStream)) {         // TODO zapytać Maćka o flush, czy tutaj tego potrzebuję oraz kodowanie
            for (Map.Entry<Long, Product> entry : productsMap.entrySet()) {
                printStream.println(toLine(entry.getKey(),entry.getValue()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toMap(Map<Long, Product> map, String line, String regex) {
        String[] lineSplit = line.split(regex);
        Product tempProduct = toProduct(lineSplit);
        map.put(tempProduct.getNumber(), tempProduct);
    }

    private String toLine(Long number, Product product) {
        //Long number = product.getNumber();
        Set<String> set = ((Picture) product).getTags();
        String[] tagsTab = set.toArray(new String[set.size()]);
        Money price = product.getPrice();
        Boolean active = product.getActive();
        String clientNumber = product.getClient().getNumber();
        String tags = toString(tagsTab);
        return String.format("%d,%s,%s,%s,%s,%s", number, tags, price.toString(), active.toString(), clientNumber, clientNumber);
    }

    private String toString(String[] tags) {
        StringBuffer sb = null;
        if (tags.length > 0)
            sb.append(tags[0]);
        if (tags.length > 1) {
            for (String tag : tags) {
                sb.insert(0, tag + ";");
            }
        }
        return sb.toString();
    }


    @Override
    public List<Product> find(Client client, Set<String> tags, Money from, Money to) {
        List<Product> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(",");
                Product product = toProduct(lineSplit);
                if (product instanceof Picture) {
                    Picture picture = (Picture) product;
                    if (matchesCriteria(picture, client, tags, from, to)) {
                        result.add(product);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean matchesCriteria(Picture picture, Client client, Set<String> tags, Money from, Money to) {
        if (tags != null && !picture.hasTags(tags))
            return false;
        Money price = picture.calculatePrice(client);
        if (from != null && from.gte(price))
            return false;
        return !(to != null && to.lte(price));
    }
}
