package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.repositories.ClientRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class CSVClientRepository implements ClientRepository {

    private String path;

    public CSVClientRepository(String path) {
        this.path = path;
    }

    @Override
    public Client get(String clientNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                if (lineSplit[0].equals(clientNumber))
                    return toClient(lineSplit);
            }
            throw new IllegalArgumentException("No such object in repository");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Client toClient(String[] lineSplit) {
        String number = lineSplit[0];
        String name = lineSplit[1];
        String adressLine1 = lineSplit[2];
        String adressLine2 = lineSplit[3];
        String country = lineSplit[4];
        String city = lineSplit[5];
        String postalCode = lineSplit[6];
        ClientStatus clientStatus = toClientStatus(lineSplit[7]);
        String pathToTransactions = lineSplit[8];
        if (clientStatus == ClientStatus.VIP) {
            Money creditLimit = Money.valueOf(Integer.parseInt(lineSplit[9]));
            return new VIPClient(number, name, toAddress(adressLine1, adressLine2, country, city, postalCode), toTransactionsList(pathToTransactions), creditLimit);
        } else {
            return new StandardClient(number, name, toAddress(adressLine1, adressLine2, country, city, postalCode),
                    clientStatus, toTransactionsList(pathToTransactions));
        }
    }

    private List<Transaction> toTransactionsList(String clientNumber) { //TODO a może oddzielne repo na trans?
        String clientTransPath = String.format("C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\%s-transactions.csv", clientNumber);
        List<Transaction> list = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(clientTransPath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                Money amount = Money.valueOf(Integer.parseInt(lineSplit[0]));
                String description = lineSplit[1];
                Integer year = Integer.parseInt(lineSplit[2]);
                Integer month = Integer.parseInt(lineSplit[3]);
                Integer day = Integer.parseInt(lineSplit[4]);
                Integer hour = Integer.parseInt(lineSplit[5]);
                Integer minute = Integer.parseInt(lineSplit[6]);
                Transaction trans = new Transaction(amount, description,
                        toLocalDateTime(year, month, day, hour, minute));
                list.add(trans);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private LocalDateTime toLocalDateTime(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private ClientStatus toClientStatus(String clientStatus) {
        switch (clientStatus.toUpperCase()) {
            case "STANDARD":
                return ClientStatus.STANDARD;
            case "VIP":
                return ClientStatus.VIP;
            case "SILVER":
                return ClientStatus.SILVER;
            case "GOLD":
                return ClientStatus.GOLD;
            case "PLATINIUM":
                return ClientStatus.PLATINIUM;
            default:
                throw new IllegalArgumentException("Unknow client status");
        }
    }

    private Address toAddress(String adressLine1, String adressLine2, String country, String city, String postalCode) {
        return new Address(adressLine1, adressLine2, country, city, postalCode);
    }

    @Override
    public void save(Client client) {
        Map<String, Client> map = new HashMap<>();
        toMap(path, map);
        map.put(client.getNumber(), client);
        toFile(map, path, false);
    }

    private void toFile(Map<String, Client> map, String path, boolean append) {
        try (OutputStream os = new FileOutputStream(path, false)) {
            PrintStream ps = new PrintStream(os);
            Client tempClient = null;
            for (Map.Entry<String, Client> entry : map.entrySet()) {
                tempClient = entry.getValue();
                if (tempClient instanceof VIPClient) {
                    String creditLimit = ((VIPClient) tempClient).getCreditLimit().toString();
                   ps.println(String.format("%s;%s", toLine(entry.getKey(), entry.getValue()),creditLimit));
                } else {
                    ps.println(toLine(entry.getKey(), entry.getValue()));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toLine(String number, Client client) {
        String name = client.getName();
        String line1 = client.getAddress().getLine1();
        String line2 = client.getAddress().getLine2();
        String country = client.getAddress().getCountry();
        String city = client.getAddress().getCity();
        String postalCode = client.getAddress().getPostalCode();
        String status = client.getStatus().toString();
        List<Transaction> transList = client.getTransactions();
        String clientTransPath = String.format( //TODO a może oddzielne repo na trans?
                "C:\\Users\\freszczypior\\IdeaProjects\\photostock-summer-2017-master\\repo\\%s-transactions.csv", number);
        toTransactionsFile(transList, clientTransPath);
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;",
                number, name, line1, line2, country, city, postalCode, status, clientTransPath);
    }

    private void toTransactionsFile(List<Transaction> transList, String path) {
        try(OutputStream os = new FileOutputStream(path, false)){
            PrintStream ps = new PrintStream(os);
            for (Transaction trans: transList) {
                String amount = trans.getAmount().toString();
                String description = trans.getDescription();
                String line = String.format("%s;%s;%s", amount, description, localDateTimeToString(trans.getDateTime()));
                ps.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String localDateTimeToString (LocalDateTime localDateTime){
        Integer year = localDateTime.getYear();
        Integer month = localDateTime.getMonth().getValue();
        Integer day = localDateTime.getDayOfMonth();
        Integer hour = localDateTime.getHour();
        Integer minute = localDateTime.getMinute();
        return String.format("%d;%d;%d;%d;%d", year, month, day, hour, minute);
    }
    private void toMap(String path, Map<String, Client> map) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                map.put(toClient(lineSplit).getNumber(), toClient(lineSplit));
            }
            throw new IllegalArgumentException("No such object in repository");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> getByLogin(String login) {  // box dla klienta
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(";");
                if (lineSplit[1].equals(login))
                    return Optional.of(toClient(lineSplit));
            }
            throw new IllegalArgumentException("No such object in repository");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No such object in repository");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
