package hackathon.iron_man.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;


public class TabFragment3 extends Fragment {


    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;
    private View view;
    SessionManagement sessionManagement;
    List<Item_fields> subscribed_items = new ArrayList<>();
    ArrayList<Item_fields> allItems = new ArrayList<>();
    ArrayList<Item_fields> arrayItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab1c, container, false);
        sessionManagement = new SessionManagement(getActivity());

        allItems = sessionManagement.getAllItemsList();
        subscribed_items = sessionManagement.getList();


        for(Item_fields i: allItems){
            for(Item_fields x: subscribed_items) {
                if(i.getName().equals(x.getName())){
                    Log.e(" Added Item : ", i.getName());
                    if(!arrayItems.contains(i))
                        arrayItems.add(i);
                }
            }
        }

        gridview = (ExpandableHeightGridView)view.findViewById(R.id.gridview);
        gridviewAdapter = new GridviewAdapter(getActivity(), arrayItems);
        gridview.setExpanded(true);
        gridview.setAdapter(gridviewAdapter);

        // New onItemClickListener

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(), hackathon.iron_man.Details.MainActivity.class);
                i.putExtra("item",arrayItems.get(position));                                         // Putting Item of ListItem in intent
                i.putExtra("fromtab", "tab3");

                startActivity(i);
            }
        });
        return view;

    }
}