package hackathon.iron_man.Category;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    List<Item_fields> allItems = new ArrayList<>();
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("BEST DEALS"));
        tabLayout.addTab(tabLayout.newTab().setText("NEW DEALS"));
        tabLayout.addTab(tabLayout.newTab().setText("SUBSCRIBED"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        sessionManagement = new SessionManagement(this);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        PagerAdapter1 adapter = new PagerAdapter1(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }



}

