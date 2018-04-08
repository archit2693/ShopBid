package hackathon.iron_man.Details;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import customfonts.MyTextView;
import hackathon.iron_man.CircleTransform;
import hackathon.iron_man.Model.Item_fields;
import hackathon.iron_man.NotificationPublisher;
import hackathon.iron_man.R;
import hackathon.iron_man.SessionManagement;
import hackathon.iron_man.cart;
import hackathon.iron_man.pusherchat.MainChat;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, View.OnClickListener {
    SliderLayout mDemoSlider;
    Button chatButton;
    MyTextView myTextView, price, bidders, submitBid, subscribe,nameItem, startTimeText,highestBidder;
    String productname;
    LinearLayout linear0, linear1, linear2, linear3, linear4, linear5, linear6, linear20, linear7, linear8,linear10,linear11,linearRating,linearLayout,linearLayout2;
    VideoView videoView;
    Context context = this;
    TextView discription3, discription2, discription1, link1, link2;
    Toolbar toolbar;
    ImageView back;
    EditText myBidValue;
    String fromTab;
    SessionManagement session;
    List<Item_fields> current_subscribed_list = new ArrayList<>();
    long myBid, currentBid;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    //Time
    int Time=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);



        //        For retrieving the product
        Intent i = getIntent();
        fromTab = i.getStringExtra("fromtab");
        final Item_fields item = (Item_fields)i.getSerializableExtra("item");

        // Getting subscribed items list
        session = new SessionManagement(this);
        current_subscribed_list = session.getList();

        Log.e("List ",current_subscribed_list.toString());
        Log.e("endTime ", item.getEndTime());

        // Time Difference Calculations
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time2=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        Date endTime = null;
        Date startTime = null;
        try {
            endTime = format.parse(item.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            startTime = format.parse(item.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date current_time = null;
        try {
            current_time = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final long differenceCE = endTime.getTime() - current_time.getTime();
        final long differenceCS = current_time.getTime() - startTime.getTime();

        Log.e("DifferenceCE : ",String.valueOf(differenceCE));
        Log.e("DifferenceCS : ",String.valueOf(differenceCS));


        // Subscribe Button Functions
        subscribe = findViewById(R.id.subscribe);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_subscribed_list.add(item);
                session.setList(current_subscribed_list);
                subscribe.setText("Subscribed");
                subscribe.setTextColor(R.color.agendacolor);
                subscribe.setClickable(false);
                Log.e("List ",session.getList().toString());
                scheduleNotification(getNotification("Bidding for "+ item.getName()+ " starts at "+item.getStartTime()), 5000);

                /**
                 *     For demo purposes Notification arrives in 5 sec
                 *     which can be set to difference variable defined above for precise notification
                 */
            }
        });

        // Setting up fire base variables

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        final DatabaseReference reference = mFirebaseDatabase.child("items").child("electronics").child("mixer");

        price = findViewById(R.id.bidding_value);
        bidders = findViewById(R.id.bidders);
        nameItem = findViewById(R.id.name_item);
        startTimeText = findViewById(R.id.start_time);

        nameItem.setText(item.getName());
        startTimeText.setText("Duration : "+item.getStartTime() + " - "+item.getEndTime());
        highestBidder = findViewById(R.id.highest_bidder);

        myBidValue = findViewById(R.id.my_bid_edittext);
        submitBid = findViewById(R.id.submit_my_bid);

        Log.e("Refernece link : ", reference.child(item.getName()).toString());
        Log.e("Refernece link : ", reference.child(item.getName()).child("bidValue").toString());


        reference.child(item.getName()).child("bidValue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentBid = (long)dataSnapshot.getValue();
                Log.e("BIDVALUE CHANGED ",currentBid+"");
                price.setText("$ " +currentBid+"");         // Setting bid value from the item recieved
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference.child(item.getName()).child("userId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("Hisgghest bidder ",dataSnapshot.toString());
                highestBidder.setText("Highest Bidder : " +dataSnapshot.getValue()+"");         // Setting bid value from the item recieved
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        linearLayout2=(LinearLayout)findViewById(R.id.linearLayout2);

        if(fromTab.equals("tab3"))
        {

            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            // THE BIDDING PROTOCOL
            submitBid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (myBidValue.getText() != null) {
                        String s = myBidValue.getText().toString();
                        if(!s.contains(".")) {
                            myBid = Long.parseLong(myBidValue.getText().toString());
                            if (differenceCE > 0 ){
                                if(differenceCS > 0) {
                                    if (currentBid < myBid) {
                                        Log.e("Refernece link : ", reference.child(item.getName()).toString());
                                        String current_user = session.getUserDetails().get(SessionManagement.KEY_EMAIL);

                                        // Placing Bid to the server
                                        reference.child(item.getName()).child("bidValue").setValue(myBid);
                                        reference.child(item.getName()).child(" ").setValue(current_user);

                                        Log.e("Refernece link : ", myBid + " value was set for " + item.getName() + " By " + current_user);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please bid higher than the current bid.", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"The Bidding for the product is not started yet.", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"The Bidding for the product is over.", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Please enter integer value.", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });

        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
        }

        toolbar=(Toolbar)findViewById(R.id.toolbar15);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        chatButton = (Button) findViewById(R.id.chatButton);
        linear0 = (LinearLayout) findViewById(R.id.linear0);
        linear20 = (LinearLayout) findViewById(R.id.linear20);
        videoView = (VideoView) findViewById(R.id.video2);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(uri);
        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);

//        myTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(),"You have successfully subscribed for the bidding of this item",Toast.LENGTH_LONG).show();
//                myTextView.setText("SUBSCRIBED");
//                myTextView.setClickable(false);
//            }
//        });


        linear0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear20.setVisibility(View.VISIBLE);
                linear0.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
            }
        });

        linear20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear0.setVisibility(View.VISIBLE);
                linear20.setVisibility(View.GONE);
                videoView.pause();
                videoView.setVisibility(View.GONE);
            }
        });


