package hackathon.iron_man.tabview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class PagerAdapter1 extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    CustomViewPager viewPager;

    public PagerAdapter1(FragmentManager fm, int NumOfTabs,CustomViewPager viewPager) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.viewPager=viewPager;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1 tab1 = new TabFragment1(viewPager);
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2(viewPager);
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}