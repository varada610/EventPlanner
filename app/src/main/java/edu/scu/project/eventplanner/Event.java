package edu.scu.project.eventplanner;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Varada on 2/12/2016.
 */
public class Event {
    private String imageName, eventName, location, type, color;
    private String date;
    private List<Contact> contacts;

    public Event() {
        this.imageName = "";//type of event Private,Public
        this.eventName = "";
        contacts = new LinkedList<>();
    }

    public Event(String image, String event) {
        this.imageName = image;
        this.eventName = event;
        contacts = new LinkedList<>();
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String name) {
        this.imageName = name;
    }

    public void setLocation(String name) {
        this.location = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setContacts(Contact contact){this.contacts.add(contact);}
}


