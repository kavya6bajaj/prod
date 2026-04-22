package com.productivity.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    private Stage stage;

    private String currentView = "";

    // -------------------------
    // STAGE INJECTION (ENTRY POINT)
    // -------------------------
    public void setStage(Stage stage) {
        this.stage = stage;

        // load default view AFTER stage is available
        loadView("/fxml/tasks.fxml");
    }

    // -------------------------
    // INITIALIZE (DO NOT LOAD VIEWS HERE)
    // -------------------------
    @FXML
    public void initialize() {
        // intentionally empty
    }

    // -------------------------
    // NAVIGATION METHODS
    // -------------------------
    @FXML
    private void showTasks() {
        loadView("/fxml/tasks.fxml");
    }

    @FXML
    private void showPomodoro() {
        loadView("/fxml/pomodoro.fxml");
    }

    @FXML
    private void showNotes() {
        loadView("/fxml/notes.fxml");
    }

    // -------------------------
    // CORE VIEW LOADER
    // -------------------------
    private void loadView(String path) {

        // prevent reloading same view
        if (path.equals(currentView)) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent view = loader.load();

            contentArea.getChildren().setAll(view);
            currentView = path;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}