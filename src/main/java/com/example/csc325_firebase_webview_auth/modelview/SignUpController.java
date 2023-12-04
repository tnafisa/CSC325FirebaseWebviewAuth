package com.example.csc325_firebase_webview_auth.modelview;//import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {

    @FXML
    private final TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    public SignUpController(TextField newUsernameField) {
        this.newUsernameField = newUsernameField;
    }

    @FXML
    private void signUpAction(ActionEvent event) throws IOException {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();

        // Implement your sign-up logic using newUsername and newPassword

        // If sign-up successful, navigate to next scene or show a success message
        // App.setRoot("NextScene.fxml");
    }
}