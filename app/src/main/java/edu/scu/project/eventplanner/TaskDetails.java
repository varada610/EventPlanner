package edu.scu.project.eventplanner;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;


import java.io.InputStream;
import java.io.IOException;


public class TaskDetails extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail);

        Bundle bundle = getIntent().getExtras();

        TextView textViewName =(TextView) findViewById(R.id.taskName);
        TextView textViewDesp =(TextView) findViewById(R.id.taskDesc);
        TextView textViewPestonName = (TextView) findViewById(R.id.personName);

        String task = bundle.getString("task");
        String description = bundle.getString("description");
        String personName = bundle.getString("person");
        setTitle(task);


        textViewName.setText(task);
        textViewDesp.setText(description);
        textViewPestonName.setText(personName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.uninstall) {
            Uri packageURI = Uri.parse("package:edu.scu.project.eventplanner");
            Intent deleteIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(deleteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
