package com.hook.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.hook.mqtt.MqttConstant.TAG;

/**
 * Description :接收服务器推送过来的消息
 */

public class MqttCallbackHandler implements MqttCallback {

    private Context context;

    public MqttCallbackHandler(Context context) {
        this.context=context;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.d(TAG,"MqttCallbackHandler/connectionLost");
    }

    /**
     *
     * @param s  主题
     * @param mqttMessage  内容信息
     * @throws Exception
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
//        Log.d(TAG,"MqttCallbackHandler/messageArrived="+s);
        Log.d(TAG,"message=" + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.d(TAG,"MqttCallbackHandler/deliveryComplete");
    }

}
