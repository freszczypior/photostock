package pl.com.bottega.photostock.sales.misc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IOPlay {

    static class Person implements Serializable{
        private int age;
        private String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    public static void main(String[] args) {
//        basicRead();
//        System.out.println("\n-------------------");
//        basicReadTryWithResources();
//        System.out.println("\n-------------------");
//        characterRead();
//        System.out.println("\n-------------------");
//        bufferedRead();
//        basicWrite();
//        writer();
//        printWriter();
        writeObjects();
        readObjects();
    }

    public static void readObjects() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:/temp.bin"))) {
            Object o;
            while ((o = ois.readObject()) != null){
                Person p = (Person) o;
                System.out.println(String.format("%s, %d", p.name, p.age));
                System.out.println();
            }
        }catch (FileNotFoundException fnfw){
            System.out.println("Nie ma pliku");
        }catch (EOFException eofe){
            System.out.println("Przetwarzanie zakończone");
        }catch (IOException ioe){
            System.out.println("Błąd wejścia");
        } catch (ClassNotFoundException cnfe){
            System.out.println("Klasa nie znaleziona");
        }
    }



    public static void writeObjects() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/temp.bin"))) {
            oos.writeObject(new Person(17, "Jan Nowak"));
            oos.writeObject(new Person(33, "Jan Kowal"));
            oos.writeObject(new Person(20, "Marek Marek"));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void printWriter() {
        try (OutputStream outStream = new FileOutputStream("D:/output.txt", true);
             PrintStream printStream = new PrintStream(outStream)) {
            printStream.println("Tyś coś innegoź");
            printStream.println("Tyś coś innegoź, ponownie to samo");
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się zapisac pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wyjścia ");
        }
    }

    public static void writer() {
        try (OutputStream outStream = new FileOutputStream("D:/output.txt", true);
             Writer writer = new OutputStreamWriter(outStream, "CP1250")) {
            writer.write("Tyś coś innegoź\r\n");        //powrót karetki, kursora
            writer.write("Tyś coś innegoź, ponownie to samo");
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się zapisac pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wyjścia ");
        }
    }

    private static void basicWrite() {
        try (OutputStream outStream = new FileOutputStream("D:/output.txt")) {
            outStream.write("Tyś coś innegoź".getBytes("CP1250"));
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się zapisac pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wyjścia ");
        }
    }

    private static void bufferedRead() {
        try (InputStream inputStream = new FileInputStream("D:/temp.txt")) {  // inne nawiasy, po tym wykona finally z close()
            InputStreamReader reader = new InputStreamReader(inputStream, "CP1250"); // CP1250 sposób kodowania windowsa
            BufferedReader bufferedReader = new BufferedReader(reader);
            System.out.println(reader.getEncoding());
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wejścia");
        }
    }

    private static void characterRead() {
        try (InputStream inputStream = new FileInputStream("D:/temp.txt")) {  // inne nawiasy, po tym wykona finally z close()
            InputStreamReader reader = new InputStreamReader(inputStream, "CP1250"); // CP1250 sposób kodowania windowsa
            System.out.println(reader.getEncoding());
            int b;
            while ((b = reader.read()) != -1) {
                System.out.print((char) b);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wejścia");
        }
    }

    private static void basicReadTryWithResources() {
        try (InputStream inputStream = new FileInputStream("D:/temp.txt")) {  // inne nawiasy, po tym wykona finally z close()
            int b;
            while ((b = inputStream.read()) != -1) {
                System.out.print((char) b);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wejścia");
        }
    }

    private static void basicRead() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("D:/temp.txt");
            int b;
            while ((b = inputStream.read()) != -1) {
                System.out.print((char) b);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie ma pliku");
        } catch (IOException ex) {
            System.out.println("Błąd wejścia");
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("błąd zamknięcia");
                }
        }
    }
}
