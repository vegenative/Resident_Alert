package com.example.resident_alert;

public class UserHelperClass {

    private String email;
    private String name;
    private String surname;
    private String telephone;
    private String city;
    private String street;
    private String block;
    private String flatLetter;
    private String flat;
    private String place;
    private String action;
    private String info;
    private String status;
    private String submissionDate;

    //Login parameters
    public UserHelperClass(String email,String name,String surname, String telephone,
                           String street, String block,String city,String flatLetter,String flat) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.city = city;
        this.street = street;
        this.block = block;
        this.flatLetter = flatLetter;
        this.flat = flat;
    }

    //Ticket parameters

    public UserHelperClass(String place, String action, String info, String status, String submissionDate) {
        this.place = place;
        this.action = action;
        this.info = info;
        this.status = status;
        this.submissionDate = submissionDate;
    }

    public UserHelperClass(String name,String surname,String telephone,String submissionDate,String status,String action,String place,String info){

        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.place = place;
        this.action = action;
        this.info = info;
        this.status = status;
        this.submissionDate = submissionDate;
    }

    public UserHelperClass() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
    public String getFlatLetter() {
        return flatLetter;
    }

    public void setFlatLetter(String flatLetter) {
        this.flatLetter = flatLetter;
    }
    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
}
