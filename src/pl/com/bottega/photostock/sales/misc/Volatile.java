package pl.com.bottega.photostock.sales.misc;

import java.util.Scanner;

//Klasa tworzy nowy wątek klasy Dog o nazwie azor i wykonuje
//        pętle wypisującą „Bork!” na ekran. W klasie Dog, aby zaznać chwilę spokoju
//        od jakże uroczego szczekającego psa, znajduje się również metoda shhhhh()
//        która umożliwia nam zatrzymanie działania pętli szczekania.
//        Zmienna canDogBork została zadeklarowana, jakovolatile, gdyż w przypadku,
//        gdy główny wątek wywołałby metodę shhhhh() pętla mogłaby się nie zatrzymać.
//        Spowodowane jest to tym, iż wątek Dog może mieć zapisaną w swojej „pamięci”
//        kopie zmiennej canDogBork o wartości true  – nawet w sytuacji,
//        w której zmienna zostałaby zmieniona przez inny wątek pętla mogłaby dalej działać.
//        Dzięki zadeklarowaniu zmiennej, jako volatile zapis odbywa się przed odczytem
//        i pętla while przed odczytem sprawdza czy wartość zmiennej canDogBork uległa
//        zmianie.

public class Volatile {

    private static Scanner scanner;

    static class Dog extends Thread{

        private volatile Boolean canDogBork = true;

        public void run(){

            while (canDogBork){
                System.out.println("Bork!!");

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shhhhh(){
            canDogBork = false;
        }
    }

    public static void main(String[] args) {

        Dog azor = new Dog();
        azor.start();

        System.out.println("Press enter to stop the borking!!");
        scanner = new Scanner(System.in);
        scanner.nextLine();

        azor.shhhhh();
    }

}
