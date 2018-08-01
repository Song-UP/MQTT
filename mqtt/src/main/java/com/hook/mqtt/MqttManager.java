package com.hook.mqtt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.hook.mqtt.MqttConstant.clientId;
import static com.hook.mqtt.MqttConstant.hostApi;
import static com.hook.mqtt.MqttConstant.subscribeTopic;
import static com.hook.mqtt.MqttConstant.topicHeader;

/**
 * ---Created by zsl on 2018/7/5 0005.---
 */
public class MqttManager {
	private static MqttManager mqttManager;
	private MqttAndroidClient client;
	private MqttConnectOptions conOpt;
	private Context mContext;
	private boolean isSubscribed;
	private Disposable disposable;
	private Gson mGson;
	private riskClientCallBack.MqttMesListemer mqttMesListemer;
	
	public static MqttManager getInstance() {
		if (mqttManager == null) {
			synchronized (MqttManager.class) {
				if (mqttManager == null) {
					mqttManager = new MqttManager();
				}
			}
		}
		return mqttManager;
	}
	
	public MqttManager init(Context context,String imei){
		if (TextUtils.isEmpty(imei))return this;
		if (mContext == null) mContext = context;
		if (TextUtils.isEmpty(clientId)) clientId = "android_" + imei;
		if (TextUtils.isEmpty(subscribeTopic)) subscribeTopic = topicHeader + imei;
		mGson = new GsonBuilder().disableHtmlEscaping().create();
		return this;
	}
	
	public void startTimer(){
		disposable = Observable.interval(10, TimeUnit.SECONDS)
				.subscribe(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						if (client == null || !client.isConnected()) {
							toConnect();
						}else if (!isSubscribed()){
							subscribe();
						}
					}
				});
	}
	
	//连接的方法
	public MqttManager toConnect() {
		
		if (client != null && client.isConnected()) {
			return this;
		}
		/**
		 * 获取连接配置
		 */
		conOpt = getMqttConnectOptions();
		
		/**
		 * 创建连接对象
		 */
		client = new MqttAndroidClient(mContext, hostApi, clientId);
		riskClientCallBack riskClientCall = new riskClientCallBack(this);
		if (mqttMesListemer != null)
			riskClientCall.setMqttMesListemer(mqttMesListemer);

		/**
		 * 连接后设计一个回调
		 */
		client.setCallback(riskClientCall);
		/**
		 * 开始连接服务器，参数：ConnectionOptions,  IMqttActionListener
		 */
		try {
			client.connect(conOpt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return this;
	}
	
	@NonNull
	private MqttConnectOptions getMqttConnectOptions() {
		if (conOpt==null){
			/**
			 * 连接的选项
			 */
			conOpt = new MqttConnectOptions();
			/**设计连接超时时间  单位 秒*/
			conOpt.setConnectionTimeout(30);
			/**设计心跳间隔时间  单位 秒*/
			conOpt.setKeepAliveInterval(60);
			/** 设置自动重连 */
			conOpt.setAutomaticReconnect(true);
			/**清除 session 每次都以新的身份连接*/
			conOpt.setCleanSession(true);
			conOpt.setUserName("xiake_cloud_push_user");
			conOpt.setPassword("xiake_cloud_push_user@2018".toCharArray());
		}
		
		return conOpt;
	}
	
	/**
	 * 获取 client
	 * @return
	 */
	public MqttAndroidClient getClient(){
		return client;
	}
	
	public void setSubscribed(boolean b) {
		isSubscribed = b;
	}
	
	public boolean isSubscribed(){
		return isSubscribed;
	}
	
	public void subscribe() {
		
		try {
			client.subscribe(subscribeTopic,2,null,new riskSubcribeCallBack(this));
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 断开 mqtt
	 */
	public void disconnect() throws MqttException {
		if (disposable!=null&&!disposable.isDisposed()){
			disposable.dispose();
			disposable = null;
		}
		if (client!=null&&client.isConnected()){
			client.disconnect();
		}
		isSubscribed = false;
	}

	public MqttManager setMqttMesListemer(riskClientCallBack.MqttMesListemer mqttMesListemer) {
		this.mqttMesListemer = mqttMesListemer;
		return this;
	}



}
