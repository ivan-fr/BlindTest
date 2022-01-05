package models;

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
}