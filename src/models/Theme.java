package models;

import composite.CompositeFichierSingleton;
import Interfaces.ISocketModelsSerializable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Theme extends AbstractModel implements ISocketModelsSerializable<Theme> {
    private final String value;

    public Theme(String value) {
        this.setKey(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public List<Fichier> getFichiers() throws SQLException {
        List<Fichier> fichiers = new ArrayList<>();
        for (Object ref : this.getOneToManyReferences().get("fichiers")) {
            fichiers.add(CompositeFichierSingleton.compositeFichierSingleton.get((Integer) ref));
        }
        return fichiers;
    }

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

    public static Theme deserialize(BufferedReader reader) throws IOException {
        String value = reader.readLine();
        return new Theme(value);
    }
}
