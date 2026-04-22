package com.productivity.state;

import com.productivity.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskStore {

    private static final ObservableList<Task> TASKS =
            FXCollections.observableArrayList();

    public static ObservableList<Task> getTasks() {
        return TASKS;
    }

    public static void addTask(Task task) {
        TASKS.add(task);
    }
}