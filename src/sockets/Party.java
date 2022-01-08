package sockets;

import Abstracts.ASocketModelSerializable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Party extends ASocketModelSerializable<Party> {
    private final String authorKey;
    private final String partyName;
    private final List<String> themesKey = new ArrayList<>();
    private final HashMap<String, AtomicInteger> participants = new HashMap<>();
    private final Integer howManyQuestions;
    private final AtomicInteger currentQuestion = new AtomicInteger(0);

    public Party(String authorLey, String partyName, Integer howManyQuestions) {
        this.authorKey = authorLey;
        this.partyName = partyName;
        this.howManyQuestions = howManyQuestions;
    }

    public Integer getCurrentQuestion() {
        return currentQuestion.get();
    }

    public List<String> getThemesKeys() {
        return themesKey;
    }

    public String getAuthorKey() {
        return authorKey;
    }

    public String getPartyName() {
        return partyName;
    }

    public List<String> getThemesKey() {
        return themesKey;
    }

    public Integer getHowManyQuestions() {
        return howManyQuestions;
    }

    @Override
    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
        writer.write(authorKey);
        writer.newLine();

        writer.write(themesKey.size());

        writer.write(this.partyName);
        writer.newLine();

        writer.write(howManyQuestions);

        for (String theme:
                themesKey) {
            writer.write(theme);
            writer.newLine();
        }

        writer.write(currentQuestion.get());
        writer.write(participants.size());

        for (String u:
             participants.keySet()) {
           writer.write(u);
           writer.newLine();
           writer.write(participants.get(u).get());
        }

        if (flush) {
            writer.flush();
        }
    }

    public HashMap<String, AtomicInteger> getParticipants() {
        return participants;
    }

    public static Party deserialize(BufferedReader reader) throws IOException {
        String authorKey = reader.readLine();
        int howManyThemes = reader.read();
        Party party = new Party(authorKey, reader.readLine(), reader.read());

        for (int i = 0; i < howManyThemes; i++) {
            party.getThemesKeys().add(reader.readLine());
        }

        party.currentQuestion.set(reader.read());
        int howManyParticipants = reader.read();

        for (int i = 0; i < howManyParticipants; i++) {
            String userKey = reader.readLine();
            int points = reader.read();
            AtomicInteger aI = new AtomicInteger();
            aI.set(points);
            party.getParticipants().put(userKey, aI);
        }

        return party;
    }
}
