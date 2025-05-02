package org.example.clientsevermsgexample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Server {
    private ServerSocket serverSocket;
    private final List<PrintWriter> clientWriters = new ArrayList<>();
    private final Consumer<String> messageCallback;

    public Server(int port, Consumer<String> messageCallback) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.messageCallback = messageCallback;
    }

    public void start() {
        new Thread(() -> {
            try {
                while (true) {
                    Socket client = serverSocket.accept();
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    clientWriters.add(out);

                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    new Thread(() -> {
                        String message;
                        try {
                            while ((message = in.readLine()) != null) {
                                messageCallback.accept("Client: " + message);
                                broadcast("Client: " + message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        broadcast("Server: " + message);
    }

    private void broadcast(String message) {
        for (PrintWriter out : clientWriters) {
            out.println(message);
        }
    }
}
