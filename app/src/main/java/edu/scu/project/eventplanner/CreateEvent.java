package edu.scu.project.eventplanner;

/**
 * Created by Varada on 2/16/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.io.InputStream;

public class CreateEvent extends AppCompatActivity {

    Firebase event = null;
    Event eventObj = new Event();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        FloatingActionButton addPeople = (FloatingActionButton)findViewById(R.id.invitePeople);
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contacts = new Intent(CreateEvent.this, ContactList.class);
                startActivityForResult(contacts, 1);

            }
        });

        FloatingActionButton event = (FloatingActionButton)findViewById(R.id.createBudget);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent budget = new Intent(CreateEvent.this,BudgetActivity.class);
                startActivity(budget);
            }
        });

        FloatingActionButton task = (FloatingActionButton)findViewById(R.id.createTask);
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tasks = new Intent(CreateEvent.this,TaskDivide.class);
                startActivity(tasks);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        try {
            InputStream imgStream = getAssets().open("Add.png");
            Drawable drawable = Drawable.createFromStream(imgStream, null);
            fab.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // CREATE EVENT

                RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup2);
                String color="";
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    int id = radioGroup.getCheckedRadioButtonId();
                    View radioView = radioGroup.findViewById(id);
                    int radioId = radioGroup.indexOfChild(radioView);
                    RadioButton button = (RadioButton) radioGroup.getChildAt(radioId);
                    color = (String) button.getText();
                }

                String var = ((EditText)findViewById(R.id.name)).getText().toString();
                eventObj = new Event("Private",var);
                var = ((EditText)findViewById(R.id.location)).getText().toString();
                eventObj.setLocation(var);
                var = ((EditText)findViewById(R.id.date)).getText().toString();
                eventObj.setDate(var);
                eventObj.setColor(color);

                addEvent(eventObj);
            }
        });
    }

    private void addEvent(Event object)
    {
        String url = "https://crackling-heat-3154.firebaseio.com/Event";
        Firebase nameEvent = new Firebase(url);
        Firebase newRef = nameEvent.child(object.getEventName());
        newRef.setValue(object);
       // Firebase attr = newRef.child("Attributes");
        //attr.setValue(object);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String name=data.getStringExtra("contactName");
                String number=data.getStringExtra("number");
                eventObj.setContacts(new Contact(name,number));
                this.addEvent(eventObj);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
