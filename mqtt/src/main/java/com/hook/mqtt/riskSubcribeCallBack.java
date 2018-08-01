package com.hook.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import static com.hook.mqtt.MqttConstant.TAG;

/**
 * Description :订阅回调
 */

public class riskSubcribeCallBack implements IMqttActionListener {
    
    private final MqttManager manager;
    
    /**
     * 构造器
     * @param manager
     */
    public riskSubcribeCallBack(MqttManager manager) {
        this.manager=manager;
    }

    /**
     * 订阅成功
     * @param iMqttToken
     */
    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        Log.e(TAG, "onSuccess: 订阅成功");
        manager.setSubscribed(true);
    }

    /**
     * 订阅失败
     * @param iMqttToken
     * @param throwable
     */
    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        Log.e(TAG, "onSuccess: 订阅失败");
        manager.setSubscribed(false);
    }
}
