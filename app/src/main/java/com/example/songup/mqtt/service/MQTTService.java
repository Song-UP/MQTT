package com.example.songup.mqtt.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.songup.MQTTPhoneInfo;
import com.example.songup.mqtt.MqttMesHandle;
import com.google.gson.Gson;
import com.hook.mqtt.MessageEvent;
import com.hook.mqtt.MqttManager;
import com.hook.mqtt.riskClientCallBack;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2018/3/30.
 */

public class MQTTService extends Service {

    AppApplication app;
    Gson gson = new Gson();
    public static final String MQTT_CONN_STATE = "mqtt_conn_state";
//    public static boolean setSubscribed = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        app = (AppApplication) getApplication();

        MqttMesHandle.getInstance().init(this);

        MqttManager.getInstance()
                .setMqttMesListemer(mesListemer)  //监听
                .init(getBaseContext(), MQTTPhoneInfo.getInstance(app).getIMEI())
                .toConnect()
                .startTimer();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        /*try {
            MqttHandler.getInstance().getClient(getBaseContext()).disconnect();
            
        } catch (MqttException e) {
            e.printStackTrace();
        }*/
        super.onDestroy();
        try {
            MqttManager.getInstance().disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    riskClientCallBack.MqttMesListemer mesListemer = new riskClientCallBack.MqttMesListemer() {
        @Override
        public void onConnection(String top, String state) {
            EventBus.getDefault().post(new MessageEvent(top,state));
        }

        @Override
        public void onMesArrived(String top, MqttMessage message) {
            MqttMesHandle.getInstance().handMessage(top, message);
        }

    };

}
