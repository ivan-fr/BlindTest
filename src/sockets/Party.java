package sockets;

import Interfaces.ISocketModelsSerializable;
import models.Theme;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Party implements ISocketModelsSerializable<Party> {
    private final Integer howManyTheme;
    private final List<Theme> themes = new ArrayList<>();
    private final Integer howManyQuestions;
    private final AtomicInteger currentQuestion = new AtomicInteger(0);

    public Party(Integer howManyTheme, Integer howManyQuestions) {
        this.howManyTheme = howManyTheme;
        this.howManyQuestions = howManyQuestions;
    }

    public Integer getCurrentQuestion() {
        return currentQuestion.get();
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public Integer getHowManyTheme() {
        return howManyTheme;
    }

    public Integer getHowManyQuestions() {
        return howManyQuestions;
    }

    @Override
    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
        writer.write(howManyTheme);
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
        Party party = new Party(reader.read(), reader.read());

        for (int i = 0; i < party.getHowManyQuestions(); i++) {
            party.getThemes().add(Theme.deserialize(reader));
        }
        party.currentQuestion.set(reader.read());

        return party;
    }
}
