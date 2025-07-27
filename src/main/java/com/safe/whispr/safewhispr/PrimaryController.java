package com.safe.whispr.safewhispr;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PrimaryController {

    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> chatListView;

    private ObservableList<String> messages;

    @FXML
    public void initialize() {
        messages = FXCollections.observableArrayList();
        chatListView.setItems(messages);
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText().trim();

        if (!message.isEmpty()) {
            messages.add("You: " + message);
            messageField.clear();

            // sending messages to firestore
            Firestore db = FirestoreClient.getFirestore();  
            Map<String, Object> data = new HashMap<>();
            data.put("sender", "You");
            data.put("message", message);
            data.put("timestamp", FieldValue.serverTimestamp());

            ApiFuture<DocumentReference> future = db.collection("messages").add(data);

            try {
                DocumentReference docRef = future.get();
                System.out.println("✅ Message sent with ID: " + docRef.getId());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("❌ Error sending message: " + e.getMessage());
            }
        }
    }
}
