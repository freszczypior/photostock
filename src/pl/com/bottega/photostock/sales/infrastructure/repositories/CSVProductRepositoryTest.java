package pl.com.bottega.photostock.sales.infrastructure.repositories;


import pl.com.bottega.photostock.sales.infrastructure.repositories.CSVProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.InMemoryClientRepository;
import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ProductRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVProductRepositoryTest {

//    public static void main(String[] args) {
//
//        //2,pieski;małpki;chomiki,20,true,null,null
//
//        Set<String> tags = new HashSet<>();
//        tags.add("okno");
//        tags.add("komin");
//        tags.add("drzwi");
//        String[] tagsTab = tags.toArray(new String[tags.size()]);
//
//        Client client = new VIPClient("Barbara Barbara", new Address("ul. Zachodnia 11", "Polska", "Lublin", "20-001"), ClientStatus.VIP, Money.valueOf(50), Money.valueOf(50));
//
//        Product product = new Picture(5L, tagsTab, Money.valueOf(10), null, null, true);
//
//        ProductRepository productRepository = new CSVProductRepository(
//                "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\products.csv",
//                new InMemoryClientRepository());
//
//        //test save()
//        productRepository.save(product);
//
//        //test find()
//        Set<String> tag = new HashSet<>();
//        tag.add("ptaszki");
//        List<Product> list = productRepository.find(client, tag, null, null);
//        for (Product prod: list) {
//            System.out.println(prod.getNumber());
//        }
//        System.out.println(list.size());
//
//        //test get()
//        Product prod = productRepository.get(1L);
//
//        Picture picture = (Picture) prod;
//        Set <String> set = picture.getTags();
//        for (String tag: set) {
//            System.out.println(tag);
//        }
//        System.out.println(prod.getNumber());
//    }

}