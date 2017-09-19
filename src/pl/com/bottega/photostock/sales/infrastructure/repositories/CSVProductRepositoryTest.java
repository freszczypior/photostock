package pl.com.bottega.photostock.sales.infrastructure.repositories;


import pl.com.bottega.photostock.sales.infrastructure.repositories.CSVProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryClientRepository;
import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVProductRepositoryTest {

    public static void main(String[] args) {

        //2,pieski;małpki;chomiki,20,true,null,null

        Set<String> tags = new HashSet<>();
        tags.add("okno");
        tags.add("komin");
        tags.add("drzwi");
        String[] tagsTab = tags.toArray(new String[tags.size()]);

        Client client = new VIPClient("Barbara Barbara", new Address("ul. Zachodnia 11", "Polska", "Lublin", "20-001"), ClientStatus.VIP, Money.valueOf(50), Money.valueOf(50));

        Product product = new Picture(5L, tagsTab, Money.valueOf(10), null, null, true);

        ProductRepository productRepository = new CSVProductRepository("D:/products.csv", new InMemoryClientRepository());


        Set<String> tag = new HashSet<>();
        tag.add("pieski");

        List<Product> list = productRepository.find(client, tag, null, null);
        System.out.println(list.size());

//        Product prod = productRepository.get(2L);
//        System.out.println(prod.getNumber());
    }

}