package hackathon.iron_man.pusherchat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hackathon.iron_man.R;

public class MessageAdapter extends BaseAdapter {

    Context messageContext;
    ArrayList<Message> messageList;
    Message message;

    public MessageAdapter(Context context, ArrayList<Message> messages){
        messageList = messages;
        messageContext = context;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder;
//        if (convertView == null){
            LayoutInflater messageInflater = (LayoutInflater) messageContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            message=(Message) getItem(position);
            Log.e("getView NAMEEE : : : ",message.name+" position  "+String.valueOf(position));


            if(message.name.equals("Server"))
                convertView = messageInflater.inflate(R.layout.message_recieve, null);
            else
                convertView = messageInflater.inflate(R.layout.message_send, null);

            holder = new MessageViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.senderView = (TextView) convertView.findViewById(R.id.message_sender);
            holder.bodyView = (TextView) convertView.findViewById(R.id.message_body);

            convertView.setTag(holder);
//        } else {
//            holder = (MessageViewHolder) convertView.getTag();
//        }

       // Message message = (Message) getItem(position);

        holder.bodyView.setText(message.text);
        holder.senderView.setText(message.name);

        Picasso.with(messageContext).
                load("https://twitter.com/"+message.name+"/profile_image?size=original").
                placeholder(R.mipmap.ic_launcher).
                into(holder.thumbnailImageView);
        return convertView;
    }

    public void add(Message message){
        messageList.add(message);
        notifyDataSetChanged();
        Log.e("Adapter Data : add : ",message.text+"  "+message.name);
    }

    public void add_sender_message(Message message){
        messageList.add(message);
        notifyDataSetChanged();
        Log.e("Adapter Data : send:",message.text+"  "+message.name);
    }



    private static class MessageViewHolder {
        public ImageView thumbnailImageView;
        public TextView senderView;
        public TextView bodyView;
    }
}
