/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safe.whispr.safewhispr;

/**
 *
 * @author manis
 */

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ChatUI extends Application {

    private final ListView<Label> messageListView = new ListView<>();
    private final TextField messageField = new TextField();
    private final Button sendButton = new Button("Send");
    private final Button logoutButton = new Button("🔒 Logout");
    private final Button exitButton = new Button("❌ Exit");
    private final Button clearChatButton = new Button("🧹 Clear Chat");

    private Firestore firestore;

    @Override
    public void start(Stage primaryStage) {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseInitializer.initialize();
        }

        firestore = FirebaseInitializer.getFirestore();

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #121212, #2a2a2a);");

        String buttonStyle = "-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10;";
        logoutButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);
        sendButton.setStyle(buttonStyle);
        clearChatButton.setStyle(buttonStyle);

        messageField.setStyle("-fx-background-radius: 10; -fx-padding: 10; -fx-text-fill: white; -fx-background-color: #3a3a3a;");
        messageListView.setPrefHeight(400);
        messageListView.setStyle("-fx-background-color: #2a2a2a;");

        HBox topButtons = new HBox(10, logoutButton, exitButton, clearChatButton);
        Label chatTitle = new Label("💬 SafeWhispr Chat");
        chatTitle.setTextFill(Color.WHITE);
        chatTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        HBox inputBox = new HBox(10, messageField, sendButton);
        HBox.setHgrow(messageField, Priority.ALWAYS);

        root.getChildren().addAll(topButtons, chatTitle, messageListView, inputBox);

        sendButton.setOnAction(e -> sendMessage());
        logoutButton.setOnAction(e -> handleLogout(primaryStage));
        exitButton.setOnAction(e -> Platform.exit());
        clearChatButton.setOnAction(e -> handleClearChat());

        listenForMessages();

        Scene scene = new Scene(root, 600, 550);
        primaryStage.setTitle("SafeWhispr Chat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendMessage() {
        String messageText = messageField.getText().trim();
        if (messageText.isEmpty()) return;

        String senderName = UserSession.isAnonymous() ? "Anonymous" : UserSession.getUsername();
        String encryptedText = AES.encrypt(messageText);

        DocumentReference newMsg = UserSession.isAnonymous()
                ? firestore.collection("anonymous_messages").document()
                : firestore.collection("public_messages").document();

        Message message = new Message(senderName, encryptedText, Timestamp.now());

        try {
            ApiFuture<WriteResult> result = newMsg.set(message);
            result.get();
        } catch (Exception e) {
            System.err.println("❌ Failed to send message: " + e.getMessage());
        }

        messageField.clear();
    }

    private void listenForMessages() {
        String collection = UserSession.isAnonymous() ? "anonymous_messages" : "public_messages";

        firestore.collection(collection)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        System.err.println("❌ Listen failed: " + e);
                        return;
                    }

                    Platform.runLater(() -> {
                        messageListView.getItems().clear();
                        for (QueryDocumentSnapshot doc : snapshots) {
    String sender = doc.getString("sender");
    String encryptedText = doc.getString("text");
    Timestamp timestamp = doc.getTimestamp("timestamp");

    String decryptedText = AES.decrypt(encryptedText);
    if (decryptedText == null) {
        decryptedText = "[UNREADABLE MESSAGE]";
    }

    // made timestamp to look like(14:53)
    String timeStr = "";
    if (timestamp != null) {
        timeStr = "  [" + timestamp.toDate().toString().substring(11, 16) + "]";
    }

    Label bubble = new Label(sender + ": " + decryptedText + timeStr);
    bubble.setWrapText(true);
    bubble.setFont(Font.font("System", FontWeight.BOLD, 13));
    bubble.setTextFill(Color.WHITE);
    bubble.setStyle("-fx-background-color: #444; -fx-background-radius: 10; -fx-padding: 8;");
    messageListView.getItems().add(bubble);
}

                    });
                });
    }

    private void handleClearChat() {
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Clear Chat");
    confirm.setHeaderText("Are you sure you want to delete all messages?");
    confirm.setContentText("This cannot be undone.");

    confirm.showAndWait().ifPresent(result -> {
        if (result == ButtonType.OK) {
            String collection = UserSession.isAnonymous() ? "anonymous_messages" : "public_messages";

            new Thread(() -> {
                try {
                    ApiFuture<QuerySnapshot> future = firestore.collection(collection).get();
                    QuerySnapshot snapshot = future.get();

                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        doc.getReference().delete();
                    }

                    Platform.runLater(() -> messageListView.getItems().clear());
                    System.out.println("✅ Done! Chat cleared successfully.");
                } catch (Exception e) {
                    System.err.println("❌ Sorry! Failed to clear chat: " + e.getMessage());
                }
            }).start();
        }
    });
}


    private void handleLogout(Stage currentStage) {
        try {
            UserSession.setUsername(null);
            UserSession.setAnonymous(false);
            Stage loginStage = new Stage();
            new App().start(loginStage);
            currentStage.close();
        } catch (Exception ex) {
            System.err.println("❌ Failed to return to Login screen: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Message {
        private String sender;
        private String text;
        private Timestamp timestamp;

        public Message() {}

        public Message(String sender, String text, Timestamp timestamp) {
            this.sender = sender;
            this.text = text;
            this.timestamp = timestamp;
        }

        public String getSender() { return sender; }
        public String getText() { return text; }
        public Timestamp getTimestamp() { return timestamp; }
    }
}