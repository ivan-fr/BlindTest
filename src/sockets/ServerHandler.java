package sockets;

import Abstracts.ASocketModelSerializable;
import composite.CompositeFichierSingleton;
import composite.CompositeThemeSingleton;
import composite.CompositeUserSingleton;
import models.Fichier;
import models.Reponse;
import models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        try {
        while (clientSocket.isConnected()) {
                int action = reader.read();
                if (action == -1) {
                    clientSocket.close();
                    broadcastClient.close();
                    serversHandler.remove(this);
                    break;
                }
                actionDispatcher(action);
            }
        } catch (IOException | SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    public void actionDispatcher(Integer action) throws IOException, SQLException, InterruptedException {
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
        } else if (EnumSocketAction.JOIN_PARTY.ordinal() == action) {
            join_party();
        } else if (EnumSocketAction.START_PARTY.ordinal() == action) {
            start_party();
        } else if (EnumSocketAction.SEND_PARTY_CHOICE.ordinal() == action) {
            send_party_choice();
        }
    }

    public synchronized void send_party_choice() throws IOException, SQLException, InterruptedException {
        Reponse r = Reponse.deserialize(reader);
        Party pSelected = null;
        for (Party party:
                parties) {
            if (party.getPartyName().contentEquals(selectedParty.getPartyName())) {
                pSelected = party;
                break;
            }
        }

        if (pSelected == null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        } else if (pSelected.getLastWinnerQuestion() != null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        if (r.getValue().contentEquals(pSelected.getGoodReponse().getValue())) {
            for (String participant :
                    pSelected.getParticipants().keySet()) {
                if (participant.contentEquals(me)) {
                    pSelected.getParticipants().get(participant).incrementAndGet();
                    break;
                }
            }
            pSelected.getCurrentQuestionInc();
            pSelected.setLastWinnerQuestion(me);
            try {
                pSelected.setGoodReponse(pSelected.getFichiersOrder().get(pSelected.getCurrentQuestion() - 1).getReponse());
            } catch (IndexOutOfBoundsException ignored) {
            }
        } else {
            writer.write(0);
            System.out.println(r.getValue());
            System.out.println(pSelected.getGoodReponse().getValue());
            System.out.println("send 0");
            writer.flush();
            return;
        }

        broadcastModelArray(EnumSocketAction.GET_PARTIES, parties);
        writer.write(1);
        System.out.println("send 1");
        writer.flush();
        Thread.sleep(4000);
        next_question_party();
    }

    public synchronized void next_question_party() throws IOException {
        Party pSelected = null;
        for (Party party:
                parties) {
            if (party.getPartyName().contentEquals(selectedParty.getPartyName())) {
                pSelected = party;
                break;
            }
        }

        if (pSelected == null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        pSelected.setLastWinnerQuestion(null);

        broadcastModelArray(EnumSocketAction.GET_PARTIES, parties);
        writer.write(1);
        System.out.println("send 1");
        writer.flush();
    }

    public synchronized void start_party() throws IOException {
        Party p = Party.deserialize(reader);
        Party pSelected = null;
        for (Party party:
                parties) {
            if (party.getPartyName().contentEquals(p.getPartyName())) {
                pSelected = party;
                break;
            }
        }

        if (pSelected == null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        pSelected.getCurrentQuestionInc();

        broadcastModelArray(EnumSocketAction.GET_PARTIES, parties);
        writer.write(1);
        System.out.println("send 1");
        writer.flush();
    }

    public synchronized void join_party() throws IOException {
        Party p = Party.deserialize(reader);
        Party pSelected = null;
        for (Party party:
             parties) {
            if (party.getPartyName().contentEquals(p.getPartyName()) && party.getCurrentQuestion() == 0) {
                pSelected = party;
                break;
            }
        }

        if (pSelected == null) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        AtomicInteger aI = new AtomicInteger();
        aI.set(0);
        pSelected.getParticipants().put(me, aI);
        selectedParty = pSelected;

        broadcastModelArray(EnumSocketAction.GET_PARTIES, parties);
        writer.write(1);
        System.out.println("send 1");
        pSelected.serialize(writer, false);
        writer.flush();
    }

    public synchronized void add_party() throws IOException, SQLException {
        Party p = Party.deserialize(reader);

        if (p.getHowManyQuestions() == 0) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        if (!CompositeUserSingleton.compositeUserSingleton.get(p.getAuthorKey()).equals(CompositeUserSingleton.compositeUserSingleton.get(me))) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        Random rand = new Random();
        boolean first = true;
        for (int i = 0; i < p.getHowManyQuestions(); i++) {
            String randomTheme = p.getThemesKey().get(rand.nextInt(p.getThemesKey().size()));
            Predicate<Fichier> byRandomTheme = fichier -> {
                try {
                    return fichier.getTheme().getValue().contentEquals(randomTheme) && !p.getFichiersOrder().contains(fichier);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            };

            List<Fichier> fichiersByTheme = CompositeFichierSingleton.compositeFichierSingleton
                    .list()
                    .stream()
                    .filter(byRandomTheme)
                    .collect(Collectors.toList());

            if (fichiersByTheme.size() == 0) {
                continue;
            }

            Fichier randomFichier = fichiersByTheme.get(rand.nextInt(fichiersByTheme.size()));
            List<Reponse> reponses = new ArrayList<>();

            fichiersByTheme.remove(randomFichier);
            reponses.add(randomFichier.getReponse());

            if (first) {
                p.setGoodReponse(randomFichier.getReponse());
                first = false;
            }

            for (int j = 0; j < Math.min(fichiersByTheme.size(), 3); j++) {
                Fichier randomFichierForTheme = fichiersByTheme.get(rand.nextInt(fichiersByTheme.size()));
                reponses.add(randomFichierForTheme.getReponse());
                fichiersByTheme.remove(randomFichierForTheme);
            }

            Collections.shuffle(reponses);
            p.getFichiersOrder().add(randomFichier);
            p.getQuestions().put(randomFichier, reponses);
        }

        if (p.getQuestions().size() == 0) {
            writer.write(0);
            System.out.println("send 0");
            writer.flush();
            return;
        }

        parties.add(p);
        broadcastModelArray(EnumSocketAction.GET_PARTIES, parties);
        writer.write(1);
        System.out.println("send 1");
        p.serialize(writer, false);
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
            broadcastModelArray(EnumSocketAction.GET_PARTIES, parties);
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
}
