package sockets;

import Abstracts.ASocketModelsSerializable;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler implements Runnable {
    public static final List<ServerHandler> runningServersHandler = new ArrayList<>();
    public static final List<Party> Parties = new ArrayList<>();

    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final String me = null;
    private final Party selectedParty = null;

    public ServerHandler(Socket clientSocket) throws IOException {
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
            } catch (IOException ignored) {
            }
        }
    }

    public void actionDispatcher(Integer action) throws IOException {
        System.out.println("receive action : " + action);
        switch (action) {
        }
    }

    private synchronized void broadcastModel(ASocketModelsSerializable<Object> model) throws IOException {
        System.out.println("broadcast model process");

        for (ServerHandler runningServer : runningServersHandler) {
            if (selectedParty == null || (selectedParty != null && selectedParty.equals(runningServer.selectedParty))) {
                model.serialize(runningServer.writer, true);
            }
        }
    }

    private void removeServer() {
        runningServersHandler.remove(this);
    }
}
