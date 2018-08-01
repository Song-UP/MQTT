package com.hook.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import static com.hook.mqtt.MqttConstant.TAG;

/**
 * Description :发布回调

 */

public class PublishCallBackHandler implements IMqttActionListener {

    private final Context context;

    public PublishCallBackHandler(Context context) {
        this.context=context;
    }

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        Log.i(TAG, "onSuccess: 发布成功");
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        Log.i(TAG, "onFailure: 发布失败");
    }
}
