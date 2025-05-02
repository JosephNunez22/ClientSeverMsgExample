package org.example.clientsevermsgexample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.clientsevermsgexample.model.Client;

import java.io.IOException;

public class ClientController {
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;

    private Client client;

    @FXML
    public void initialize() {
        try {
            client = new Client("localhost", 12345, this::addMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        button_send.setOnAction(event -> {
            String msg = tf_message.getText();
            if (!msg.isEmpty()) {
                client.sendMessage(msg);
                addMessage("You: " + msg);
                tf_message.clear();
            }
        });
    }

    private void addMessage(String msg) {
        Platform.runLater(() -> {
            Text text = new Text(msg);
            TextFlow flow = new TextFlow(text);
            vbox_messages.getChildren().add(flow);
            sp_main.setVvalue(1.0);
        });
    }
}