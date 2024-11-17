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
        // Default constructor required for calls to DataSnapshot.getValue(Complaint.class)
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

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
