package com.example.lenovo.model;

public class LoginModel {

    private String login_id="";
    private String user_name="";
    private String password="";
    private String user_type="";

    public LoginModel() {
    }

    public LoginModel(String login_id, String user_name, String password, String user_type) {
        this.login_id = login_id;
        this.user_name = user_name;
        this.password = password;
        this.user_type = user_type;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
