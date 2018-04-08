package hackathon.iron_man;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hackathon.iron_man.Home.MainActivity;
import hackathon.iron_man.Model.Item_fields;

import static java.security.AccessController.getContext;

public class ChoiceActivity extends AppCompatActivity{

    int images[] = {R.drawable.slidertea, R.drawable.slider2, R.drawable.slider3, R.drawable.images_tourntravels};

    GridView gridView;
    CustomGridAdapter customGridAdapter;
    List<GridCardModel> gridCardModelList;
    Button save;
    ArrayList<String> arrayList = new ArrayList<>();
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choices_layout);
        gridView = (GridView)findViewById(R.id.grid_view);
        sessionManagement = new SessionManagement(this);
        save = (Button)findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),arrayList.size()+"",Toast.LENGTH_LONG).show();
                sessionManagement.setInterestsList(arrayList);
                startActivity(new Intent(ChoiceActivity.this, MainActivity.class));
                finish();
            }
        });

        //filling grid cards with data
        setGridItems();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });



    }

    public void setGridItems() {

        //filling the grid cards with own data

        gridCardModelList = new ArrayList<>();
        gridCardModelList.add(new GridCardModel(images[0], "Electronics",false));
        gridCardModelList.add(new GridCardModel(images[1], "Fashion" , false));
        gridCardModelList.add(new GridCardModel(images[2], "Home Decor",false));
        gridCardModelList.add(new GridCardModel(images[3], "Books",true));
        customGridAdapter = new CustomGridAdapter(getApplicationContext(), gridCardModelList, new CustomGridAdapter.VenueAdapterClickCallbacks() {
            @Override
            public void onCheckBoxClick(String p) {
                arrayList.add(p);
            }
        });
        gridView.setAdapter(customGridAdapter);
    }

}
