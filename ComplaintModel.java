package com.example.lenovo.model;

public class ComplaintModel {

    String complaint_id="";
    String bus_id="";
    String passenger_id="";
    String complaint_detail="";
    String complaint_date="";
    String reply="";
    String reply_date="";

    public ComplaintModel() {
    }

    public ComplaintModel(String complaint_id, String bus_id, String passenger_id, String complaint_detail, String complaint_date, String reply, String reply_date) {
        this.complaint_id = complaint_id;
        this.bus_id = bus_id;
        this.passenger_id = passenger_id;
        this.complaint_detail = complaint_detail;
        this.complaint_date = complaint_date;
        this.reply = reply;
        this.reply_date = reply_date;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public String getComplaint_detail() {
        return complaint_detail;
    }

    public void setComplaint_detail(String complaint_detail) {
        this.complaint_detail = complaint_detail;
    }

    public String getComplaint_date() {
        return complaint_date;
    }

    public void setComplaint_date(String complaint_date) {
        this.complaint_date = complaint_date;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }
}
