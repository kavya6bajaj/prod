package com.productivity.service;

import com.productivity.model.Task;
import java.util.List;
import java.util.ArrayList;

public class MockTaskService implements TaskService {

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task(1L, "Finish JavaFX UI", false, "HIGH"));
        tasks.add(new Task(2L, "Implement Pomodoro", false, "MEDIUM"));
        tasks.add(new Task(3L, "Write Notes Module", true, "LOW"));

        return tasks;
    }
}