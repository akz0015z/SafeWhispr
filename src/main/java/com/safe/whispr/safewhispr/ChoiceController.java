/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safe.whispr.safewhispr;

/**
 *
 * @author manis
 */
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceController implements Initializable {

    @FXML
    private Button anonymousBtn;

    @FXML
    private Button publicBtn;

    @FXML
    private Button backToLoginButton; 

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anonymousBtn.setOnAction(e -> {
            UserSession.setPostingMode("Anonymous");
            if (UserSession.getUsername() == null || UserSession.getUsername().isEmpty()) {
                UserSession.setUsername("User");
            }
            openChatUI();
        });

        publicBtn.setOnAction(e -> {
            UserSession.setPostingMode("Public");
            if (UserSession.getUsername() == null || UserSession.getUsername().isEmpty()) {
                UserSession.setUsername("User");
            }
            openChatUI();
        });

        if (backToLoginButton != null) {
            backToLoginButton.setOnAction(e -> handleBackToLogin());
        }
    }

    private void openChatUI() {
        try {
            ChatUI chatUI = new ChatUI();
            chatUI.start(new Stage());

            // to close the current window
            Stage currentStage = (Stage) anonymousBtn.getScene().getWindow();
            currentStage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            App.setRoot("fxml/Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}