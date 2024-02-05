package lk.ijse.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private final int id;

    public ClientHandler(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.id = id;
        try {
            this.inputStream = new DataInputStream(clientSocket.getInputStream());
            this.outputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessages(){
        String message = "";
        while(!message.equals("end")){
            try {
                message = inputStream.readUTF();
                Server.getInstance().broadcastMessage(message, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try{
            clientSocket.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}

