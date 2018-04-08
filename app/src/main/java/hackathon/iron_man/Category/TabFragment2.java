package hackathon.iron_man.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;



public class TabFragment2 extends Fragment {


    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;

    private int[] IMAGEgrid = {R.drawable.pik1, R.drawable.pik2, R.drawable.pik3, R.drawable.pik4, R.drawable.pik1, R.drawable.pik2,};
    private String[] TITLeGgrid = {"Min 70% off", "Min 50% off", "Min 45% off",  "Min 60% off", "Min 70% off", "Min 50% off"};
    private String[] DIscriptiongrid = {"Wrist Watches", "Belts", "Sunglasses","Perfumes", "Wrist Watches", "Belts"};
    private String[] Dategrid = {"Bid Now!","Bid Now!","Bid Now!", "Bid Now!", "Bid Now!","Bid Now!"};
    SessionManagement sessionManagement;
    ArrayList<Item_fields> allItems = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab1c, container, false);
        sessionManagement = new SessionManagement(getActivity());
        allItems = sessionManagement.getAllItemsList();
        gridview = (ExpandableHeightGridView)view.findViewById(R.id.gridview);

        gridviewAdapter = new GridviewAdapter(getActivity(), allItems);
        gridview.setExpanded(true);
        gridview.setAdapter(gridviewAdapter);

        // New onItemClickListener

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(), hackathon.iron_man.Details.MainActivity.class);
                i.putExtra("item",allItems.get(position));                                         // Putting Title of ListItem in intent
                i.putExtra("fromtab", "tab2");
                startActivity(i);
            }
        });
        return view;

    }
}