package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryClientRepository implements ClientRepository {

    private static final Map<String, Client> REPO;

    static {        //statyczny blok inicjalizujący
        REPO = new HashMap<>();
        Client client1 = new StandardClient("Jan Nowak", new Address("ul. Północna 11", "Polska", "Lublin", "20-001"));
        Client client2 = new StandardClient("Marek Marek", new Address("ul. Wschodnia 11", "Polska", "Lublin", "20-001"));
        Client client3 = new VIPClient("Barbara Barbara", new Address("ul. Zachodnia 11", "Polska", "Lublin", "20-001"));
        Client client4 = new VIPClient("Anna Anna", new Address("ul. Południowa 11", "Polska", "Lublin", "20-001"));
        REPO.put(client1.getNumber(), client1);
        REPO.put(client2.getNumber(), client2);
        REPO.put(client3.getNumber(), client3);
        REPO.put(client4.getNumber(), client4);
    }

    @Override
    public Client get(String number) {
        if (!REPO.containsKey(number))
            throw new IllegalArgumentException(String.format("No such Client %s in repository", number));
        return REPO.get(number);
    }
    @Override
    public void save(Client client) {
        REPO.put(client.getNumber(), client);
    }

    @Override
    public Optional<Client> getByLogin(String login) {
        for (Client client : REPO.values()) {
            if (client.hasLogin(login))
                return Optional.of(client);
        }
        return Optional.empty();
    }
}
