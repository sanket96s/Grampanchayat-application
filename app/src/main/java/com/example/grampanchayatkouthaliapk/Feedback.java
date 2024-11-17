package com.example.grampanchayatkouthaliapk;

public class Feedback {
    private String name;
    private String email;
    private String message;

    // Empty constructor for Firebase
    public Feedback() {}

    // Constructor
    public Feedback(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters and setters
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

    public void setMessage(String message) {
        this.message = message;
    }
}
