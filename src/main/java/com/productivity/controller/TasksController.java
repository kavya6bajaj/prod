package com.productivity.controller;

import com.productivity.model.Task;
import com.productivity.state.TaskStore;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class TasksController {

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Boolean> completedColumn;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private TableColumn<Task, Void> actionColumn;

    private ObservableList<Task> taskList;

    @FXML
    public void initialize() {

        // ---------------------------
        // DATA SOURCE
        // ---------------------------
        taskList = TaskStore.getTasks();
        taskTable.setItems(taskList);

        // ---------------------------
        // COLUMN BINDING
        // ---------------------------
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().isCompleted() ? "Done" : "Pending"
                )
        );

        // ---------------------------
        // TABLE SETTINGS
        // ---------------------------
        taskTable.setEditable(true);

        // ---------------------------
        // CHECKBOX COLUMN (toggle completion)
        // ---------------------------
        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                index -> {
                    Task task = taskTable.getItems().get(index);

                    SimpleBooleanProperty property =
                            new SimpleBooleanProperty(task.isCompleted());

                    property.addListener((obs, oldVal, newVal) -> {
                        task.setCompleted(newVal);
                        taskTable.refresh();
                    });

                    return property;
                }
        ));

        // ---------------------------
        // DELETE COLUMN (FULL ACTION BUTTON)
        // ---------------------------
        actionColumn.setCellFactory(col -> new TableCell<>() {

            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.getStyleClass().add("btn-delete");

                deleteBtn.setOnAction(e -> {
                    Task task = getTableView().getItems().get(getIndex());

                    if (task != null) {
                        TaskStore.getTasks().remove(task);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    // ---------------------------
    // ADD TASK
    // ---------------------------
    @FXML
    private void handleAddTask() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Task");
        dialog.setHeaderText("Create New Task");
        dialog.setContentText("Enter task title:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(title -> {

            Task newTask = new Task(
                    System.currentTimeMillis(),
                    title,
                    false,
                    "MEDIUM"
            );

            TaskStore.addTask(newTask);
        });
    }
}