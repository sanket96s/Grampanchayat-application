package com.example.grampanchayatkouthaliapk;

public class Scheme {

    private final String title;
    private final String description;
    private final int imageResId;
    private final String eligibility;
    private final String requiredDocs;

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
