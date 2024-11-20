package com.example.grampanchayatkouthaliapk;

public class Complaint {
    private String complaintId;
    private String name;
    private String address;
    private String mobileNumber;
    private String category;
    private String complaintDescription;
    private String status;

    public Complaint() {
    }

    public Complaint(String complaintId, String name, String address, String mobileNumber, String category, String complaintDescription, String status) {
        this.complaintId = complaintId;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.category = category;
        this.complaintDescription = complaintDescription;
        this.status = status;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public String getStatus() {
        return status;
    }

}
