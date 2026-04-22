package com.productivity;

import com.productivity.controller.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1000, 700);

        // attach CSS
        String css = getClass().getResource("/css/app.css").toExternalForm();
        scene.getStylesheets().add(css);

        // inject controller
        DashboardController controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle("Productivity Suite");
        stage.setScene(scene);

        stage.setMinWidth(900);
        stage.setMinHeight(600);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}