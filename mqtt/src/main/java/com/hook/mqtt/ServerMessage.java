package com.hook.mqtt;

import java.util.ArrayList;

/**
 * ---Created by zsl on 2018/7/5 0005.---
 */
public class ServerMessage {
	// TODO: 2018/7/5 0005  服务端传过来的消息对象（待定）
	//消息类型
	private int Type;

	private boolean IsBackToDesktop;
	private boolean IsDisabledPhoneSettings;
	private boolean IsUseCamera;
	private boolean IsViewPhoneCallLog;
	private boolean IsCloseNotAllowedApp;
	private boolean IsShowWxRepeatedFriendsNotify;
	private boolean IsShowWxShowContactWxNo;
	private boolean IsShowWxFriendsPhoneNo;
	private boolean IsAllowedUseWxSearch;
	private boolean IsUploadWxSearchInfo;
	private boolean IsUseCameraInWx;
	private boolean IsDisabledWxSettings;

	//发送朋友圈
	private int SendFriendsCircleId;
	private int FriendsCircleType;  //发送内容类型 1.文本， 2图片文本， 3 视频文件
	private String FriendsCircleContent; //文本内容
	private int FriendsCircleVisible;
	private ArrayList<String> FriendsCircleFileIds;
	private String WxLabel;  //1.全部 10 私密 20 指定标签 30 屏蔽标签

//	/AuxiliaryMarketing/SetOnekeyPointPraiseStatus
//	{
//		Token: "",
//		OnekeyWxPointPraiseId: 0,
//		Status: 10, //10 发送成功, 20 发送失败
//	}


	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public boolean isBackToDesktop() {
		return IsBackToDesktop;
	}

	public void setBackToDesktop(boolean backToDesktop) {
		IsBackToDesktop = backToDesktop;
	}

	public boolean isDisabledPhoneSettings() {
		return IsDisabledPhoneSettings;
	}

	public void setDisabledPhoneSettings(boolean disabledPhoneSettings) {
		IsDisabledPhoneSettings = disabledPhoneSettings;
	}

	public boolean isUseCamera() {
		return IsUseCamera;
	}

	public void setUseCamera(boolean useCamera) {
		IsUseCamera = useCamera;
	}

	public boolean isViewPhoneCallLog() {
		return IsViewPhoneCallLog;
	}

	public void setViewPhoneCallLog(boolean viewPhoneCallLog) {
		IsViewPhoneCallLog = viewPhoneCallLog;
	}

	public boolean isCloseNotAllowedApp() {
		return IsCloseNotAllowedApp;
	}

	public void setCloseNotAllowedApp(boolean closeNotAllowedApp) {
		IsCloseNotAllowedApp = closeNotAllowedApp;
	}

	public boolean isShowWxRepeatedFriendsNotify() {
		return IsShowWxRepeatedFriendsNotify;
	}

	public void setShowWxRepeatedFriendsNotify(boolean showWxRepeatedFriendsNotify) {
		IsShowWxRepeatedFriendsNotify = showWxRepeatedFriendsNotify;
	}

	public boolean isShowWxShowContactWxNo() {
		return IsShowWxShowContactWxNo;
	}

	public void setShowWxShowContactWxNo(boolean showWxShowContactWxNo) {
		IsShowWxShowContactWxNo = showWxShowContactWxNo;
	}

	public boolean isShowWxFriendsPhoneNo() {
		return IsShowWxFriendsPhoneNo;
	}

	public void setShowWxFriendsPhoneNo(boolean showWxFriendsPhoneNo) {
		IsShowWxFriendsPhoneNo = showWxFriendsPhoneNo;
	}

	public boolean isAllowedUseWxSearch() {
		return IsAllowedUseWxSearch;
	}

	public void setAllowedUseWxSearch(boolean allowedUseWxSearch) {
		IsAllowedUseWxSearch = allowedUseWxSearch;
	}

	public boolean isUploadWxSearchInfo() {
		return IsUploadWxSearchInfo;
	}

	public void setUploadWxSearchInfo(boolean uploadWxSearchInfo) {
		IsUploadWxSearchInfo = uploadWxSearchInfo;
	}

	public boolean isUseCameraInWx() {
		return IsUseCameraInWx;
	}

	public void setUseCameraInWx(boolean useCameraInWx) {
		IsUseCameraInWx = useCameraInWx;
	}

	public boolean isDisabledWxSettings() {
		return IsDisabledWxSettings;
	}

	public void setDisabledWxSettings(boolean disabledWxSettings) {
		IsDisabledWxSettings = disabledWxSettings;
	}

	public ArrayList<String> getFriendsCircleFileIds() {
		return FriendsCircleFileIds;
	}

	public void setFriendsCircleFileIds(ArrayList<String> friendsCircleFileIds) {
		FriendsCircleFileIds = friendsCircleFileIds;
	}

	public int getSendFriendsCircleId() {
		return SendFriendsCircleId;
	}

	public void setSendFriendsCircleId(int sendFriendsCircleId) {
		SendFriendsCircleId = sendFriendsCircleId;
	}

	public int getFriendsCircleType() {
		return FriendsCircleType;
	}

	public void setFriendsCircleType(int friendsCircleType) {
		FriendsCircleType = friendsCircleType;
	}

	public String getFriendsCircleContent() {
		return FriendsCircleContent;
	}

	public void setFriendsCircleContent(String friendsCircleContent) {
		FriendsCircleContent = friendsCircleContent;
	}

	public int getFriendsCircleVisible() {
		return FriendsCircleVisible;
	}

	public void setFriendsCircleVisible(int friendsCircleVisible) {
		FriendsCircleVisible = friendsCircleVisible;
	}



	public String getWxLabel() {
		return WxLabel;
	}

	public void setWxLabel(String wxLabel) {
		WxLabel = wxLabel;
	}


//	public static ServerMessage GetCirleBeen(String json){
//		ServerMessage serverMessage = new ServerMessage();
//		if (json == null)
//			return serverMessage;
//		try {
//			JSONObject jsonObject = new JSONObject(json);
//			serverMessage.setSendFriendsCircleId(jsonObject.getInt("SendFriendsCircleId"));
//			serverMessage.setFriendsCircleType(jsonObject.getInt("FriendsCircleType"));
//			serverMessage.setFriendsCircleContent(jsonObject.getString("FriendsCircleContent"));
//			JSONArray jsonArray = jsonObject.getJSONArray("FriendsCircleFileIds");
//			ArrayList<String> list = new ArrayList<>();
//			for (int i = 0; jsonArray!= null && i>jsonArray.length(); i++){
//				 list.add(jsonArray.getString(0));
//			}
//			serverMessage.setFriendsCircleFileIds(list);
//			serverMessage.setWxLabel(jsonObject.getString("WxLabel"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return serverMessage;
//
//
//	}

}
