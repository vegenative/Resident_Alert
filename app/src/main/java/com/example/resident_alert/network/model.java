package com.example.resident_alert.network;

public class model {
    private String action;
    private String place;
    private String status;
    private String submissionDate;



    private String phone;

    public model() {
    }

    public model(String action, String place, String status, String submissionDate, String phone) {
        this.action = action;
        this.place = place;
        this.status = status;
        this.submissionDate = submissionDate;
        this.phone = phone;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
