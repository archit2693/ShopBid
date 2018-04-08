package hackathon.iron_man.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;

import hackathon.iron_man.R;
import hackathon.iron_man.cart;
import hackathon.iron_man.pusherchat.MainChat;

public class MainFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener, View.OnClickListener {

    private View view;
    SliderLayout mDemoSlider;
    DrawerLayout mDrawer;
    private ListView list, list_nav;
    private ArrayList<Beanclass> Bean;
    private ListbaseAdapter baseAdapter;
    private Toolbar mToolbar;

    private int[] IMAGE = {R.drawable.slidertea, R.drawable.slider2, R.drawable.slider3,};

    private String[] TITLE = {"Electronics", "Fashion", "Home and Decor"};

    private String[] SUBTITLE = {"Great Deals on Electronics! Grab it!", "Great Deals in Fashion! Grab it!", "Great Deals on Home and deco! Grab it!"};

    private String[] SHOP = {"Bid Now", "Bid Now", "Bid Now"};
    MainFragment context = this;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        list = (ListView) view.findViewById(R.id.list);
        // ListView
        Bean = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            Beanclass bean = new Beanclass(IMAGE[i], TITLE[i], SUBTITLE[i], SHOP[i]);
            Bean.add(bean);
        }
        baseAdapter = new ListbaseAdapter(getActivity(), Bean) {
        };
        list.setAdapter(baseAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getActivity(), hackathon.iron_man.Category.MainActivity.class);
                        i.putExtra("title", Bean.get(0).getTitle());                                         // Putting Title of ListItem in intent
                        startActivity(i);

                        break;
                    case 1:
                        Intent intent = new Intent(getActivity(), hackathon.iron_man.Category.MainActivity.class);
                        intent.putExtra("title", Bean.get(1).getTitle());                                            // Putting Title of ListItem in intent
                        startActivity(intent);

                        break;
                    case 2:
                        Intent in = new Intent(getActivity(), hackathon.iron_man.Category.MainActivity.class);
                        in.putExtra("title", Bean.get(2).getTitle());                                        // Putting Title of ListItem in intent
                        startActivity(in);

                        break;

                }
            }
        });

        // Slider
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.s1);
        file_maps.put("2", R.drawable.s2);
        file_maps.put("3", R.drawable.s3);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new ChildAnimationExample());
        mDemoSlider.setDuration(4000);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_home:
                Intent intent = new Intent(getActivity(), cart.class);
                intent.putExtra("title", "");
                startActivity(intent);
                break;
            case R.id.reviewChat:
                Intent i = new Intent(getActivity(), MainChat.class);
                startActivity(i);
                break;

        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}