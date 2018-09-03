package com.example.lenovo.model;

public class UserModel {

    String user_id="";
    String login_id="";
    String fname="";
    String sname="";
    String gender="";
    String dob="";
    String place="";
    String phone="";
    String email="";

    public UserModel() {
    }

    public UserModel(String user_id, String login_id, String fname, String sname, String gender, String dob, String place, String phone, String email) {
        this.user_id = user_id;
        this.login_id = login_id;
        this.fname = fname;
        this.sname = sname;
        this.gender = gender;
        this.dob = dob;
        this.place = place;
        this.phone = phone;
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
