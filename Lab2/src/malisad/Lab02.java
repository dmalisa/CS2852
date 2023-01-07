/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Lab2
 * Name: malisad
 * Created 3/19/2021
 */
package malisad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * Lab2 purpose:
 *
 * @author malisad
 * @version created on 3/19/2021 at 6:40 PM
 */
public class Lab02 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Lab2.fxml"));
        primaryStage.setTitle("Dot to Dot");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
