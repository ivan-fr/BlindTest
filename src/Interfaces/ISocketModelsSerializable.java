package Interfaces;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface ISocketModelsSerializable<T> {
    void serialize(BufferedWriter writer, boolean flush) throws IOException;

    static <T> T deserialize(BufferedReader reader) throws IOException {
        return null;
    }
}
