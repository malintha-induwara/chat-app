package lk.ijse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static Server server;
    private ServerSocket serverSocket;
    public  final int PORT = 3030;
    private int index = 0;
    private final List<ClientHandler> clients = new ArrayList<>();


    private Server(){
    }

    public static Server getInstance(){
        return (server==null)?server=new Server():server;
    }


    public void startServer(){
        try{
            serverSocket = new ServerSocket(PORT);
            do { iniConnections();
            }while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniConnections() throws IOException {
        Socket clientSocket = serverSocket.accept();

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

    public  void broadcastMessage(String message, int senderId) {
        for (ClientHandler client : clients) {
            if (client.getId() != senderId) {
                client.sendMessage(message);
            }
        }
    }
}

