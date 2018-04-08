package hackathon.iron_man.tabview;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;
import hackathon.iron_man.Model.Cart_model;
import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;

/**
 * Created by Iron_Man on 12/11/16.
 */
public class TabFragment3 extends Fragment {


    private  View view;
    MyTextView total;
    private ListView listview;
    SessionManagement sessionManagement;
    List<Item_fields> subscribed_items = new ArrayList<>();
    ArrayList<Item_fields> allItems = new ArrayList<>();
    ArrayList<Cart_model> arrayItems = new ArrayList<>();
    ArrayList<Item_fields> dummy = new ArrayList<>();

    private JayBaseAdapter baseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab3, container, false);


        listview = (ListView)view.findViewById(R.id.listview);
        sessionManagement = new SessionManagement(getActivity());

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
                    }
                }
            }
        }


        SessionManagement sessionManagement = new SessionManagement(getActivity());
        total = view.findViewById(R.id.total2);
        total.setText("$ "+ sessionManagement.getCartSum());
        baseAdapter = new JayBaseAdapter(getActivity() , arrayItems) {
        };

        listview.setAdapter(baseAdapter);


//
//        fonts1 =  Typeface.createFromAsset(TabFragment3.this.getAssets(),
//                "fonts/Lato-Light.ttf");
//
//
//
//        fonts2 =  Typeface.createFromAsset(TabFragment3.this.getAssets(),
//                "fonts/Lato-Regular.ttf");
//
//
//
//
//        TextView t4 =(TextView)findViewById(R.id.shopping);
//        t4.setTypeface(fonts2);
//        TextView t5 =(TextView)findViewById(R.id.pay);
//        t5.setTypeface(fonts1);




        return  view;

    }
}