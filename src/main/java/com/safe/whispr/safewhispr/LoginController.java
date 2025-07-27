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
import com.google.cloud.firestore.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Firestore firestore;

    public LoginController() {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseInitializer.initialize();
        }
        firestore = FirebaseInitializer.getFirestore();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String enteredPassword = passwordField.getText().trim();

        if (username.isEmpty() || enteredPassword.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        DocumentReference userRef = firestore.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = userRef.get();

        try {
            DocumentSnapshot snapshot = future.get();

            if (snapshot.exists()) {
                String encryptedPassword = snapshot.getString("password");

                if (encryptedPassword != null) {
                    String decryptedPassword = AESEncryption.decrypt(encryptedPassword);

                    if (enteredPassword.equals(decryptedPassword)) {
                        UserSession.setUsername(username);
                        App.setRoot("fxml/Choice");
                    } else {
                        showAlert("Login Failed", "Invalid username or password.");
                    }
                } else {
                    showAlert("Login Failed", "No password found for user.");
                }
            } else {
                showAlert("Login Failed", "User does not exist.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during login.");
        } catch (Exception e) {
            showAlert("Error", "Decryption failed. " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            App.setRoot("fxml/Signup");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the sign-up screen.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
