package edu.scu.project.eventplanner;


public class Task {

    private String task;
    private String person;
    private String description;

    public Task(String task, String person, String description) {
        this.task = task;
        this.person = person;
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String personname) {
        this.person = personname;
    }

    public String getDescription() {
        return description;
    }

}
