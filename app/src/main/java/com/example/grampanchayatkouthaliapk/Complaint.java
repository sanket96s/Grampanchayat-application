package com.example.grampanchayatkouthaliapk;

public class Complaint {
    private String complaintId;
    private String name;
    private String address;
    private String mobileNumber;
    private String complaintDescription;
    private String status;

    // Default constructor required for calls to DataSnapshot.getValue(Complaint.class)
    public Complaint() {
    }

    // Parameterized constructor for initializing all fields
    public Complaint(String complaintId, String name, String address, String mobileNumber, String ignoredCategory, String complaintDescription, String status) {
        this.complaintId = complaintId;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.complaintDescription = complaintDescription;
        this.status = status;
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
