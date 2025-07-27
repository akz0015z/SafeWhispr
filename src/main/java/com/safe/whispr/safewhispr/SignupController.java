/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safe.whispr.safewhispr;

/**
 *
 * @author manis
 */
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import java.io.IOException;
import com.safe.whispr.safewhispr.AES;



public class SignupController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private void handleCreateAccount() {
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                // encrypts password before saving
                String encryptedPassword = AES.encrypt(password);

                // starting firebase if not already
                if (com.google.firebase.FirebaseApp.getApps().isEmpty()) {
                    FirebaseInitializer.initialize();
                }

                Firestore db = FirebaseInitializer.getFirestore();
                DocumentReference userRef = db.collection("users").document(username);
                userRef.set(new User(username, encryptedPassword));

                showAlert("Success", "Account created for " + username + "!");

                App.setRoot("fxml/Login");

            } catch (Exception e) {
                showAlert("Error", "Something went wrong during account creation.");
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Please fill in both fields.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            App.setRoot("fxml/Login");
        } catch (IOException e) {
            showAlert("Error", "Failed to return to login screen.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // the user class for storing in Firestore
    public static class User {
        private String username;
        private String password;

        public User() {} 

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }
}