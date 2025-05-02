package org.example.clientsevermsgexample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {
    private Socket socket;
    private PrintWriter out;

    public Client(String host, int port, Consumer<String> messageCallback) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(() -> {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    messageCallback.accept(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}