//                ***********discription**********

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        discription1 = (TextView) findViewById(R.id.discription1);


        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear2.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);
                discription1.setVisibility(View.VISIBLE);

            }
        });

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear2.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
                discription1.setVisibility(View.GONE);


            }
        });

        //                ***********related video**********

        linear7 = (LinearLayout) findViewById(R.id.linear7);
        linear8 = (LinearLayout) findViewById(R.id.linear8);
        link1 = (TextView) findViewById(R.id.link1);
        link2 = (TextView) findViewById(R.id.link2);

        linear7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear8.setVisibility(View.VISIBLE);
                linear7.setVisibility(View.GONE);
                link1.setVisibility(View.VISIBLE);
                link2.setVisibility(View.VISIBLE);

            }
        });

        linear8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear7.setVisibility(View.VISIBLE);
                linear8.setVisibility(View.GONE);
                link1.setVisibility(View.GONE);
                link2.setVisibility(View.GONE);


            }
        });
 //                ***********Rating**********

        linear10 = (LinearLayout) findViewById(R.id.linear10);
        linear11 = (LinearLayout) findViewById(R.id.linear11);
        linearRating = (LinearLayout)findViewById(R.id.linearRating);
        linear10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear11.setVisibility(View.VISIBLE);
                linear10.setVisibility(View.GONE);
                linearRating.setVisibility(View.VISIBLE);

            }
        });

        linear11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear10.setVisibility(View.VISIBLE);
                linear11.setVisibility(View.GONE);
                linearRating.setVisibility(View.GONE);


            }
        });


//                ***********use and care**********

        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        discription2 = (TextView) findViewById(R.id.discription2);


        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear4.setVisibility(View.VISIBLE);
                linear3.setVisibility(View.GONE);
                discription2.setVisibility(View.VISIBLE);

            }
        });

        linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear4.setVisibility(View.GONE);
                linear3.setVisibility(View.VISIBLE);
                discription2.setVisibility(View.GONE);


            }
        });


//                ***********design**********

        linear5 = (LinearLayout) findViewById(R.id.linear5);
        linear6 = (LinearLayout) findViewById(R.id.linear6);
        discription3 = (TextView) findViewById(R.id.discription3);


        linear5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear6.setVisibility(View.VISIBLE);
                linear5.setVisibility(View.GONE);
                discription3.setVisibility(View.VISIBLE);

            }
        });

        linear6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                linear6.setVisibility(View.GONE);
                linear5.setVisibility(View.VISIBLE);
                discription3.setVisibility(View.GONE);


            }
        });


//         ********Item Image*********
        ImageView itemImage = findViewById(R.id.itemImage);
        Glide.with(this).load(item.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemImage);



        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainChat.class);
                startActivity(intent);

            }
        });


        link1=(TextView)findViewById(R.id.link1);
        link1=(TextView)findViewById(R.id.link2);
        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(link1.toString()));
                internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(internetIntent);

            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(link2.toString()));
                internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(internetIntent);
            }
        });
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cartImage:
                Intent intent = new Intent(MainActivity.this, cart.class);
                intent.putExtra("title", productname);
                startActivity(intent);
                break;
            case R.id.reviewChat:
                Intent i = new Intent(MainActivity.this, MainChat.class);
                startActivity(i);
                finish();
                break;

        }
    }
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Bidding Alert !");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.robot);
        return builder.build();
    }
}
