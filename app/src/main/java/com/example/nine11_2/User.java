package com.example.nine11_2;

public class User {

   public static String SSN  , phoneNumber , fullName ;
   public static String password;

    public User(String phoneNumber, String fullName, String password) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.password = password;
    }

    public User(String phoneNumber, String fullName) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }

    public User() {}

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
