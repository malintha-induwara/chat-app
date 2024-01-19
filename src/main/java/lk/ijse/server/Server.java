package lk.ijse.server;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import lk.ijse.chatApp.util.UserCountUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket server;
    public static final int PORT = 3030;
    private static int index = 0;
    private static List<ConnectedClient> clients = new ArrayList<>();

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
                ConnectedClient client = new ConnectedClient(clientSocket,index);
                clients.add(client);
                client.readMessages();
                client.close();
            }).start();
        }
    }

    public static void broadcastMessage(String message, int senderId) {
        for (ConnectedClient client : clients) {
            if (client.getId() != senderId) {
                client.sendMessage(message);
            }
        }
    }
}

