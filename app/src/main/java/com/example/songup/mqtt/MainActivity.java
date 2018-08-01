package com.example.songup.mqtt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.songup.mqtt.services.MyMqtt;
import com.example.songup.mqtt.util.MQTTManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.startService(new Intent(this, MyMqtt.class));

        MQTTManager.getInstance(this).connect();

        MQTTManager.getInstance(this).setMessageHandlerCallBack(new MQTTManager.MessageHandlerCallBack() {
            @Override
            public void messageSuccess(String topicName, String s) {
                Toast.makeText(MainActivity.this, topicName, Toast.LENGTH_SHORT  ).show();;
            }
        });
        MQTTManager.getInstance(this).subscribeMsg("1111",1);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MQTTManager.getInstance(MainActivity.this).publish("1111", "哈哈哈哈",true, 1);
            }
        });
    }


}
