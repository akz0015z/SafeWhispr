package com.safe.whispr.safewhispr;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("com/safe/whispr/safewhispr/primary");
    }
}
