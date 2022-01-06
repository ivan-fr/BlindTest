package models;

import composite.CompositeReponseSingleton;
import composite.CompositeThemeSingleton;

import java.sql.SQLException;

public class Fichier extends AbstractModel {
    private final String name;
    private final String extension;
    private final String reponse;
    private final String theme;
    private final String type;

    public Fichier(String name, String extension, String type,  String theme, String reponse) {
        this.name = name;
        this.extension = extension;
        this.reponse = reponse;
        this.type = type;
        this.theme = theme;
        this.getOneToOneReferences().put("reponse", reponse);
        this.getManyToOneReferences().put("theme", theme);
    }

    public String getType() {
        return type;
    }

    public Theme getTheme() throws SQLException {
        return CompositeThemeSingleton.compositeThemeSingleton.get((String) this.getManyToOneReferences().get("theme"));
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