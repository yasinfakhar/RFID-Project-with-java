/**
 * edited : 13 March 2020
 * **/

package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.repository.repository;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        repository.stage = stage;
        repository.changeStageContent("MainLayout");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
