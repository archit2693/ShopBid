package hackathon.iron_man;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hackathon.iron_man.Activities.RegisterActivity;
import hackathon.iron_man.Home.MainActivity;
import hackathon.iron_man.Model.Item_fields;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 4000;
    SessionManagement session;
    private DatabaseReference check;
    private FirebaseDatabase mFirebaseInstance;
    List<Item_fields> allItems = new ArrayList<>();
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        session = new SessionManagement(this);
        sessionManagement = new SessionManagement(this);
        // Fetching data from the server to display

        // Initialising Fire base references
        mFirebaseInstance = FirebaseDatabase.getInstance();
        check = mFirebaseInstance.getReference();
        // fetching articles from server

        check.child("items").child("electronics").child("mixer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Splash Activity : ", "Inside Here");
                allItems = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    allItems.add(postSnapshot.getValue(Item_fields.class));

                    Log.e("size of my list ", String.valueOf(allItems.size()));

                    Log.e("image url ", allItems.get(0).getImageURL());

                }
                sessionManagement.setAllItemsList(allItems);
                Log.e("SUPER LOG : ", sessionManagement.getAllItemsList().size()+"");
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }

        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.e("values",session.getUserDetails().get(SessionManagement.KEY_EMAIL));




                // Checking login
                if (session.getUserDetails().get(SessionManagement.KEY_EMAIL) == null || session.getUserDetails().get(SessionManagement.KEY_PASS)== null) {
                    List<Item_fields> empty_list = new ArrayList<>();
                    session.setList(empty_list);
                    startActivity(new Intent(SplashScreen.this, RegisterActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);


    }
}

