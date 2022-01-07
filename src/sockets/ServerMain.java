package sockets;

import composite.CompositeFichierSingleton;
import composite.CompositeReponseSingleton;
import composite.CompositeThemeSingleton;
import composite.CompositeUserSingleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerMain {
    private final static int PORT = 1500;
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Mysql implementation is OK.");
        } catch (Exception ex) {
            System.out.println("Mysql implementation error.");
        }


        try {
            CompositeUserSingleton.compositeUserSingleton.hydrate();
            CompositeFichierSingleton.compositeFichierSingleton.hydrate();
            CompositeReponseSingleton.compositeReponseSingleton.hydrate();
            CompositeThemeSingleton.compositeThemeSingleton.hydrate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Waiting connection...");
        waitConnection();
    }

    public static void waitConnection() {
        try {
            while(!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("A client has joined");
                ServerHandler serverHandler = new ServerHandler(serverSocket, clientSocket);
                Thread thread = new Thread(serverHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public static void closeServerSocket() {
        try {
            if (serverSocket!=null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
