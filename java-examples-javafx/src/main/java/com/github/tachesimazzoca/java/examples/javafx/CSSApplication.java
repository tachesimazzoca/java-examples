package com.github.tachesimazzoca.java.examples.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CSSApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Form");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("main-grid");

        final int FIELD_ROW_OFFSET = 1;

        // Title
        Text titleText = new Text();
        titleText.setText("Create Your Account");
        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().add(titleText);
        grid.add(titleBox, 0, 0, 2, 1);

        // E-mail
        Label emailLabel = new Label();
        emailLabel.setText("E-mail");
        grid.add(emailLabel, 0, FIELD_ROW_OFFSET);
        final TextField emailField = new TextField();
        grid.add(emailField, 1, FIELD_ROW_OFFSET);

        // Password
        Label passwordLabel = new Label();
        passwordLabel.setText("Password");
        grid.add(passwordLabel, 0, FIELD_ROW_OFFSET + 1);
        final PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, FIELD_ROW_OFFSET + 1);

        // Submit
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(actionEvent.getSource());
                System.out.println(emailField.getText());
                System.out.println(passwordField.getText());
            }
        });
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.getChildren().add(submitButton);
        grid.add(buttonBox, 0, FIELD_ROW_OFFSET + 3, 2, 1);

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        String externalForm = CSSApplication.class.getResource(
                "/CSSApplication.css").toExternalForm();
        System.out.println("CSS URL: " + externalForm);
        scene.getStylesheets().add(externalForm);

        primaryStage.show();
    }
}
