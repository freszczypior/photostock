package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;

import java.util.Optional;

public class CSVClientRepository implements ClientRepository {

    private static String path;


    @Override
    public Client get(String number) { //musi pobrać numer klienta

        return null;
    }

    @Override
    public void save(Client client) { //zapisać klienta

    }

    @Override
    public Optional<Client> getByLogin(String login) {  // box dla klienta
        Optional<Client> optional;

        return null;
    }
}
