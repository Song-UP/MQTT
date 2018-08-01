package com.example.songup;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.TELEPHONY_SERVICE;

public class MQTTPhoneInfo {
    static Context mContext;
    private static MQTTPhoneInfo instance;

    public static MQTTPhoneInfo getInstance(Context context) {
        if (instance == null) {
            instance = new MQTTPhoneInfo(context);
        }
        return instance;
    }

    public MQTTPhoneInfo(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 获取版本code
     *
     * @return
     */
    public int getVerCode() {
        int verCode = -1;
        try {
            verCode = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVerName() {
        String verName = "";
        try {
            verName = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;
    }

    /**
     * 获取手机是否root信息    * @return
     */
    public String isRoot() {
        String bool = "Root:false";
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = "Root:false";
            } else {
                bool = "Root:true";
            }
        } catch (Exception e) {
        }
        return bool;
    }

    /**
     * 获取IMEI号，，手机型号
     */
    public String getIMEI() {
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();
        return imei;
    }

    /**
     * 获取IESI号
     *
     * @return
     */
    private String getIESI() {
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String imsi = mTm.getSubscriberId();
        return imsi;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public String getTYPE() {
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String mtype = android.os.Build.MODEL;
        return mtype;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public String getTYB() {
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String mtyb = android.os.Build.BRAND;
        return mtyb;
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    public String getPhoneNumber() {
        TelephonyManager mTm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String phoneNumber = mTm.getLine1Number();
        return phoneNumber;
    }

    /**
     * 手机CPU信息
     */
    public String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""}; //1-cpu型号 //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {

        }
        return "CPU型号:" + cpuInfo[0] + "\nCPU频率：" + cpuInfo[1];
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(200);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            // 打印Log
            if (serviceList.get(i).service.getShortClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 插入手机号和联系人
     */
    public boolean saveContact(String phone, String name) {

        try {
            ContentValues values = new ContentValues();
            ContentResolver cr = mContext.getContentResolver();
            // 下面的操作会根据RawContacts表中已有的rawContactId使用情况自动生成新联系人的rawContactId
            Uri rawContactUri = cr.insert(
                    ContactsContract.RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);

            // 向data表插入姓名数据
            if (!"".equals(name)) {
                values.clear();
                values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name+"_C");
                cr.insert(ContactsContract.Data.CONTENT_URI,
                        values);
            }
            // 向data表插入电话数据
            if (!"".equals(phone)) {
                values.clear();
                values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                cr.insert(ContactsContract.Data.CONTENT_URI, values);
            }
        } catch (Exception e) {
            return false;
        }
        return true;



    }
}
