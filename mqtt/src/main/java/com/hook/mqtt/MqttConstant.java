package com.hook.mqtt;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MqttConstant {

    public static final String TAG = "jjdd";

    public static final String topicHeader = "Xiake_Risk/Device_";

    public static String clientId; //android_imei

    public static final String hostApi = "tcp://tyt.xiake.com:1883";//"tcp://www.weipinz.com:1883";

    // 订阅 topic，两个下划线之间的为机器人微信号（test-robot-001）, 发送消息的 topic
//    public static String subscribeTopic = "client_test-robot-001_topic";
    public static String subscribeTopic; //topicHeader +imei
    
    /**
     * mqtt
     */
    //一键发朋友圈
    public static final int ONE_KEY_SEND_CIRCLE_OF_FRIENDS = 2;
    //一键点赞
    public static final int ONE_KEY_THUMBS_UP = 3;

    //清除内部数据（全部）
    public static final int CLEAR_APP_DATA = 5;
    //前置语音（功能推后）
    public static final int FOREGROUND_VOICE = 6;

    /****真正开始完成 ********/
    public static final int SYNC_PHONE_ADDRESS = 20; //同步手机号
    public static final int PHONE_SETTING = 30;      //手机设置
    public static final int UNBIND_DEVICE= 10;      //取消绑定
    public static final int UNINSTALAL_WECHAT = 1;   //卸载微信
    public static final int SENSITIVE_WORDS_CHANGE = 70;   //卸载微信

    public static final int SEND_GROUR_FRIENDS = 50;   //发送朋友圈

}
