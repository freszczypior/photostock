package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;

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


    @Override
    public Optional<Product> getOptional(Long number) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                if (lineSplit[0].equals(number.toString()))
                    return Optional.of(toProduct(lineSplit));
            }
            throw new IllegalArgumentException("No such object in repository");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Product toProduct(String[] lineSplit) {     // TODO repo product a zwracam picture
        Long nr = Long.parseLong(lineSplit[0]);
        Money price = Money.valueOf(Integer.parseInt(lineSplit[2]));
        boolean active = Boolean.valueOf(lineSplit[3]);
        String reservedNumber = lineSplit[4];
        String ownerNumber = lineSplit[5];
        Brand brand = toBrand(lineSplit[6]);
        if (brand == Brand.PICTURE) {
            String[] tags = lineSplit[1].split(",");
            return new Picture(nr, tags, price, findClient(reservedNumber), findClient(ownerNumber), active, brand);
        } else {
            Long length = Long.parseLong(lineSplit[1]);
            return new Clip(nr, length, price, findClient(reservedNumber), findClient(ownerNumber), active, brand);
        }
    }

    private Brand toBrand(String brand) {
        return brand.toLowerCase().equals("clip") ? Brand.CLIP : Brand.PICTURE;
    }

    private Client findClient(String number) {
        if (number.equals("null"))
            return null;
        else
            return clientRepository.get(number);
    }

    @Override
    public void save(Product product) {
        Map<Long, Product> productsMap = new HashMap<>();
        toMap(path, productsMap);
        productsMap.put(product.getNumber(), product);
        toFile(productsMap, path, false);
    }

    private void toFile(Map<Long, Product> productsMap, String path, boolean append) {
        try (OutputStream outputStream = new FileOutputStream(path, append);
             PrintStream printStream = new PrintStream(outputStream)) {         // TODO flush, czy tutaj tego potrzebuję oraz co z kodowaniem
            for (Map.Entry<Long, Product> entry : productsMap.entrySet()) {
                printStream.println(toLine(entry.getKey(), entry.getValue()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toMap(String path, Map<Long, Product> productsMap) {   //TODO a co z rozróżnieniem czy picture czy clip
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                Product tempProduct = toProduct(lineSplit);
                productsMap.put(tempProduct.getNumber(), tempProduct);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toLine(Long number, Product product) {
        String price = product.getPrice().toString();       //TODO zwraca np 10.0 CREDIT, do tej pory w pliku zapisywaliśmy price jako np 10
        Boolean active = product.getActive();
        Client reservedClient = product.getReservedBy();
        Client ownerClient = product.getOwner();
        Brand brand = product.getBrand();
        if (product instanceof Picture) {
            Set<String> set = ((Picture) product).getTags();
            String[] tagsTab = set.toArray(new String[set.size()]);
            return String.format("%d;%s;%s;%s;%s;%s;%s", number, toString(tagsTab), price, active.toString(), toNumber(reservedClient), toNumber(ownerClient), brand);
        } else {
            String length = ((Clip) product).getLenght().toString();
            return String.format("%d;%s;%s;%s;%s;%s;%s", number, length, price, active.toString(), toNumber(reservedClient), toNumber(ownerClient), brand);
        }
    }

    private String toNumber(Client client) {
        return client == null ? null : client.getNumber();
    }

    private String toString(String[] tags) {        //TODO do nadklasy
        StringBuilder sb = new StringBuilder();
        if (tags.length == 1)
            sb.append(tags[0]);
        if (tags.length > 1) {
            sb.append(tags[0]);
            for (int i = 1; i < tags.length; i++) {
                sb.insert(0, tags[i] + ",");
            }
//        if (tags.length > 0) {
//            sb.append(tags[0]);
//            if (tags.length > 1)
//                for (int i = 1; i < tags.length; i++) {
//                    sb.insert(0, tags[i] + ",");
//                }
        }
        return sb.toString();
        }


        @Override
        public List<Product> find (Client client, Set < String > tags, Money from, Money to)
        {      // TODO a co z klipami?
            List<Product> result = new LinkedList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] lineSplit = line.split(";");
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
