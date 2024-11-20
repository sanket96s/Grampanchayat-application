package com.example.grampanchayatkouthaliapk;

public class Feedback {
    private final String message;
    private String name;
    private String email;

    public Feedback(String message) {
        this.message = message;
    }

    public Feedback(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
}
