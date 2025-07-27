package com.safe.whispr.safewhispr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Where i start firebase
        FirebaseInitializer.initialize();

        // Where it loads the login screen
        scene = new Scene(loadFXML("fxml/Login"), 640, 480); 
        stage.setScene(scene);
        stage.setTitle("SafeWhispr");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String path = "/" + fxml + ".fxml";  // allows full path like "fxml/Login" or "com/safe/whispr/safewhispr/primary"
        var url = App.class.getResource(path);

        if (url == null) {
            throw new IOException("FXML file not found at path: " + path);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}