package com.java_academy;


import com.java_academy.gui.Controller;
import com.java_academy.logic.model.MessageObject;
import com.java_academy.network.Connector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeWindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Battleships");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
