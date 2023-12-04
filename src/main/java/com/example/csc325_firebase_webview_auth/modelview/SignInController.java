package com.example.csc325_firebase_webview_auth.modelview;//import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {

    @FXML
    private final TextField usernameField;

    @FXML
    private final PasswordField passwordField;

    public SignInController(TextField usernameField, PasswordField passwordField) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
    }

    @FXML
    private void signInAction(ActionEvent event) throws IOException {
        String userName = usernameField.getText();
        SharedData.setUserName(userName);
        String password = passwordField.getText();

        // Implement your sign-in logic using username and password

        // If sign-in successful, navigate to next scene
        // App.setRoot("NextScene.fxml");
    }
}