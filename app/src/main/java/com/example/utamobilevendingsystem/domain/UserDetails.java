package com.example.utamobilevendingsystem.domain;

import java.util.ArrayList;
import java.util.List;

public class UserDetails {

    private int userId;
    private int utaID;
    private String fName;
    private String lName;
    private String username;
    private String dob;
    private String phoneNummber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String ZIP;

    private List<UserCart> userCart = new ArrayList<UserCart>();
    private  List<Payments> payments = new ArrayList<Payments>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

     public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getUtaID() {
        return utaID;
    }

    public void setUtaID(int utaID) {
        this.utaID = utaID;
    }

    public String getDob() { return dob; }


    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public void setDob(String dob){ this.dob = dob; }

    public String getPhoneNummber() {
        return phoneNummber;
    }

    public void setPhoneNummber(String phoneNummber) {
        this.phoneNummber = phoneNummber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public List<UserCart> getUserCart() {
        return userCart;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", fName=" + fName +
                ", lName=" + lName +
                ", utaID=" + utaID +
                ", dob=" + dob +
                ", phoneNumber='" + phoneNummber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", ZIP='" + ZIP + '\'' +
                '}';
    }
}
