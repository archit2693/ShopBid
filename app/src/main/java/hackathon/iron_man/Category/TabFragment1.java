package hackathon.iron_man.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;


public class TabFragment1 extends Fragment {


    private ExpandableHeightGridView gridview;
    private GridviewAdapter gridviewAdapter;
    private View view;

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
                i.putExtra("item",allItems.get(position));
                i.putExtra("fromtab", "tab1");
                startActivity(i);
            }
        });
     return view;

    }
}