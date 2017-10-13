package pl.com.bottega.photostock.sales.misc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducentConsumerProblem {


    static class Producent implements Runnable{

        Warehouse warehouse;

        public Producent(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        @Override
        public void run() {
            while (true){
                try {
                    // random zwraca rzeczywistą,
                    // my potrzebujemy całkowitej, dlatego 10000, a do tego z racji,
                    // że random może zwrócić 0 to dodajemy 1 sek czyli 1000ms
                    Thread.sleep(1000 + ((long) Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String product = String.valueOf((int) (Math.random() * 100));
                System.out.println("Wyprodukowałem: "+product);
                warehouse.put(product);

            }
        }
    }
//      niskopoziomowo
//    static class Warehouse{
//        private Queue<String> products = new LinkedList<>();
//
//        public synchronized void put(String product) {
//            products.add(product);
//            notify();
//        }
//
//        public synchronized String take() {
//            if (products.isEmpty())
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            String product = products.poll();
//
//            return product;
//        }
//    }

    // z wykorzystaniem blokowania
    static class Warehouse{
        private BlockingQueue<String> products = new LinkedBlockingQueue<>();

        public  void put(String product) {
            products.add(product);
        }

        public String take() {
            try {
                return products.take();
            } catch (InterruptedException e) {
                return take();
            }
        }
    }

    static class Consumer implements Runnable{
        Warehouse warehouse;

        public Consumer(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        @Override
        public void run() {
            while (true){
                String product = warehouse.take();
                try {
                    Thread.sleep(1000 + ((long) Math.random() * 2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Skonsumowałem: "+product);
            }
        }
    }

    private static final int PRODUCENT_COUNT = 10;
    private static final int CONSUMER_COUNT = 5;

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        for (int i = 0; i < PRODUCENT_COUNT; i++){
            new Thread(new Producent(warehouse)).start();
        }
        for (int i = 0; i < CONSUMER_COUNT; i++){
            new Thread(new Consumer(warehouse)).start();
        }
    }


}
