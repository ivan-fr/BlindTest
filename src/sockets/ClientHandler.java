package sockets;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler {
    private final Socket clientSocket;
    private final Socket broadcastSocket;

    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final BufferedReader readerBroadcast;
    private final BufferedWriter writerBroadcast;

    public ClientHandler(Socket clientSocket, Socket broadCastSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        this.broadcastSocket = broadCastSocket;
        this.writerBroadcast = new BufferedWriter(new OutputStreamWriter(broadCastSocket.getOutputStream()));
        this.readerBroadcast = new BufferedReader(new InputStreamReader(broadCastSocket.getInputStream()));

        new Thread(() -> {
            while (broadCastSocket.isConnected()) {
                try {
                    int choose = reader.read();
                    actionDispatcher(choose);
                } catch (IOException ignored) {
                }
            }
        }).start();
    }

    public boolean sendAction(Integer action, Integer dataInteger, String dataString) throws IOException {
        if (!clientSocket.isConnected()) {
            return false;
        }

        boolean first = true;
        boolean defer = true;
        do {
            if (!first) {
                System.out.println("Wrong server id");
            }

            System.out.println("Give a server id");

            writer.write(action);
            writer.flush();

            if (reader.read() == 0) {
                first = false;
                continue;
            }

            if (dataInteger == null) {
                writer.write(dataString);
            } else {
                writer.write(dataInteger);
            }
            writer.flush();

            if (reader.read() == 1) {
                break;
            }

            first = false;
        } while (true);

        return defer;
    }

    public void actionDispatcher(Integer action) throws IOException {
        switch (action) {
        }
    }
}
