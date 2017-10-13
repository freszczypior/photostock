package pl.com.bottega.photostock.sales.misc;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FileClient {

    private static final String HOST = "localhost";
    private static final int PORT = 6662;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

//        String remoteFile = getRemoteFile(scanner);
//        String localFile = getLocalFile(scanner);
        String remoteFile = "D:\\server.txt";
        String localFile = "D:\\client.txt";

        try {
            Socket socket = new Socket(HOST, PORT);
            sendToServer(remoteFile, socket);
            retrieveFromServer(localFile, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void retrieveFromServer(String localFile, Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        OutputStream os = new FileOutputStream(localFile, true);
        PrintStream ps = new PrintStream(os);

        String line;
        while ((line = br.readLine()) != null){
            ps.println(line);
        }
    }

    private static void sendToServer(String remoteFile, Socket socket) throws IOException {
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.printf("GET %s\n", remoteFile);
        pw.flush();
    }

//    private static String getLocalFile(Scanner scanner) {
//        System.out.print("Input remote file path: ");
//        return scanner.nextLine();
//    }
//
//    private static String getRemoteFile(Scanner scanner) {
//        System.out.print("Input local file path: ");
//        return scanner.nextLine();
//    }
}
