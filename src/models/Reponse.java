package models;


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
}