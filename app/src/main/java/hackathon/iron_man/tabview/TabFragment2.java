package hackathon.iron_man.tabview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;

/**
 * Created by Iron_Man on 12/11/16.
 */
@SuppressLint("ValidFragment")
public class TabFragment2 extends Fragment {

    customfonts.MyTextView ct,total;
    View view;
    CustomViewPager viewPager;

    public TabFragment2(CustomViewPager viewPager) {
        this.viewPager=viewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragmenttab2, container, false);
        ct=(customfonts.MyTextView)view.findViewById(R.id.order);
        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);

            }
        });
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        total = view.findViewById(R.id.total2);
        total.setText("$ "+ sessionManagement.getCartSum());
        return view;
    }
}