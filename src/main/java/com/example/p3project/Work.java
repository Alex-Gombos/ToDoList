package com.example.p3project;

public class Work extends Task implements Urgent, Done {

    boolean urgent, done;

    public Work(String name, String location, String date, int time, String details, boolean status, boolean done) {
        super(name, location, date, time, details);
        this.urgent = status;
        this.done=done;
    }


    @Override
    public boolean isUrgent() {
        return urgent;
    }

    @Override
    public void toggleUrgent() {
        urgent= !urgent;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void toggleDone() {
        done = !done;
    }
}
