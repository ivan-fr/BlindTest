package models;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Reponse extends AbstractModel {
    private final String value;

    public Reponse(String value) {
        this.value = value;
        setKey(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s", value);
    }

    @Override
    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
        writer.write(this.value);
        writer.newLine();

        if (flush) {
            writer.flush();
        }
    }

    public static Reponse deserialize(BufferedReader reader) throws IOException {
        String value = reader.readLine();
        return new Reponse(value);
    }
}