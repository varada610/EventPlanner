package edu.scu.project.eventplanner;
/**
 * Created by Varada on 2/16/2016.
 */
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ManageContacts extends AppCompatActivity {

    ListView listView = null;
    List<Contact> contactList = null;
    Cursor cursor = null;
    Firebase dataRef;
    CustomContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_manage_contacts);
        }
        catch(android.view.InflateException e) {Log.d("view-varada",e.getMessage());}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        contactList = new LinkedList<>();
        String url = "https://crackling-heat-3154.firebaseio.com/People/Contact";
        Firebase user = new Firebase(url);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot userSnapshot) {

                for (DataSnapshot keys : userSnapshot.getChildren()) {
                    String number = (String) keys.getValue();
                    String name = keys.getKey();
                    Contact newContact = new Contact(name, number);
                    contactList.add(newContact);
                }
                Log.d("coen-nu", String.valueOf(userSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Log.d("coen-name", "tag1");


        Button search = (Button)findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText text = (EditText) findViewById(R.id.name);
                checkAndAddContact(text.getText().toString());
            }
        });

        listView = (ListView)findViewById(R.id.list_item);



        adapter = new CustomContactAdapter(this, R.layout.activity_contact_list, contactList);
        registerForContextMenu(listView);
        listView.setOnCreateContextMenuListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        {super.onCreateContextMenu(menu, view, menuInfo);
            Toast.makeText(ManageContacts.this, "onCreate " , Toast.LENGTH_LONG);
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
                String name = contactList.get(position).getName();
                Log.d("oncontextitemSel",name);
                contactList.remove(position);
                String url = "https://crackling-heat-3154.firebaseio.com/People"+"/Contact/"+name;
                Firebase user = new Firebase(url);
                user.removeValue();
                listView.invalidateViews();
                adapter.notifyDataSetChanged();

                return true;
            }
            default:
                //return super.onContextItemSelected(item);
        }
        return true;
    }

    public boolean checkAndAddContact(String contactName)
    {
        String name, phoneNumber="";
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if(cursor.getCount() >0)
        {
            cursor.moveToNext();
            do
            {
                name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
            }while(!name.equals(contactName) && cursor.moveToNext());

            if(name.equals(contactName)) {

                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                    }
                }
                Contact contact = new Contact(name, phoneNumber);
                Firebase.setAndroidContext(this);
                String url = "https://crackling-heat-3154.firebaseio.com/People/Contact";
                dataRef = new Firebase(url);
                Firebase newRef = dataRef.child(contact.getName());
                newRef.setValue(contact.getNumber());

                Iterator<Contact> contactIterator = contactList.iterator();
                boolean bool =true;
                while(contactIterator.hasNext())
                {Contact check = contactIterator.next();
                if ((check.getName()).equals(contact.getName()) )
                {
                    bool = false;
                }}
                if(bool) {
                    contactList.add(contact);
                    listView.invalidateViews();
                    adapter.notifyDataSetChanged();
                }

                return true;
            }
            else {Toast.makeText(getApplicationContext(),"No such name exists",Toast.LENGTH_SHORT);  return false;}
        }
        return false;
    }
    }


