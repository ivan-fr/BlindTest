package sockets;

import Abstracts.ASocketModelSerializable;
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

    private User me;

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
                    int choose = readerBroadcast.read();
                    actionDispatcher(choose);
                } catch (IOException ignored) {
                }
            }
        }).start();
    }

    public User getMe() {
        return me;
    }

    public boolean sendAction(EnumSocketAction action, ASocketModelSerializable model) throws IOException {
        if (!clientSocket.isConnected()) {
            return false;
        }

        writer.write(action.ordinal());

        if (model == null) {
            writer.flush();
        } else {
            model.serialize(writer, true);
        }

        int response = reader.read();
        return response == 1;
    }

    public boolean signUp(String username, String password) throws IOException {
        return sendAction(EnumSocketAction.SIGNUP, new User(username, password));
    }

    public boolean signOut() throws IOException {
        if (sendAction(EnumSocketAction.SIGNOUT, null)) {
            me = null;
            return true;
        }

        return false;
    }

    public boolean signIn(String username, String password) throws IOException {
        if (sendAction(EnumSocketAction.SIGNIN, new User(username, password))) {
            me = User.deserialize(reader);
            return true;
        }

        return false;
    }

    public void actionDispatcher(Integer action) throws IOException {

    }
}
