package com.example.csc325_firebase_webview_auth.modelview;//package modelview;

import com.example.csc325_firebase_webview_auth.App;
import com.example.csc325_firebase_webview_auth.FirestoreContext;
import com.example.csc325_firebase_webview_auth.models.Person;
import com.example.csc325_firebase_webview_auth.viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.javafx.application.PlatformImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AccessFBView {


     @FXML
    private TextField nameField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField ageField;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton;
    @FXML
    private TextArea outputField;
     private boolean key;

    private final ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;
    private ObservableList<Person> getListOfUsers() {

        return listOfUsers;
    }

    void initialize() {

        AccessDataViewModel accessDataViewModel;
        accessDataViewModel = new AccessDataViewModel();
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
    }

    @FXML
    private void addRecord(ActionEvent event) {

        addData();
    }

        @FXML
    private void readRecord(ActionEvent event, PlatformImpl Platform) {

        PlatformImpl.runLater(this::readFirebase);
    }

            @FXML
    private void regRecord(ActionEvent event) {

        registerUser();
    }

     @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("WebContainer.fxml");
    }
    @FXML
    private BorderPane rootPane; // This refers to your root pane in FXML

    @FXML
    private void switchToSignIn(ActionEvent event) throws IOException {
        App.setRoot("SignIn.fxml");
    }

    @FXML
    private void switchToSignUp(ActionEvent event) throws IOException {
        App.setRoot("SignUp.fxml");
    }

    public void addData() {

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }

        public void readFirebase()
         {
             key = false;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
             try {
                 documents = future.get().getDocuments();
                 if (!documents.isEmpty()) {
                     for (QueryDocumentSnapshot document : documents) {
                         Platform.runLater(() -> {
                             outputField.appendText(
                                     document.getData().get("Name") + ", Major: " +
                                             document.getData().get("Major") + ", Age: " +
                                             document.getData().get("Age") + "\n"
                             );
                         });


                    person  = new Person
                            (String.valueOf(document.getData().get("Name")),
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString()));
                    listOfUsers.add(person);
                }
            }
            else
            {
               System.out.println("No data");
            }
            key=true;

        }
        catch (InterruptedException | ExecutionException ex)
        {
             ex.printStackTrace();
        }



        public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    public void registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());

        } catch (FirebaseAuthException ex) {
            Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
