package sockets;

import models.User;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private final Socket clientSocket;
    private final Socket broadcastSocket;

    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final BufferedReader readerBroadcast;
    private final BufferedWriter writerBroadcast;

    private String username;

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

    public boolean sendAction(EnumSocketAction action, Integer[] dataInteger, String[] dataString) throws IOException {
        if (!clientSocket.isConnected()) {
            return false;
        }

        writer.write(action.ordinal());
        if (dataInteger == null) {
            for (String data:
                 dataString) {
                writer.write(data);
                writer.newLine();
            }
        } else {
            for (Integer data:
                    dataInteger) {
                writer.write(data);
            }
        }
        writer.flush();

        return reader.read() == 1;
    }

    public boolean signUp(String username, String password) throws IOException {
        return sendAction(EnumSocketAction.SIGNUP, null, new String[]{username, password});
    }

    public void actionDispatcher(Integer action) throws IOException {

    }
}
