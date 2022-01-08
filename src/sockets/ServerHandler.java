package sockets;

import Abstracts.ASocketModelSerializable;
import composite.CompositeThemeSingleton;
import composite.CompositeUserSingleton;
import models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler implements Runnable {
    public static final List<ServerHandler> serversHandler = new ArrayList<>();
    public static final List<Party> parties = new ArrayList<>();

    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final Socket broadcastClient;
    private final BufferedReader readerBroadcast;
    private final BufferedWriter writerBroadcast;

    private String me = null;
    private Party selectedParty = null;

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
    public synchronized void run() {
        while (clientSocket.isConnected()) {
            try {
                int action = reader.read();
                if (action == -1) {
                    clientSocket.close();
                    broadcastClient.close();
                    serversHandler.remove(this);
                    break;
                }
                actionDispatcher(action);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionDispatcher(Integer action) throws IOException, SQLException {
        System.out.println("receive action : " + action);
        if (EnumSocketAction.SIGNUP.ordinal() == action) {
            signUp();
        } else if (EnumSocketAction.SIGNIN.ordinal() == action) {
            signIn();
        } else if (EnumSocketAction.SIGNOUT.ordinal() == action) {
            signOut();
        } else if (EnumSocketAction.GET_THEMES.ordinal() == action) {
            getThemes();
        } else if (EnumSocketAction.ADD_PARTY.ordinal() == action) {
            add_party();
        }
    }

    public synchronized void add_party() throws IOException, SQLException {
        Party p = Party.deserialize(reader);

        if (!CompositeUserSingleton.compositeUserSingleton.get(p.getAuthorKey()).equals(CompositeUserSingleton.compositeUserSingleton.get(me))) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        parties.add(p);
        broadcastModelArray(EnumSocketAction.ADD_PARTY, parties);
        writer.write(1);
        System.out.println("send 1");
        writer.flush();
    }

    public void getThemes() throws IOException {
        if (me == null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        broadcastModelArray(EnumSocketAction.GET_THEMES, CompositeThemeSingleton.compositeThemeSingleton.list());
        writer.write(1);
        System.out.println("send 1");
        writer.flush();
    }

    public synchronized void signOut() throws IOException {
        if (me == null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        me = null;
        serversHandler.remove(this);

        writer.write(1);
        System.out.println("send 1");
        writer.flush();
    }

    public void signUp() throws IOException, SQLException {
        User newUser = CompositeUserSingleton.compositeUserSingleton.save(User.deserialize(reader));

        if (newUser == null) {
            writer.write(0);
            System.out.println("send 0");
        } else {
            writer.write(1);
            System.out.println("send 1");
        }
        writer.flush();
    }

    public synchronized void signIn() throws IOException, SQLException {
        User targetFromClient = User.deserialize(reader);
        User userExist = CompositeUserSingleton.compositeUserSingleton.get((String) targetFromClient.getKey());

        if (userExist != null && targetFromClient.getPassword().contentEquals(userExist.getPassword())) {
            writer.write(1);
            userExist.serialize(writer, true);
            me = (String) userExist.getKey();
            serversHandler.add(this);
            return;
        }

        writer.write(0);
        System.out.println("send 0");
        writer.flush();
    }

    private synchronized<T extends ASocketModelSerializable<T>>  void broadcastModel(EnumSocketAction action, T model) throws IOException {
        System.out.println("broadcast model process");

        for (ServerHandler runningServer : serversHandler) {
            if (selectedParty == null || (selectedParty != null && selectedParty.equals(runningServer.selectedParty))) {
                runningServer.writerBroadcast.write(action.ordinal());
                model.serialize(runningServer.writerBroadcast, true);
            }
        }
    }

    private synchronized <T extends ASocketModelSerializable<T>> void broadcastModelArray(EnumSocketAction action, List<T> models) throws IOException {
        System.out.println("broadcast array model process");

        for (ServerHandler runningServer : serversHandler) {
            if (selectedParty == null || (selectedParty != null && selectedParty.equals(runningServer.selectedParty))) {
                runningServer.writerBroadcast.write(action.ordinal());
                runningServer.writerBroadcast.write(models.size());
                runningServer.writerBroadcast.flush();

                try {
                    for (T model :
                            models) {
                        model.serialize(runningServer.writerBroadcast, false);
                    }
                    runningServer.writerBroadcast.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void removeServer() {
        serversHandler.remove(this);
    }
}
