package edu.scu.project.eventplanner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button viewEvents = (Button)findViewById(R.id.viewEvents);
        viewEvents.setText("View/Manage Events");

        Button viewContacts = (Button)findViewById(R.id.viewMyFriends);
        viewContacts.setText("View My Friends");

        TextView name = (TextView)findViewById(R.id.userName);
        name.setText(getIntent().getStringExtra("name").toString());

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.createEvent);
        try {
            InputStream imgStream = getAssets().open("Event.png");
            Drawable drawable = Drawable.createFromStream(imgStream, null);
            fab1.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.createPrivateEvent);
        try {
            InputStream imgStream = getAssets().open("EventPrivate.png");
            Drawable drawable = Drawable.createFromStream(imgStream, null);
            fab2.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();
        }

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,CreateEvent.class);
                startActivity(intent);

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, CreatePublicActivity.class);
                startActivity(intent);

            }
        });

        viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginPage.this,ManageEvents.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

       Button friends =  (Button)findViewById(R.id.viewMyFriends);
       friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginPage.this,ManageContacts.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {
            Intent signOut = new Intent(LoginPage.this,SignOn.class);
            startActivity(signOut);
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
