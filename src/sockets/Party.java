package sockets;

import Abstracts.ASocketModelsSerializable;
import models.Theme;
import models.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Party extends ASocketModelsSerializable<Party> {
    private final User author;
    private final String partyName;
    private final List<Theme> themes = new ArrayList<>();
    private final Integer howManyQuestions;
    private final AtomicInteger currentQuestion = new AtomicInteger(0);

    public Party(User author, String partyName, Integer howManyQuestions) {
        this.author = author;
        this.partyName = partyName;
        this.howManyQuestions = howManyQuestions;
    }

    public Integer getCurrentQuestion() {
        return currentQuestion.get();
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public Integer getHowManyQuestions() {
        return howManyQuestions;
    }

    @Override
    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
        author.serialize(writer, false);

        writer.write(themes.size());

        writer.write(this.partyName);
        writer.newLine();

        writer.write(howManyQuestions);

        for (Theme theme:
             themes) {
            theme.serialize(writer, false);
        }

        writer.write(currentQuestion.get());

        if (flush) {
            writer.flush();
        }

    }

    public static Party deserialize(BufferedReader reader) throws IOException {
        User author = User.deserialize(reader);
        int howManyTheme = reader.read();
        Party party = new Party(author, reader.readLine(), reader.read());

        for (int i = 0; i < howManyTheme; i++) {
            party.getThemes().add(Theme.deserialize(reader));
        }
        party.currentQuestion.set(reader.read());

        return party;
    }
}
