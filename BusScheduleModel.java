package com.example.lenovo.model;

public class BusScheduleModel {

    String bus_schedule_id="";
    String route_id="";
    String bus_id="";
    String start_time="";
    String end_time="";
    String schedule_status="";

    public BusScheduleModel() {
    }

    public BusScheduleModel(String bus_schedule_id, String route_id, String bus_id, String start_time, String end_time, String schedule_status) {
        this.bus_schedule_id = bus_schedule_id;
        this.route_id = route_id;
        this.bus_id = bus_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.schedule_status = schedule_status;
    }

    public String getBus_schedule_id() {
        return bus_schedule_id;
    }

    public void setBus_schedule_id(String bus_schedule_id) {
        this.bus_schedule_id = bus_schedule_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSchedule_status() {
        return schedule_status;
    }

    public void setSchedule_status(String schedule_status) {
        this.schedule_status = schedule_status;
    }
}

