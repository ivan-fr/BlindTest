package sockets;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
