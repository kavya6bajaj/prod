package com.productivity.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NotesController {

    @FXML
    private ListView<String> notesList;

    // =====================================================
    // STORAGE LOCATION (LOCAL FOLDER IN PROJECT USER SPACE)
    // =====================================================
    private final String STORAGE_DIR =
            System.getProperty("user.home") + "/ProductivitySuite/notes-storage";

    // =====================================================
    // INITIALIZE
    // =====================================================
    @FXML
    public void initialize() {

        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs(); // create folder if not exists
        }

        loadNotes();
    }

    // =====================================================
    // UPLOAD NOTE FILE
    // =====================================================
    @FXML
    private void handleUploadNote() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Note File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Documents", "*.pdf", "*.docx", "*.txt", "*.png", "*.jpg"
                ),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) return;

        try {
            File dir = new File(STORAGE_DIR);
            if (!dir.exists()) dir.mkdirs();

            // Prevent overwriting: if same name exists, append timestamp
            String fileName = selectedFile.getName();
            File targetFile = new File(dir, fileName);

            if (targetFile.exists()) {
                String newName = System.currentTimeMillis() + "_" + fileName;
                targetFile = new File(dir, newName);
            }

            Path targetPath = targetFile.toPath();

            Files.copy(
                    selectedFile.toPath(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            loadNotes();

        } catch (Exception e) {
            showError("Upload Failed", "Could not upload file.");
            e.printStackTrace();
        }
    }

    // =====================================================
    // DELETE SELECTED NOTE
    // =====================================================
    @FXML
    private void handleDeleteNote() {

        String selectedFile = notesList.getSelectionModel().getSelectedItem();

        if (selectedFile == null) {
            showError("No Selection", "Please select a file to delete.");
            return;
        }

        File file = new File(STORAGE_DIR, selectedFile);

        if (file.exists()) {
            boolean deleted = file.delete();

            if (deleted) {
                loadNotes();
            } else {
                showError("Delete Failed", "Could not delete file.");
            }
        }
    }

    // =====================================================
    // LOAD FILES INTO LISTVIEW
    // =====================================================
    private void loadNotes() {

        notesList.getItems().clear();

        File dir = new File(STORAGE_DIR);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    notesList.getItems().add(file.getName());
                }
            }
        }
    }

    // =====================================================
    // ERROR ALERT HELPER
    // =====================================================
    private void showError(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}