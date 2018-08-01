package com.hook.mqtt;

import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * ---Created by zsl on 2018/7/5 0005.---
 */
public class riskClientCallBack implements MqttCallbackExtended {
	private final MqttManager manager;
	private String TAG = "---riskMqttCallBack--";
	private MqttMesListemer mqttMesListemer;
	public final static String MQTT_CON_STATE = "mqtt_con_state";
	public final static String MQTT_UNBIND_DEVICE = "mqtt_unbind_device";
	public final static String MQTT_FLUSH_SETTING = "flush_setting";
	
	public riskClientCallBack(MqttManager mqttManager) {
		this.manager = mqttManager;
	}
	
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		if (!reconnect){
			//如果不是重连  连接后就订阅
			manager.subscribe();
		}
		if (mqttMesListemer != null)
			mqttMesListemer.onConnection(MQTT_CON_STATE,"true");
		Log.e(TAG, "connectComplete: serverURI="+serverURI );
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		if (mqttMesListemer != null)
			mqttMesListemer.onConnection(MQTT_CON_STATE,"false");
		Log.e(TAG, "connectComplete: cause="+cause );
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		Log.e(TAG, "messageArrived: topic="+topic );
		MqttAndroidClient client = manager.getClient();
		if (client==null||!client.isConnected()){
			manager.toConnect();
		}else if (!manager.isSubscribed()){
			manager.subscribe();
		}

		//TODO: 2018/7/18 使用接口替换
//		manager.handMessage(topic,message);
		if (mqttMesListemer != null) {
//			mqttMesListemer.onMesArrived(topic, message);
			String mes = message.toString();
			mqttMesListemer.onMesArrived(topic, message);
		}
		/**通知服务端消息已被处理*/
		client.acknowledgeMessage(String.valueOf(message.getId()));
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		Log.e(TAG, "deliveryComplete: token="+token );
	}

	public void setMqttMesListemer(MqttMesListemer mqttMesListemer) {
		this.mqttMesListemer = mqttMesListemer;
	}

	public interface MqttMesListemer{
		void onConnection(String top, String state);
		void onMesArrived(String top, MqttMessage message);
	}
}
