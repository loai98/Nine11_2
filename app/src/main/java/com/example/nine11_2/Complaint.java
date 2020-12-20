package com.example.nine11_2;

public class Complaint {
    String department , type , discreption , city , image ,date;
    User user ;
    Address address ;

    public Complaint() {
    }

    public Complaint(String department, String type, String discreption , String city, String image  , String date , Address address) {
        this.department = department;
        this.type = type;
        this.discreption = discreption;
        this.city = city;
        this.image = image;
        this.date = date;
        this.address =address;
    }


    public String getImage() {
        return image;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartment() {
        return department;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscreption() {
        return discreption;
    }

    public void setDiscreption(String discreption) {
        this.discreption = discreption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
