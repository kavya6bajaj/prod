    package com.productivity.model;

    public class Task {

        private Long id;
        private String title;
        private boolean completed;
        private String priority;

        public Task(Long id, String title, boolean completed, String priority) {
            this.id = id;
            this.title = title;
            this.completed = completed;
            this.priority = priority;
        }

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public boolean isCompleted() { return completed; }
        public String getPriority() { return priority; }

        // ✅ ADD THIS
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }