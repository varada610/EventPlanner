package edu.scu.project.eventplanner;

/**
 * Created by Varada on 2/15/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class ContactList extends AppCompatActivity {

    List<Contact> contactList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.list_item);
        contactList = new LinkedList<>();

        Firebase.setAndroidContext(this);
        String url = "https://crackling-heat-3154.firebaseio.com/People/Contact";
        Firebase user = new Firebase(url);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot userSnapshot) {

                for (DataSnapshot keys : userSnapshot.getChildren()) {
                    String name = keys.getKey();
                    String number = (String)keys.getValue();
                    Contact newContact = new Contact(name, number);
                    contactList.add(newContact);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        CustomContactAdapter adapter = new CustomContactAdapter(this, R.layout.activity_contact_list, contactList);
        registerForContextMenu(listView);
        listView.setOnCreateContextMenuListener(this);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        {super.onCreateContextMenu(menu, view, menuInfo);
            Toast.makeText(ContactList.this, "onCreate ", Toast.LENGTH_LONG);
            getMenuInflater().inflate(R.menu.menu_add, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.add:
            {
                int position = info.position;
                Contact name = contactList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("contactName",name.getName());
                returnIntent.putExtra("number",name.getNumber());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                //listView.invalidateViews();
                //adapter.notifyDataSetChanged();

                return true;
            }
            default:
                //return super.onContextItemSelected(item);
        }
        return true;
    }

}
