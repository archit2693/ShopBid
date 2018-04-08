package hackathon.iron_man;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;
import hackathon.iron_man.Model.Cart_model;
import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.tabview.JayBaseAdapter;
import hackathon.iron_man.tabview.MainActivity;
import hackathon.iron_man.tabview.Bean;

/**
 * Created by Iron_Man on 06/04/17.
 */

public class cart extends AppCompatActivity {

    private ListView listview;
    MyTextView total;
    SessionManagement sessionManagement;
    List<Item_fields> subscribed_items = new ArrayList<>();
    ArrayList<Item_fields> allItems = new ArrayList<>();
    ArrayList<Cart_model> arrayItems = new ArrayList<>();
    ArrayList<Item_fields> dummy = new ArrayList<>();
    long sum =0;

    private JayBaseAdapter baseAdapter;
    customfonts.MyTextView ct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hackathon.iron_man.R.layout.cart);
        listview = (ListView) findViewById(hackathon.iron_man.R.id.listview1);
        ct = (customfonts.MyTextView) findViewById(hackathon.iron_man.R.id.cpay);
        total = findViewById(R.id.total2);

        sessionManagement = new SessionManagement(getApplicationContext());

        allItems = sessionManagement.getAllItemsList();
        subscribed_items = sessionManagement.getList();


        for(Item_fields i: allItems){
            for(Item_fields x: subscribed_items) {
                if(i.getName().equals(x.getName())){
                    Log.e(" In cart : ", i.getName());
                    Cart_model c;
                    if(i.getUserId().equals(sessionManagement.getUserDetails().get(SessionManagement.KEY_EMAIL)))
                         c = new Cart_model(i.getName(), i.getImageURL(), "WON", i.getBidValue());
                    else
                        c = new Cart_model(i.getName(), i.getImageURL(), "LOST", i.getBidValue());

                    if(!dummy.contains(i)) {
                        dummy.add(i);
                        arrayItems.add(c);
                        sum += c.getBidValue();
                    }
                }
            }
        }
        sessionManagement.setCartSum(sum);
        total.setText("$ "+sum + "");
        baseAdapter = new JayBaseAdapter(getApplicationContext(), arrayItems) {
        };

        listview.setAdapter(baseAdapter);

        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
