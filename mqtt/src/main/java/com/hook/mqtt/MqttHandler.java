package com.hook.mqtt;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.lang.reflect.UndeclaredThrowableException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import static com.hook.mqtt.MqttConstant.TAG;
import static com.hook.mqtt.MqttConstant.clientId;
import static com.hook.mqtt.MqttConstant.hostApi;
import static com.hook.mqtt.MqttConstant.subscribeTopic;
import static com.hook.mqtt.MqttConstant.topicHeader;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MqttHandler {

    static MqttAndroidClient client;

    private Context mContext;

    private static MqttHandler mqttHandler;

    public static MqttHandler getInstance() {
        if (mqttHandler == null) {
            synchronized (MqttHandler.class) {
                if (mqttHandler == null) {
                    mqttHandler = new MqttHandler();
                }
            }
        }
        return mqttHandler;
    }


    //连接的方法
    public void toConnect(Context context, String imei, Timer timer) {
        if (TextUtils.isEmpty(imei))return ;
        if (mContext == null) mContext = context;
        if (TextUtils.isEmpty(clientId)) clientId = "android_" + imei;
        if (TextUtils.isEmpty(subscribeTopic)) subscribeTopic = topicHeader + imei;

        if (client != null && client.isConnected()) {
            return ;
        }

        /**
         * 连接的选项
         */
        MqttConnectOptions conOpt = new MqttConnectOptions();
        /**设计连接超时时间  单位 秒*/
        conOpt.setConnectionTimeout(30);
        /**设计心跳间隔时间  单位 秒*/
        conOpt.setKeepAliveInterval(60);
        /**清除 session */
        conOpt.setCleanSession(true);
        /** 设置自动重连 */
        conOpt.setAutomaticReconnect(true);
        conOpt.setUserName("xiake_cloud_push_user");
        conOpt.setPassword("xiake_cloud_push_user@2018".toCharArray());
        /**
         * 创建连接对象
         */
        client = getClient(context);

        /**
         * 连接后设计一个回调
         */
//        client.setCallback();
        /**
         * 开始连接服务器，参数：ConnectionOptions,  IMqttActionListener
         */
        try {
            client.connect(conOpt, null, iMqttActionListener);
            // 链接成功取消定时器
            if (timer != null) timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 client
     * @param context
     * @return
     */
    public MqttAndroidClient getClient(Context context){
        if (client == null) {
            client = new MqttAndroidClient(context, hostApi, clientId);
        }
        return client;
    }

    IMqttActionListener iMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            MyHelper.SaveOne("SLSuccess", sf.format(new Date())+"");
            Toast.makeText(mContext,"mqtt---- 连接成功",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onSuccess: 连接成功");
            /**连接成功后订阅*/
            if (mContext != null) toSubscribe(subscribeTopic, mContext);
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String msg = null;
            if (exception instanceof UndeclaredThrowableException){
                Throwable targetEx = ((UndeclaredThrowableException) exception).getUndeclaredThrowable();
                if (targetEx != null){
                    msg = targetEx.getMessage();
                }
            } else {
                msg = exception.getMessage();
            }

            MyHelper.SaveOne("SLfail",sf.format(new Date())+"  ********  "+msg);
            Toast.makeText(mContext,"mqtt 连接失败，尝试重新连接！" + exception.getMessage() ,Toast.LENGTH_SHORT).show();
//            if (mContext != null && !TextUtils.isEmpty(clientId)) toConnect(mContext,clientId,null);
        }
    };

    //订阅的方法
    private void toSubscribe(String subTopic, Context context) {
        //获取订阅的主题
        if (subTopic == null || "".equals(subTopic)) {
            Toast.makeText(context, "请输入订阅的主题", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] split = subTopic.split(",");
        /**一共有多少个主题*/
        int length = split.length;
        String[] subTopics = new String[length];//订阅的主题
        int[] qos = new int[length];// 服务器的质量
        for (int i = 0; i < length; i++) {
            subTopics[i] = split[i];
            qos[i] = 2;
        }

        /*if (client != null) {
            try {
                if (length > 1) {
                    *//**订阅多个主题,服务的质量默认为 2 *//*
                    client.subscribe(subTopics, qos, null, new riskSubcribeCallBack(context));
                } else {
                    *//**订阅一个主题，服务的质量默认为 0 *//*
                    client.subscribe(subTopic, 2, null, new riskSubcribeCallBack(context));
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "订阅成功 哈哈");
        } else {
            Log.e(TAG, "MqttAndroidClient==null");
        }*/
    }


    public void toPublish(String pubMessage) {
        toPublish(subscribeTopic,pubMessage);
    }

    //发布的方法
    public void toPublish(String pubTopic, String pubMessage) {
        Log.e("toPublish: ", pubTopic);
        /**消息的服务质量*/
        int qos = 0;
        /**消息是否保持*/
        boolean retain = false;
        /**要发布的消息内容*/
        byte[] message = pubMessage.getBytes();
        if (pubTopic != null && !"".equals(pubTopic)) {
            if (client != null && mContext != null) {
                try {
                    /**发布一个主题:如果主题名一样不会新建一个主题，会复用*/
                   client.publish(pubTopic, message, qos, retain, null, new PublishCallBackHandler(mContext));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("mqtt", "MqttAndroidClient==null");
            }
        } else {
            if (mContext != null) Toast.makeText(mContext, "发布的主题不能为空", Toast.LENGTH_SHORT).show();
        }
    }

}
