package pl.com.bottega.photostock.sales.misc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MapClass {


    public static void main(String[] args) {
        writeTofile("D:/ala.txt", "Ala ma kota");
        writeTofile("D:/ala.txt", "Ala nie ma kota");
        writeTofile("D:/ala.txt", "Ala nie ma na imię Janusz");
        saveMapToFile(readFileAndCreateMap("D:/ala.txt"), "D:/map.txt");
    }

    private static void writeTofile(String path, String text) {
        try (OutputStream outStream = new FileOutputStream(path, true);
             PrintStream printStream = new PrintStream(outStream)) {
            printStream.println(text);
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się zapisac pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wyjścia ");
        }
    }

    public static Map<String, Integer> readFileAndCreateMap(String path) {
        Map<String, Integer> map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1250"))){
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "CP1250"));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                for (String word : line.split("\\s"))
                    addToMap(map, word);
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wejścia");
        }
        return map;
    }

    private static void addToMap(Map<String, Integer> map, String word) {
        if (map.get(word) == null)
            map.put(word, 1);
        else
            map.put(word, map.get(word) + 1);
    }

    public static void saveMapToFile(Map<String, Integer> map, String path) {
        if (!map.isEmpty())
            map.entrySet().forEach((entry) -> writeTofile(path, String.format("Klucz: %s, wartość: %d", entry.getKey(), entry.getValue())));
//            for (Map.Entry<String, Integer> entry : map.entrySet())
//                writeTofile(path, String.format("Klucz: %s, wartość: %d", entry.getKey(), entry.getValue()));
        else
            throw new IllegalStateException("Mapa jest pusta");
    }
}
