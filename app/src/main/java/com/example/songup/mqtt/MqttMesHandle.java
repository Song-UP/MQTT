package com.example.songup.mqtt;

import android.content.Context;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * Created by Administrator on 2018/7/20.
 */

public class MqttMesHandle {

    Context mContext;
    Gson mGson = new Gson();
//    public ActionUi actionUi;

    private static MqttMesHandle mqttManager;
//    MultiFileDown multiFileDown;

    public MqttMesHandle(){
//        actionUi = new ActionUi(AppApplication.getInstance(), AppApplication.getInstance());
//        multiFileDown = new MultiFileDown(AppApplication.getInstance().getMyOkhttp());
    }

    public static MqttMesHandle getInstance() {
        if (mqttManager == null) {
            synchronized (MqttMesHandle.class) {
                if (mqttManager == null) {
                    mqttManager = new MqttMesHandle();
                }
            }
        }
        return mqttManager;
    }

    public void init(Context context){
        this.mContext = context;

    }

    /**
     * 处理服务端发过来的消息
     * @param topic 消息的 topic（服务端消息发送对象）
     * @param message 消息主体
     */
    public void handMessage(String topic, MqttMessage message) {
//        if (TextUtils.isEmpty(topic)||message==null)
//            return;
//        if (!topic.equals(MqttConstant.subscribeTopic))
//            return;
//
//        String msg = message.toString();
//
//        // TODO: 2018/7/5 0005
//        try {
//            final ServerMessage serverMessage = mGson.fromJson(msg, ServerMessage.class);
//            int type = serverMessage.getType();
//            switch (type){
//                case MqttConstant.UNBIND_DEVICE:
//                    unbindDevice();
//                    break;
//                case MqttConstant.SYNC_PHONE_ADDRESS:
//                    syncPhoneAddress();
//                    break;
//                case MqttConstant.PHONE_SETTING:
//                    changeSetting(serverMessage);
//                    EventBus.getDefault().post(new MessageEvent(riskClientCallBack.MQTT_FLUSH_SETTING,"true"));
//                    break;
//
//                case MqttConstant.UNINSTALAL_WECHAT:
//                    uninstall("com.tencent.mm");
//                    break;
//                case MqttConstant.SENSITIVE_WORDS_CHANGE:
//                    requestSentive(mContext,1);
//                    requestSentive(mContext,10);
//                    break;
//
//                case MqttConstant.SEND_GROUR_FRIENDS:
//                    switch (serverMessage.getFriendsCircleType()){
//                        case 1: //文字
//                            actionUi.openSendsnslineUi(msg);
//                            break;
//                        case 10: //文字，图片
//                            if (serverMessage.getFriendsCircleFileIds() != null) {
//                                String currentToken = MyHelper.GetString(Constant.getInstance().Token, "");
//                                for (int i = 0; i < serverMessage.getFriendsCircleFileIds().size(); i++) {
//                                    String url = UtilHelper.getImageUrl(serverMessage.getFriendsCircleFileIds().get(i), currentToken);
//                                    serverMessage.getFriendsCircleFileIds().set(i,url);
//                                }
//                            }
//                            multiFileDown
//                                    .urls(serverMessage.getFriendsCircleFileIds())
//                                    .baseListName(MyHelper.getFilePath() + "photo/")
//                                    .enqueue(new MultiFileDown.MulitDownloadListener() {
//                                        @Override
//                                        public void finishDown(ArrayList<String> outList) {
//                                            serverMessage.setFriendsCircleFileIds(outList);
//                                            String msg = mGson.toJson(serverMessage);
//                                            actionUi.openSendsnslineUi(msg);
//                                        }
//                                        @Override
//                                        public void failer() {
//                                            ToastUtil.showShort(mContext, "发布朋友圈失败，请检查网络");
//                                        }
//                                    });
//                            break;
//                        case 20: //视频
//                            break;
//                    }
//
//
//                    break;
//
//
////                case MqttConstant.ONE_KEY_SEND_CIRCLE_OF_FRIENDS:
////                    oneKeySendCircleOfFriends(content);
////                    break;
////                case MqttConstant.ONE_KEY_THUMBS_UP:
////                    oneKeyThumbsUp(content);
////                    break;
////
////                case MqttConstant.CLEAR_APP_DATA:
////                    clearAppData();
////                    break;
////                case MqttConstant.FOREGROUND_VOICE:
////                    foregroundVoice();
////                    break;
////                default:
////                    break;
//            }
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//        }
//
//
    }
//    /**
//     * 同步手机通讯录
//     */
//    private void syncPhoneAddress(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AddOrUpdatePhoneContacts addOrUpdatePhoneContacts=AddOrUpdatePhoneContacts.GetAddOrUpdatePhoneContact(mContext);
//                final String uploadFriendJson = OtherMsgDispose.gson.toJson(addOrUpdatePhoneContacts);
//                Log.e("yuandan","联系人响应Json "+uploadFriendJson);
//                OkhttpUtil.okHttpPostJson(UrlUtil.AddOrUpdatePhoneContacts, uploadFriendJson, new CallBackUtil.CallBackString() {
//                    @Override
//                    public void onFailure(Call call, Exception e) {
//                        e.printStackTrace();
//                    }
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("xposed",""+response);
//                    }
//                });
//
//
//            }
//        }).start();
//    }
//
//    /**
//     * 解除绑定设备
//     */
//    public void unbindDevice(){
////        MyHelper.deleteFile("Token"); //以后要加上，随便把所有的Token改正（静态常量只初始化一次）
//        SharedPreferencesUtils.setParam(mContext,"passwrod","");
//        EventBus.getDefault().post(new MessageEvent(riskClientCallBack.MQTT_UNBIND_DEVICE,""));
//    }
//
//    /**
//     * 修改设置
//     */
//    public void changeSetting(ServerMessage serverMessage){
//        setSetting(Constant.SystemDesktopFlag, serverMessage.isBackToDesktop());
//        setSetting(Constant.PhoneSettingsFlag, serverMessage.isDisabledPhoneSettings());
//
//        setSetting(Constant.UseCameraFlag, serverMessage.isUseCamera());
//        setSetting(Constant.PhoneCallRecordFlag, serverMessage.isViewPhoneCallLog());
//
////        setSetting(Constant., serverMessage.isCloseNotAllowedApp());
//        setSetting(Constant.WeChatReatFriendRemindFlag, serverMessage.isShowWxRepeatedFriendsNotify());
//
//        setSetting(Constant.DisplayFriendWeChatNumberFlag, serverMessage.isShowWxShowContactWxNo());
//        setSetting(Constant.DisplayFriendWeChatCallPhoneNumberFlag, serverMessage.isShowWxFriendsPhoneNo());
//
//        setSetting(Constant.WeChatSearchFlag, serverMessage.isAllowedUseWxSearch());
//        setSetting(Constant.WeChatSearChTimesUpperLimitFlag, serverMessage.isUploadWxSearchInfo());
//        setSetting(Constant.WeChatUseCameraFlag, serverMessage.isUseCameraInWx());
//        setSetting(Constant.WeChatSettingFlag, serverMessage.isDisabledWxSettings());
//    }
//
//    public void setSetting(String name, boolean isOpen){
//        MyHelper.SetString(name,String.valueOf(isOpen));
//    }
//
//
//    /**
//     * 一键加好友
//     */
//    private void oneKeyAddFriend(String content) {
//
//    }
//
//    /**
//     * 一键发朋友圈
//     */
//    private void oneKeySendCircleOfFriends(String content) {
//
//    }
//
//    /**
//     * 一键点赞
//     */
//    private void oneKeyThumbsUp(String content) {
//
//    }
//
//    /**
//     * 卸载指定包名的应用
//     * @param packageName
//     */
//    public boolean uninstall(String packageName) {
//        boolean b = checkApplication(packageName);
//        if (b) {
//            MyHelper.SetString(HookUtils.UNINSTALL_WCHAT, "true");
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断该包名的应用是否安装
//     *
//     * @param packageName
//     * @return
//     */
//    private boolean checkApplication(String packageName) {
//        if (packageName == null || "".equals(packageName)) {
//            return false;
//        }
//        try {
//            mContext.getPackageManager().getApplicationInfo(packageName,
//                    PackageManager.MATCH_UNINSTALLED_PACKAGES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 清除app数据
//     * 清除 data/data/PackageName 下的所有文件
//     */
//    private void clearAppData() {
//        String packageName = "com.xiake.authoritymanagement";
//        File file = new File("data/data" + packageName);
//        deleteFile(file);
//
//    }
//
//    private void deleteFile(File file) {
//        if (!file.exists()) {
//            return;
//        } else {
//            if (file.isFile()) {
//                file.delete();
//                return;
//            }
//            if (file.isDirectory()) {
//                File[] childFile = file.listFiles();
//                if (childFile == null || childFile.length == 0) {
//                    file.delete();
//                    return;
//                }
//                for (File f : childFile) {
//                    deleteFile(f);
//                }
//                file.delete();
//            }
//        }
//    }
//
//    /**
//     * 请求敏感词  1,不能发送  10.可以发送，但是会有提醒
//     */
//    public static void requestSentive(Context context, final int wordType) {
//        GetAllSensitiveWords querySensitiveWords=new GetAllSensitiveWords(context);
//        querySensitiveWords.setWordType(wordType);
//        String  querySensitiveWordsJson=OtherMsgDispose.gson.toJson(querySensitiveWords);
//        OkhttpUtil.okHttpPostJson(UrlUtil.GetAllSensitiveWords, querySensitiveWordsJson, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(String response) {
//                MyHelper.SetString("sesitive_word_"+wordType, response);
//                Log.e("wusong","获取敏感词响应"+response);
//            }
//        });
//
//    }


}
