package edu.scu.project.eventplanner;

import android.content.Intent;
import android.os.Bundle;
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
/**
 * Created by Varada on 2/12/2016.
 */

public class ManageEvents extends AppCompatActivity {

    private ListView eventList=null;
    private List<Event> events = new LinkedList<Event>();
    private Event event = null;
    private CustomEventAdapterList adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventList = (ListView) findViewById(R.id.list_item);
        //Populate the events list from the databse and display it.
        Firebase.setAndroidContext(this);
        String url = "https://crackling-heat-3154.firebaseio.com/Event";
        Firebase user = new Firebase(url);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot userSnapshot) {

                for (DataSnapshot keys : userSnapshot.getChildren()) {
                    String type = (String) keys.child("imageName").getValue();
                    String name = keys.getKey();
                    Event newEvent = new Event(type, name);
                    events.add(newEvent);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        registerForContextMenu(eventList);
        eventList.setOnCreateContextMenuListener(this);


        adapter = new CustomEventAdapterList(this, R.layout.activity_event_list, events);
        eventList.setAdapter(adapter);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View clickView,
                                    int position, long id) {

                String name = events.get(position).getEventName();
                String eventType = events.get(position).getImageName();
                Intent intent = new Intent(ManageEvents.this, EventDetails.class);
                intent.putExtra("name", name);
                intent.putExtra("eventType", eventType);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        {super.onCreateContextMenu(menu, view, menuInfo);
            Toast.makeText(ManageEvents.this, "onCreate ", Toast.LENGTH_LONG);
            getMenuInflater().inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
            {
                int position = info.position;
                String name = events.get(position).getEventName();
                Log.d("oncontextitemSel", name);
                String url = "https://crackling-heat-3154.firebaseio.com/Event"+"/"+name;
                events.remove(position);
                eventList.invalidateViews();
                adapter.notifyDataSetChanged();
                Firebase.setAndroidContext(this);
                Firebase user = new Firebase(url);
                user.removeValue();

                return true;
            }
            default:
                //return super.onContextItemSelected(item);
        }
        return true;
    }
}
