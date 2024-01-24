package lk.ijse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket server;
    public static final int PORT = 3030;
    private static int index = 0;
    private static List<ClientHandler> clients = new ArrayList<>();

    public void startServer(){
        try{
            server = new ServerSocket(PORT);
            do { iniConnections();
            }while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniConnections() throws IOException {
        Socket clientSocket = server.accept();

        if(clientSocket.isConnected()){
            new Thread(()->{
                index++;
                ClientHandler client = new ClientHandler(clientSocket,index);
                clients.add(client);
                client.readMessages();
                client.close();
            }).start();
        }
    }

    public static void broadcastMessage(String message, int senderId) {
        for (ClientHandler client : clients) {
            if (client.getId() != senderId) {
                client.sendMessage(message);
            }
        }
    }
}

