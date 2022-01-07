package models;

import Abstracts.ASocketModelSerializable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractModel extends ASocketModelSerializable<AbstractModel> {
    private Object key;
    private final HashMap<String, Object> manyToOneReferences = new HashMap<>();
    private final HashMap<String, Object> oneToOneReferences = new HashMap<>();
    private final HashMap<String, ArrayList<Object>> oneToManyReferences = new HashMap<>();
    private final HashMap<String, ArrayList<Object>> manyToManyReferences = new HashMap<>();

    public HashMap<String, Object> getManyToOneReferences() {
        return manyToOneReferences;
    }

    public HashMap<String, ArrayList<Object>> getOneToManyReferences() {
        return oneToManyReferences;
    }

    public HashMap<String, ArrayList<Object>> getManyToManyReferences() {
        return manyToManyReferences;
    }

    public HashMap<String, Object> getOneToOneReferences() {
        return oneToOneReferences;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }
}