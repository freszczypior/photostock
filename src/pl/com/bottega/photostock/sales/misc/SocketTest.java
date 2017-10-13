package pl.com.bottega.photostock.sales.misc;

import java.io.*;
import java.net.Socket;

public class SocketTest {

//    public static void main(String[] args) throws IOException {
//        Socket socket = new Socket("www.pollub.pl", 80);  //dla http jest 80, dla https jest 443
//
//        OutputStream os = socket.getOutputStream();
//        PrintWriter pw = new PrintWriter(os);
//        pw.println("GET / HTTP/1.1");
//        pw.println("Host: www.pollub.pl");
//        pw.println("\r\n");
//        pw.flush();
//
//        InputStream is = socket.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        String line;
//        while ((line = br.readLine()) != null){
//            System.out.println(line);
//        }
//    }

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("www.pollub.pl", 80);

        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.println("GET / HTTP/1.1");
        pw.println("Host: www.pollub.pl");
        pw.println("\r\n");
        pw.flush();

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        String line;
        while ((line = bf.readLine()) != null){
            System.out.println(line);
        }

    }


}
