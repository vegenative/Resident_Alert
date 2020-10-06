package com.example.resident_alert;

public class UserHelperClass {

    private String email, password, name, surname, telephone, city, postalCode, street, blockNumber;

    public UserHelperClass(String email, String password,String name,String surname, String telephone,
                           String street, String blockNumber, String postalCode,String city) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.blockNumber = blockNumber;
    }

    public UserHelperClass() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }
}
