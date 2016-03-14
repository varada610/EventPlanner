package edu.scu.project.eventplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SignOn extends AppCompatActivity {

    private EditText signInName = null;
    private String password ="";
    private String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_on);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        signInName = (EditText)findViewById(R.id.enterName);
        signInName.setText(" ");
        ((EditText)findViewById(R.id.enterPwd)).setText("");

        Button sign = (Button)findViewById(R.id.signin);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = signInName.getText().toString();
                String url = "https://crackling-heat-3154.firebaseio.com/Authenticate";
                Firebase user = new Firebase(url);


                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot userSnapshot) {

                        for (DataSnapshot keys : userSnapshot.getChildren()) {
                            password = (String) keys.getValue();
                            String key = keys.getKey();


                            EditText pd = (EditText)findViewById(R.id.enterPwd);
                            String pwd = pd.getText().toString();

                            Intent login = new Intent(SignOn.this,LoginPage.class);
                            Log.d("start",name);
                            login.putExtra("name", name);
                            startActivity(login);

                            //if (key.equals(name) && password.equals(pwd))
                                //startLogin();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });

    }

    public void startLogin()
    {
        Intent login = new Intent(SignOn.this,LoginPage.class);
        Log.d("start",name);
        login.putExtra("name", name);
        startActivity(login);
    }

}
