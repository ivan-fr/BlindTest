package models;

import Abstracts.ASocketModelSerializable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class User extends AbstractModel {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.setKey(username);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("%s", username);
    }

    @Override
    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
        writer.write(username);
        writer.newLine();
        writer.write(password);
        writer.newLine();

        if (flush) {
            writer.flush();
        }
    }

    public static User deserialize(BufferedReader reader) throws IOException {
        String username = reader.readLine();
        String password = reader.readLine();
        return new User(username, password);
    }
}