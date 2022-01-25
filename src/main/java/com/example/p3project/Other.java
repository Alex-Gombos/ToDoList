package com.example.p3project;

public class Other extends Task implements Done{

    boolean done;

    public Other(String name, String location, String date, int time, String details, boolean done) {
        super(name, location, date, time, details);
        this.done = done;
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
