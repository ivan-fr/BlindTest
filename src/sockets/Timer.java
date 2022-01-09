package sockets;

import Abstracts.ASocketModelSerializable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer extends ASocketModelSerializable<Timer> {
    private final AtomicInteger seconds = new AtomicInteger(0);

    public Timer(int seconds) {
        this.seconds.set(seconds);
    }

    public AtomicInteger getSeconds() {
        return seconds;
    }

    public void serialize(BufferedWriter writer, boolean flush) throws IOException {
        writer.write(seconds.get());
        if (flush) {
            writer.flush();
        }
    }

    public static Timer deserialize(BufferedReader reader) throws IOException {
        return new Timer(reader.read());
    }
}