package sockets;

import Abstracts.ASocketModelSerializable;
import jFrame.MainFrame;
import models.Theme;
import models.User;

import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private final Socket clientSocket;
    private final Socket broadcastSocket;

    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final BufferedReader readerBroadcast;
    private final BufferedWriter writerBroadcast;

    private MainFrame mainFrame = null;
    private User me = null;
    private Party mySession = null;

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
                    if (choose == -1) {
                        broadCastSocket.close();
                        clientSocket.close();
                        me = null;
                        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
                        break;
                    }
                    broadCastActionDispatcher(choose);
                } catch (IOException e) {
                    e.printStackTrace();
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

    public Party getMySession() {
        return mySession;
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

    public boolean add_party(String partyName, Integer howManyQuestions, List<String> themes) throws IOException {
        if (me == null) {
            return false;
        }

        Party p = new Party(me.getUsername(), partyName, howManyQuestions);
        p.getThemesKeys().addAll(themes);

        if (sendAction(EnumSocketAction.ADD_PARTY, p)) {
            return join_party(partyName);
        }

        return false;
    }

    public boolean join_party(String partyName) throws IOException {
        if (me == null) {
            return false;
        }

        Party p = new Party("", partyName, 0);
        if (sendAction(EnumSocketAction.JOIN_PARTY, p)) {
            mySession = Party.deserialize(reader);
            System.out.println(mySession);
            mainFrame.onUpdateMySessionGame(mySession);
            return true;
        }

        return false;
    }

    public boolean get_themes() throws IOException {
        return sendAction(EnumSocketAction.GET_THEMES, null);
    }

    private void get_themes_broadcast() throws IOException {
        int howManyThemes = readerBroadcast.read();

        ArrayList<Theme> themes = new ArrayList<>();

        for (int i = 0; i < howManyThemes; i++) {
            themes.add(Theme.deserialize(readerBroadcast));
        }

        mainFrame.updateThemeTable(themes);
    }

    private void get_parties_broadcast() throws IOException {
        int howManyParties = readerBroadcast.read();

        ArrayList<Party> parties = new ArrayList<>();

        for (int i = 0; i < howManyParties; i++) {
            parties.add(Party.deserialize(readerBroadcast));
        }

        if (mySession != null) {
            for (Party party:
                    parties) {
                if (party.getPartyName().contentEquals(mySession.getPartyName())) {
                    mainFrame.onUpdateMySessionGame(party);
                    mySession = party;
                    break;
                }
            }
        }

        mainFrame.updateSessionTable(parties);
    }

    public void broadCastActionDispatcher(Integer action) throws IOException {
        if (EnumSocketAction.GET_THEMES.ordinal() == action) {
            get_themes_broadcast();
        } else if (EnumSocketAction.GET_PARTIES.ordinal() == action) {
            get_parties_broadcast();
        }
    }
}
