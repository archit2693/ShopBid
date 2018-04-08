package hackathon.iron_man;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;


public class CustomGridAdapter extends BaseAdapter {
    private Context mContext;
    List<GridCardModel> dataSet;
    VenueAdapterClickCallbacks venueAdapterClickCallbacks;

    public CustomGridAdapter(Context mContext, List<GridCardModel> dataSet,VenueAdapterClickCallbacks venueAdapterClickCallbacks) {
        this.mContext = mContext;
        this.dataSet = dataSet;
        this.venueAdapterClickCallbacks = venueAdapterClickCallbacks;
    }

    @Override
    public int getCount() {

        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = inflater.inflate(R.layout.card_layout, null);
            TextView tplace = (TextView) grid.findViewById(R.id.place);
            ImageView imageView = (ImageView) grid.findViewById(R.id.image);
            CheckBox checkBox = (CheckBox)grid.findViewById(R.id.chk);
            tplace.setText(dataSet.get(position).getName());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    venueAdapterClickCallbacks.onCheckBoxClick(dataSet.get(position).getName());
                }
            });

            //uploading image in image holder using glide library

            Glide.with(mContext).load(dataSet.get(position).getImage())
                    .dontAnimate()
                    .bitmapTransform(new RoundedCornersTransformation(mContext, 50, 0, RoundedCornersTransformation.CornerType.TOP))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } else {

            grid = (View) convertView;
        }

        return grid;
    }

    public void setFilter(List<GridCardModel> Model) {
        dataSet = new ArrayList<>();
        dataSet.addAll(Model);
        notifyDataSetChanged();
    }

    public interface VenueAdapterClickCallbacks {
        void onCheckBoxClick(String p);
    }
}