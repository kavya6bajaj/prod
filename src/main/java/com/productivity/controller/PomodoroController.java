package com.productivity.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

public class PomodoroController {

    @FXML private Label timeLabel;
    @FXML private Label modeLabel;

    @FXML private TextField focusInput;
    @FXML private TextField breakInput;

    @FXML private Arc progressArc;

    private Timeline timeline;

    private int focusMinutes = 25;
    private int breakMinutes = 5;

    private int totalSeconds;
    private int remainingSeconds;

    private boolean isFocusSession = true;

    // -------------------------
    // SET CYCLE FROM INPUT
    // -------------------------
    @FXML
    private void handleSetCycle() {

        try {
            if (!focusInput.getText().isBlank()) {
                focusMinutes = Integer.parseInt(focusInput.getText());
            }

            if (!breakInput.getText().isBlank()) {
                breakMinutes = Integer.parseInt(breakInput.getText());
            }

        } catch (NumberFormatException e) {
            focusMinutes = 25;
            breakMinutes = 5;
        }

        resetTimer();
    }

    // -------------------------
    // START TIMER
    // -------------------------
    @FXML
    private void handleStart() {

        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            return;
        }

        startSession(isFocusSession ? focusMinutes : breakMinutes);
    }

    // -------------------------
    // PAUSE TIMER
    // -------------------------
    @FXML
    private void handlePause() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    // -------------------------
    // RESET TIMER
    // -------------------------
    @FXML
    private void handleReset() {
        if (timeline != null) {
            timeline.stop();
        }
        resetTimer();
    }

    // -------------------------
    // START SESSION LOGIC
    // -------------------------
    private void startSession(int minutes) {

        totalSeconds = minutes * 60;
        remainingSeconds = totalSeconds;

        updateUI();

        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> tick())
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        modeLabel.setText(isFocusSession ? "FOCUS MODE" : "BREAK MODE");
    }

    // -------------------------
    // TIMER TICK
    // -------------------------
    private void tick() {

        if (remainingSeconds <= 0) {
            timeline.stop();

            // auto switch session
            isFocusSession = !isFocusSession;
            startSession(isFocusSession ? focusMinutes : breakMinutes);

            return;
        }

        remainingSeconds--;
        updateUI();
    }

    // -------------------------
    // UI + RING UPDATE
    // -------------------------
    private void updateUI() {

        int min = remainingSeconds / 60;
        int sec = remainingSeconds % 60;

        timeLabel.setText(String.format("%02d:%02d", min, sec));

        double progress = (double) remainingSeconds / totalSeconds;

        // 🔥 Ring animation (key part)
        progressArc.setLength(360 * progress);
    }

    // -------------------------
    // RESET STATE
    // -------------------------
    private void resetTimer() {

        isFocusSession = true;

        totalSeconds = focusMinutes * 60;
        remainingSeconds = totalSeconds;

        if (timeline != null) {
            timeline.stop();
        }

        modeLabel.setText("FOCUS MODE");
        updateUI();

        // full circle reset
        progressArc.setLength(360);
    }
}