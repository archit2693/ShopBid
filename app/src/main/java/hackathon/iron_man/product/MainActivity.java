package hackathon.iron_man.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import hackathon.iron_man.R;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    SliderLayout mDemoSlider;



//    Typeface fonts1,fonts2;
    private ExpandableHeightGridView gridview;
    private ArrayList<Beanclass> beanclassArrayList;
    private GridviewAdapter gridviewAdapter;








    private int[] IMAGEgrid = {R.drawable.bag1, R.drawable.bag3, R.drawable.bag6, R.drawable.bag4};
    private String[] TITLeGgrid = {"Min 70% off", "Min 50% off", "Min 45% off",  "Min 60% off"};
    private String[] DIscriptiongrid = {"Office bag", "Travel bag", "Causal bag","Class bag"};
    private String[] Dategrid = {"Explore Now!","Grab Now!","Discover now!", "Great Savings!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product);





//        ********GRIDVIEW***********


        gridview = (ExpandableHeightGridView)findViewById(R.id.gridview);
        beanclassArrayList= new ArrayList<Beanclass>();

        for (int i= 0; i< IMAGEgrid.length; i++) {

            Beanclass beanclass = new Beanclass(IMAGEgrid[i], TITLeGgrid[i], DIscriptiongrid[i], Dategrid[i]);
            beanclassArrayList.add(beanclass);

        }
        gridviewAdapter = new GridviewAdapter(MainActivity.this, beanclassArrayList);
        gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);


//


//
//        fonts1 =  Typeface.createFromAsset(MainActivity.this.getAssets(),
//                "fonts/Lato-Light.ttf");
//
//
//
//        fonts2 =  Typeface.createFromAsset(MainActivity.this.getAssets(),
//                "fonts/Lato-Regular.ttf");
//
//
//        TextView t5 =(TextView)findViewById(R.id.title);
//        t5.setTypeface(fonts1);



//         ********Slider*********

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.g1);
        file_maps.put("2",R.drawable.g2);
        file_maps.put("3",R.drawable.g3);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
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
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
