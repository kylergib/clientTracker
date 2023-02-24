package com.kyle.clientTracker;

import com.kyle.clientTracker.model.Client;
import com.kyle.clientTracker.model.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    public static Client client;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Config.setProps();
//        client = new Client(Config.getServerUrl(), Config.getServerPort());
        client = new Client("localhost", Config.getServerPort());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        URL stylesheetUrl = getClass().getResource("style.css");
        if (stylesheetUrl != null) {
            scene.getStylesheets().add(stylesheetUrl.toExternalForm());
            System.out.println("Stylesheet loaded successfully.");
        } else {
            System.err.println("Failed to load stylesheet.");
        }
        stage.setTitle("Bet Tracker");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}