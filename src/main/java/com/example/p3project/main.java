package com.example.p3project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

public class main extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        ConnectDB connect = new ConnectDB();
        connect.connect();

        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 770, 450);
        stage.setTitle("To-Do-List!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}