package com.example.lab4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class that extends the JavaFX Application class.
 * This class is responsible for starting the application and displaying the main window.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public class Application extends javafx.application.Application {
    /**
    * This method is called when the application is starting.
    * It sets up the stage, loads the FXML file, and displays the scene.
    *
    * @param stage the primary stage for the application
    * @throws IOException if an error occurs while loading the FXML file
    */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Student System");
        stage.setScene(scene);
        stage.show();
    }

    /**
    * The main method is the entry point of the application.
    * It launches the application by calling the launch method.

    * @param args the command line arguments
    */
    public static void main(String[] args) {
        launch();
    }
}