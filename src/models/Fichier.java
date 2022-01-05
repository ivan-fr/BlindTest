package models;

import composite.CompositeReponseSingleton;

import java.sql.SQLException;

public class Fichier extends AbstractModel {
    private final String name;
    private final String extension;
    private final String reponse;
    private final String type;

    public Fichier(String name, String extension, String type, String reponse) {
        this.name = name;
        this.extension = extension;
        this.reponse = reponse;
        this.type = type;
        this.getOneToOneReferences().put("reponse", reponse);
    }

    public String getType() {
        return type;
    }

    public Reponse getReponse() throws SQLException {
        return CompositeReponseSingleton.compositeReponseSingleton.get((String) this.getOneToOneReferences().get("reponse"));
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", name, extension);
    }
}