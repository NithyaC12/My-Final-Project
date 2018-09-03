package com.example.lenovo.model;

public class FeedbackModel {

    String feedback_id="";
    String bus_id="";
    String passenger_id="";
    String feedback_detail="";
    String feedback_date="";

    public FeedbackModel() {
    }

    public FeedbackModel(String feedback_id, String bus_id, String passenger_id, String feedback_detail, String feedback_date) {
        this.feedback_id = feedback_id;
        this.bus_id = bus_id;
        this.passenger_id = passenger_id;
        this.feedback_detail = feedback_detail;
        this.feedback_date = feedback_date;
    }

    public String getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(String feedback_id) {
        this.feedback_id = feedback_id;
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

    public String getFeedback_detail() {
        return feedback_detail;
    }

    public void setFeedback_detail(String feedback_detail) {
        this.feedback_detail = feedback_detail;
    }

    public String getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(String feedback_date) {
        this.feedback_date = feedback_date;
    }
}
