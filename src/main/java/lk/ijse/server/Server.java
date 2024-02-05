package lk.ijse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static Server server;
    private ServerSocket serverSocket;
    private int index = 0;
    private final List<ClientHandler> clients = new ArrayList<>();


    private Server(){
        try {
            serverSocket = new ServerSocket(3030);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Server getInstance(){
        return (server==null)?server=new Server():server;
    }


    public void startServer(){
        try{
            do {
                connectionInitializer();
            }while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectionInitializer() throws IOException {
        Socket clientSocket = serverSocket.accept();
        if(clientSocket.isConnected()){
            Runnable runnable = ()->{
                index++;
                ClientHandler client = new ClientHandler(clientSocket,index);
                clients.add(client);
                client.readMessages();
                client.close();
            };
            Thread thread = new Thread(runnable);
            thread.start();
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

