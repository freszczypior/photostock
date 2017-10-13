package pl.com.bottega.photostock.sales.misc;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class ChatServer {


    public static void main(String[] args) throws IOException {
        new ChatServer().work();

    }
    private List<Client> clients = new Vector<>();  // vector jest bezpieczny wątkowo

    public void work() throws IOException {

        ServerSocket serverSocket = new ServerSocket(6661);
        while (true){
            Socket clientSocket = serverSocket.accept();
            Client client = new Client(this, clientSocket.getInputStream(), clientSocket.getOutputStream());
            clients.add(client);
            new Thread(client).start();

        }
    }

    static class Client implements Runnable{

        private ChatServer server;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;
        private String name;

        public Client(ChatServer server, InputStream inputStream, OutputStream outputStream){
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            this.server = server;
            this.printWriter = new PrintWriter(outputStream);
        }

        @Override
        public void run() {
            try {
                name = bufferedReader.readLine();
                System.out.println(name);
                if (name == null) {
                    server.clientDisconnected(this);
                    return;
                }
                while (true){
                    String msg = bufferedReader.readLine();
                    if (msg == null) {
                        server.clientDisconnected(this);
                        return;
                    }
                    server.newMessage(msg, this);

                }
            } catch (IOException e) {
                server.clientDisconnected(this);
            }
        }

        public void sendMessage(String msg, Client author) {
            try {
                printWriter.println(author.name + " > " + msg);
                printWriter.flush();
            }catch (Exception ex){

            }
        }
    }

    private void clientDisconnected(Client client) {
        this.clients.remove(client);
    }

    private void newMessage(String msg, Client author) {
        for (Client otherClient: clients) {
            if (author != otherClient){
                otherClient.sendMessage(msg, author);

            }
        }
    }

}
