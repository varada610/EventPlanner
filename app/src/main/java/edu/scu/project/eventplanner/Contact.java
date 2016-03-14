package edu.scu.project.eventplanner;

import android.graphics.Bitmap;

/**
 * Created by Varada on 2/15/2016.
 */
public class Contact {

    private String name;
    private String number;
    private Bitmap avatar = null;

    public Contact(String name,String number)
    {
        this.name = name;
        this.number = number;
    }

    public String getName(){ return this.name; }

    public String getNumber(){ return this.number; }

    public void setAvatar (Bitmap image) { this.avatar = image;}
}
