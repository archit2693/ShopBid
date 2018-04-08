package hackathon.iron_man.tabview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hackathon.iron_man.R;

/**
 * Created by Iron_Man on 12/11/16.
 */
public class TabFragment1 extends Fragment {

    customfonts.MyTextView ct;
    View view;
    CustomViewPager viewPager;

    public TabFragment1(CustomViewPager viewPager) {
        this.viewPager=viewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragmenttab1, container, false);
        ct=(customfonts.MyTextView)view.findViewById(R.id.order);
        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);

            }
        });
        return view;
    }
}