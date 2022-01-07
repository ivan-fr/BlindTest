package models;


import Interfaces.ISocketModelsSerializable;

import java.io.BufferedWriter;
import java.io.IOException;

public class Reponse extends AbstractModel implements ISocketModelsSerializable<Reponse> {
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

    }
}