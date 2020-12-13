package com.company.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    private boolean isStarted;
    Users users = new Users();
    private int port = 3345;

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        isStarted = true;
    }

    public void accept() throws IOException {
        while (true) {
            if (isStarted) {
                Socket socket = serverSocket.accept();
                new MonoServer(socket).start();
            }
        }
    }

    public void sendToAllUsers(String message) throws IOException {
        for (Map.Entry<Integer, Connection> user : users.getUsers().entrySet()) {
                user.getValue().send(message);
        }
    }

    class MonoServer extends Thread {
        private final Socket socket;

        MonoServer(Socket socket) {
            this.socket = socket;
        }

        private void sendRequest(Connection connection) throws IOException {
            while (true) {
                String string = connection.receive();
                System.out.println("В отправке");
                sendToAllUsers(string);
            }
        }

        @Override
        public void run() {
            try {
                Connection connection = new Connection(socket);
                System.out.println("В потоке");
                users.addNewUser(connection);
                sendRequest(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (RuntimeException ex){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // рантайм на случай если нет мест
        }
    }
}
