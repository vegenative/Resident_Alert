package com.example.resident_alert;

public class UserHelperClass {

    private String email, name, surname, telephone, city, street, block, flatLetter, flat, place, action, info;

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

    public UserHelperClass(String place, String action, String info) {
        this.place = place;
        this.action = action;
        this.info = info;
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
}
