package com.example.grampanchayatkouthaliapk;

public class Scheme {

    private String title;
    private String description;
    private int imageResId;
    private String eligibility;
    private String requiredDocs;

    public Scheme(String title, String description, int imageResId, String eligibility, String requiredDocs) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.eligibility = eligibility;
        this.requiredDocs = requiredDocs;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getEligibility() {
        return eligibility;
    }

    public String getRequiredDocs() {
        return requiredDocs;
    }
}
