package edu.scu.project.eventplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TaskDivide extends AppCompatActivity {   //ActionBarActivity
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_divide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Event Planner");
        final List<edu.scu.project.eventplanner.Task> tasks = new ArrayList<>();
        tasks.add(new Task ("Rent a place", "Michael", "Rent a 5 bedroom nice cabin in Taho"));
        tasks.add(new Task ("Purchase Food", "Sara", "Purchase marineated meat & chicken, with salad, bread and fruits."));
        tasks.add(new Task ("Purchase Drink", "Tom", "Purchase Pepsi, Orange Juice and wine"));
        tasks.add(new Task("Prepare Entertainment", "Tina", "Bring a set of tennis rocket, a football and a set of playing cards"));
        tasks.add(new Task("Prepare Utensils", "Maya", "Purchase plates, spoons, forks, knives and cups "));

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new CustomAdapter(this, R.layout.custom_row, tasks));

        FloatingActionButton addTask = (FloatingActionButton)findViewById(R.id.fab);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tasks = new Intent(TaskDivide.this, AddtaskDetails.class);
                startActivity(tasks);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = tasks.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("task", task.getTask());
                bundle.putString("person", task.getPerson());
                bundle.putString("description", task.getDescription());
                if (position == tasks.size() - 1) {
                    showAlert(view, bundle);
                    return;
                }
                Intent intent = new Intent(TaskDivide.this, TaskDetails.class);
                intent.putExtras(bundle);
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
        if (id == R.id.uninstall) {
            Uri packageURI = Uri.parse("package:com.example.angine.Task");
            Intent deleteIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(deleteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlert(View view, final Bundle bundle) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TaskDivide.this);
        alertDialogBuilder.setMessage("You are almost there!! This is the last task. Are you ready to go!!");
        alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                Intent intent = new Intent(getApplicationContext(),TaskDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

/*
Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        startActivity(new Intent(this,add_task_detail.class));
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/

       /* fabImageButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        //here
        startActivity(new Intent(this,NewActivity.class));
        }
        });*/