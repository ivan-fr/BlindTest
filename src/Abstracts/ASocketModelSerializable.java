package Abstracts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class ASocketModelSerializable<T> {
    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
    }

    public static <T> T deserialize(BufferedReader reader) throws IOException {
        return null;
    }
}
