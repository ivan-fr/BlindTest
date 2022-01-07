package sockets;

import Abstracts.ASocketModelsSerializable;
import composite.CompositeUserSingleton;
import models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler implements Runnable {
    public static final List<ServerHandler> runningServersHandler = new ArrayList<>();
    public static final List<Party> Parties = new ArrayList<>();

    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final Socket broadcastClient;
    private final BufferedReader readerBroadcast;
    private final BufferedWriter writerBroadcast;

    private final String me = null;
    private final Party selectedParty = null;

    public ServerHandler(ServerSocket serverSocket, Socket clientSocket) throws IOException {
        this.broadcastClient = serverSocket.accept();
        this.writerBroadcast = new BufferedWriter(new OutputStreamWriter(this.broadcastClient.getOutputStream()));
        this.readerBroadcast = new BufferedReader(new InputStreamReader(this.broadcastClient.getInputStream()));
        System.out.println("Broadcast client connected.");

        this.clientSocket = clientSocket;
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                int action = reader.read();
                actionDispatcher(action);
            } catch (IOException | SQLException ignored) {
            }
        }
    }

    public void actionDispatcher(Integer action) throws IOException, SQLException {
        System.out.println("receive action : " + action);
        if (EnumSocketAction.SIGNUP.ordinal() == action) {
            signUp();
        }
    }

    public void signUp() throws IOException, SQLException {
        User newUser = CompositeUserSingleton.compositeUserSingleton.save(new User(reader.readLine(), reader.readLine()));

        if (newUser != null) {
            writer.write(1);
        } else {
            writer.write(0);
        }
    }

    private synchronized void broadcastModel(ASocketModelsSerializable<Object> model) throws IOException {
        System.out.println("broadcast model process");

        for (ServerHandler runningServer : runningServersHandler) {
            if (selectedParty == null || (selectedParty != null && selectedParty.equals(runningServer.selectedParty))) {
                model.serialize(runningServer.writerBroadcast, true);
            }
        }
    }

    private void removeServer() {
        runningServersHandler.remove(this);
    }
}
