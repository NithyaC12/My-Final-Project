package com.example.lenovo.model;

public class RouteModel {

    String route_id="";
    String route_name="";
    String route_number="";
    String route_from="";
    String route_to="";

    public RouteModel() {
    }

    public RouteModel(String route_id, String route_name, String route_number, String route_from, String route_to) {
        this.route_id = route_id;
        this.route_name = route_name;
        this.route_number = route_number;
        this.route_from = route_from;
        this.route_to = route_to;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getRoute_number() {
        return route_number;
    }

    public void setRoute_number(String route_number) {
        this.route_number = route_number;
    }

    public String getRoute_from() {
        return route_from;
    }

    public void setRoute_from(String route_from) {
        this.route_from = route_from;
    }

    public String getRoute_to() {
        return route_to;
    }

    public void setRoute_to(String route_to) {
        this.route_to = route_to;
    }
}
