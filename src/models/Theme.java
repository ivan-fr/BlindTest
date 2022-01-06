package models;

import composite.CompositeFichierSingleton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Theme extends AbstractModel {

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
}
