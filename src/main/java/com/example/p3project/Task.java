package com.example.p3project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task{
    private final SimpleStringProperty name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty date;
    private final SimpleStringProperty details;
    int time; // in hours


    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleStringProperty getLocation() {
        return location;
    }

    public SimpleStringProperty getDate() {
        return date;
    }
    public SimpleStringProperty getDetails() {
        return details;
    }

    public int getTime() {
        return time;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setTime(int time) {
        this.time = time;
    }
    public StringProperty nameProperty(){
        return name;
    }
    public StringProperty dateProperty(){
        return date;
    }
    public StringProperty locationProperty(){
        return location;
    }
    public StringProperty detailsProperty(){return details;}

    public Task(String name, String location, String date, int time, String details) {
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
        this.date = new SimpleStringProperty(date);
        this.time = time;
        this.details = new SimpleStringProperty(details);
    }

    public void setDetails(String newValue) {
        details.set(newValue);
    }


}
