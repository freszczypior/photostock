package pl.com.bottega.photostock.sales.misc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FileServer {

    public static void main(String[] args) throws IOException {
        new FileServer().work();
    }
    private void work() throws IOException {

        ServerSocket serverSocket = new ServerSocket(6662);
        BlockingQueue<Socket> sockets = new LinkedBlockingQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            sockets.add(clientSocket);
            executorService.submit(new Client(sockets.poll()));

        }
    }
    public class Client implements Runnable {

        private Socket clientSocket;

        public Client(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try {
                String enquiry = getEnquiry();

                String command = getCommand(enquiry);
                String filePath = getPath(enquiry);

                if (command.equals("GET")) {
                    sendReply(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getEnquiry() throws IOException {
            InputStream is = clientSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            return br.readLine();
        }

        private String getCommand(String enquiry){
            String [] splitLine = enquiry.trim().split(" +");
            return splitLine[0].toUpperCase();
        }
        private String getPath(String enquiry){
            String [] splitLine = enquiry.trim().split(" +");
            return splitLine[1].toLowerCase();
        }

        private void sendReply(String path) throws IOException {
            File file = new File(path);
            OutputStream os = clientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            if (!file.exists()) {
                pw.println("ERROR No such file\n");
            }
            if (file.isDirectory()) {
                pw.println("ERROR File is a directory\n");
            }
            if (file.isFile()) {
                InputStream is2 = new FileInputStream(path);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                String line;
                while ((line = br2.readLine()) != null) {
                    pw.println(line);
                }
            }
            pw.flush();
        }
    }

}
