package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.pubnub.api.*;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-0d99650e-54f4-11e6-aba3-0619f8945a4f");
        pnConfiguration.setPublishKey("pub-c-6f60573f-e6cf-4202-97fe-74bc30620aac");

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

            }

            @Override
            public void message(PubNub pubnub, final PNMessageResult message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        String msg = message.getMessage().getAsString();
                        String[] msgList = msg.split(",");
                        double lat=Double.parseDouble(msgList[0]);
                        double lng=Double.parseDouble(msgList[1]);
                        JSONObject mission = new JSONObject();

                        try {
                            mission.put("lat",lat);
                            mission.put("lng",lng);
                            TextView tv = (TextView)findViewById(R.id.mymsg);
                            tv.setText(String.valueOf(mission.getDouble("lng")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                Toast.makeText(getApplicationContext(),message.getMessage().getAsString(),Toast.LENGTH_SHORT).show()
                    }
                });

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        pubnub.subscribe().channels(Arrays.asList("puravida")).execute();
    }

}
