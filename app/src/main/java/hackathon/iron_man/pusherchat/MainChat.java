package hackathon.iron_man.pusherchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import hackathon.iron_man.Details.MainActivity;
import hackathon.iron_man.R;

public class MainChat extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    final String MESSAGES_ENDPOINT = "https://gentle-plateau-99726.herokuapp.com";

    MessageAdapter messageAdapter;
    EditText messageInput;
    Button sendButton;
    String username;
    ListView messagesView;
    ArrayList<Message> arrayList;
    String text;
    Pusher pusher;
    MyThread thread;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pusher);
        username = "Sagar";
        Toast.makeText(this, "Welcome, " + username + "!", Toast.LENGTH_LONG).show();
        messageInput = (EditText) findViewById(R.id.message_input);
        messageInput.setOnKeyListener(this);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
        arrayList = new ArrayList<>();
        messagesView = (ListView) findViewById(R.id.messages_view);
        messageAdapter = new MessageAdapter(this, new ArrayList<Message>());
        messagesView.setAdapter(messageAdapter);
        handler = new Handler();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        pusher = new Pusher("c55be1b1d956db3eedd0", options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                Log.d("Dataaa ::  ", data);
                thread = new MyThread(data);
                handler.post(thread);
                //runOnUiThread(thread);
            }
        });
        pusher.connect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            postMessage();
        }
        if (keyCode == KeyEvent.KEYCODE_BACK)                       // Code for Back Key Press
        {
            Log.e("Back press  :: ", "run");
            handler.removeCallbacks(thread);
            pusher.disconnect();
            finish();
        }
        return true;
    }

    private void postMessage() {

        text = messageInput.getText().toString();

        if (text.equals("")) {
            return;
        }

        RequestParams params = new RequestParams();

        params.put("text", text);
        params.put("name", username);
        params.put("time", new Date().getTime());

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(MESSAGES_ENDPOINT + "/messages", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(final int statusCode, Header[] headers, JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInput.setText("");
                        Log.d("Success", String.valueOf(statusCode));
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        postMessage();
        messageAdapter.add_sender_message(new Message(text, username));
    }


    class MyThread implements Runnable {

        String data;

        public MyThread(String data) {
            this.data = data;
        }

        @Override
        public void run() {
            Gson gson = new Gson();
            Message message = gson.fromJson(data, Message.class);
            messageAdapter.add(message);
            messagesView.setSelection(messageAdapter.getCount() - 1);
        }
    }

}
