package pl.com.bottega.photostock.sales.misc;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 6661;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj swoje imię: ");
        String msg = scanner.nextLine();
        try {
            Socket socket = new Socket(HOST, PORT);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.println(msg);
            pw.flush();

            new Thread(() -> {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                try {
                    while (!(line = br.readLine()).trim().equals(""))
                        System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                msg = scanner.nextLine();
                if (msg.trim() != "") {
                    pw.println(msg);
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}