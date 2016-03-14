package edu.scu.project.eventplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {

    TextView nameText = null,typeText= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("eventType");

        nameText = (TextView)findViewById(R.id.namevalue);
        nameText.setText(name);

        typeText = (TextView)findViewById(R.id.typeView);
        typeText.setText(type);
        /*Other event details to be updated from sqlite*/
    }

}
