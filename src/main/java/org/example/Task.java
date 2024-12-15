package org.example;

public class Task {
    private String description;
    private State state;

    public Task() {}

    public Task(String description, State state) {
        this.description = description;
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return  description +
                ", " + state;
    }
}
