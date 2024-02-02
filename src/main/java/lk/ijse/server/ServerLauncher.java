package lk.ijse.server;

public class ServerLauncher {
    public static void main(String[] args) {

        Server server = Server.getInstance();
        server.startServer();

    }
}

