package com.example.songup.mqtt.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.songup.mqtt.been.MyMessage;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MQTT长连接
 */
public class MyMqtt extends Service {
    private static final String TAG = MyMqtt.class.getSimpleName();
    public static MqttAndroidClient client;
    private MqttConnectOptions conOpt;

//    private String host = "tcp://10.0.2.2:61613";
//    private String host = "tcp://0.0.0.0:61613"; //模拟器连接，本机地址
    private String host =  "tcp://192.168.1.104:61613"; //真机连接
    private String username = "admin";
    private String password = "password";
    private static String clientId = "test";   //客户端Id要保持不一样，才能区分
    private String myTopic = "topic";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    public void init(){
        //服务器地址，（协议+地址+端口号）
        String url = host;
        client = new MqttAndroidClient(this, url, clientId );
        //设置Mqtt并接受消息
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();
        //清理缓存
        conOpt.setCleanSession(true);
        //设置连接超时时间，秒
        conOpt.setConnectionTimeout(30);
        //设置心跳包发送间隔，秒
        conOpt.setKeepAliveInterval(20);
        //用户名,密码
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());
        //last will message  设置最后的遗嘱（通知订户客户机从 MQTT 服务器异常断开连接）
        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + clientId + "\"}";
        String topic  = myTopic;
        Integer qos = 0;
        boolean retained = false;
        if ( (!message.equals(""))  ||  (!topic.equals("")) ){
            try {
                //最后的遗嘱
                conOpt.setWill(topic, message.getBytes(), qos, retained);
            }catch (Exception e){
                Log.i(TAG, "Exception Occured", e);
                e.printStackTrace();
                doConnect = false; //设置遗嘱出现问题，不再连接
            }
        }
        if (doConnect){
            doClientConnection();;
        }
    }

    //Mqtt的监听并且毁掉,（全局的，连接的过程都在监听，服务器收到消息的监听）
    private MqttCallback mqttCallback = new MqttCallback() {
        @Override  //失去连接
        public void connectionLost(Throwable cause) {
        }
        @Override   //消息到达
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String str1 = new String(message.getPayload());  //以字节数组的形式返回有效载荷(内容)。
            MyMessage mes = new MyMessage();
            mes.setMsg(str1);
            //EventButs 使用通知其他组件
            Toast.makeText(MyMqtt.this, "收到消息啦", Toast.LENGTH_SHORT).show();
        }
        @Override   //失去连接，重连
        public void deliveryComplete(IMqttDeliveryToken token) {
        }
    };

    /**  连接Mqtt服务器 **/
    private void doClientConnection(){
        if (!client.isConnected() && isConnectIsNomarl()){
            try {
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
    //判断手机网络是否正常
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.i(TAG, "MQTT当前网络名称：" + name);
            return true;
        } else {
            Log.i(TAG, "MQTT 没有可用网络");
            return false;
        }
    }

    //Mqtt是否连接成功------------当异步动作完成时，将通知该接口的实现器。
    IMqttActionListener iMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            Log.i(TAG, "连接成功 ");
            try {
                client.subscribe(myTopic,1);  //连接成功，开始订阅主题，设置服务质量
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            exception.printStackTrace();
            //连接失败重连
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        
        super.onDestroy();
    }
}
