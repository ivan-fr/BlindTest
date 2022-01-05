package models;

import composite.CompositeReponseSingleton;

public class Fichier extends AbstractModel {
    private final String name;
    private final String extension;
    private final String reponse;

    public Fichier(String name, String extension, String reponse) {
        this.name = name;
        this.extension = extension;
        this.reponse = reponse;
        this.getOneToOneReferences().put("reponse", reponse);
    }

    public Reponse getReponse() {
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
        return String.format("%s", name);
    }
}