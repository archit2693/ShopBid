package hackathon.iron_man.Category;

/**
 * Created by Iron_Man on 12/11/16.
 */

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.R;



public class GridviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item_fields> bean;
    public GridviewAdapter(Context context, ArrayList<Item_fields> bean) {
        this.bean = bean;
        this.context = context;
    }
    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.image1 = (ImageView) convertView.findViewById(R.id.image1);
            viewHolder.title1 = (TextView) convertView.findViewById(R.id.title1);
            viewHolder.discription1 = (TextView) convertView.findViewById(R.id.description1);
            viewHolder.date1 = (TextView) convertView.findViewById(R.id.date1);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Item_fields bean = (Item_fields) getItem(position);
        Glide.with(context)
                .load(bean.getImageURL()).into(viewHolder.image1);
        viewHolder.title1.setText(bean.getName());
        viewHolder.discription1.setText("$ "+bean.getBidValue());
        viewHolder.date1.setText("Starts at: "+bean.getStartTime());

        return convertView;
    }

    private class ViewHolder {
        ImageView image1;
        TextView title1;
        TextView discription1;
        TextView date1;

    }
}
