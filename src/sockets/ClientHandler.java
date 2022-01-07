package sockets;

import Abstracts.ASocketModelSerializable;
import jFrame.MainFrame;
import models.Theme;
import models.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    private final Socket clientSocket;
    private final Socket broadcastSocket;

    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final BufferedReader readerBroadcast;
    private final BufferedWriter writerBroadcast;

    private MainFrame mainFrame = null;

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
                    System.out.println(choose);
                    broadCastActionDispatcher(choose);
                } catch (IOException ignored) {
                }
            }
        }).start();
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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

    private void get_themes_broadcast() throws IOException {
        Integer howManyThemes = readerBroadcast.read();

        System.out.println(howManyThemes);

        ArrayList<Theme> themes = new ArrayList<>();

        for (int i = 0; i < howManyThemes; i++) {
            themes.add(Theme.deserialize(readerBroadcast));
        }

        System.out.println(themes);

        mainFrame.updateThemeTable(themes);
    }

    public boolean get_themes() throws IOException {
        return sendAction(EnumSocketAction.GET_THEMES, null);
    }

    public void broadCastActionDispatcher(Integer action) throws IOException {
        if (EnumSocketAction.GET_THEMES.ordinal() == action) {
            get_themes_broadcast();
        }
    }
}
