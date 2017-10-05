package com.goyo.traveltracker.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.goyo.traveltracker.gloabls.Global;

import org.apache.http.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by mTech on 04-Mar-2017.
 */
public class common {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public boolean getConnectivityStatus(Context context) {
        int conn = common.getConnectivityStatusInt(context);
        boolean status = false;
        if (conn == common.TYPE_WIFI) {
            status = true;
        } else if (conn == common.TYPE_MOBILE) {
            status = true;
        } else if (conn == common.TYPE_NOT_CONNECTED) {
            status = false;
        }
        return status;
    }

    private static int getConnectivityStatusInt(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getDateFormat(String presentfromat,String date,String reqFormat) {
        String strNewDate="";
        try {
            DateFormat df=new SimpleDateFormat(presentfromat, Locale.ENGLISH);
            Date d=df.parse(date);
            SimpleDateFormat sdf =new SimpleDateFormat(reqFormat,Locale.ENGLISH);
            strNewDate = sdf.format(d);
//			Log.e("New Date", "Date==  "+strNewDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return strNewDate;
    }

    public static String deviceId(Context ctx){

        String android_id = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;

    }

    private boolean isDeviceSupportCamera(Context c) {
        if (c.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    public static String dateandtime(Context ctx){

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        String  currenttime = sdf.format(new Date());
        return currenttime;

    }

    public static String calenderdateandtime(Context ctx,  long dt){

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        String  currenttime = sdf.format(dt);
        return currenttime;

    }

    public static String GetUniqueID(String name){

        String id = name+String.valueOf(System.currentTimeMillis())+Global.loginusr.getDriverid()+ Global.loginusr.getEnttid();
        return id;

    }

    public final static String Language(String key){
        HashMap<String, String> strLang = new HashMap<>();
        strLang.put("en","English");
        strLang.put("gu","ગુજરાતી");
        strLang.put("hi","हिंदी");
        strLang.put("mi","मराठी");
        return  strLang.get(key);
    }

    public static boolean setLanguage(Context appContext, Context baseContext){
        boolean isLanguageSet = true;
        String languageToLoad  = SHP.get(appContext, SHP.ids.sett_lang, "false").toString(); // your language
        if (languageToLoad.equals("false")) {
            languageToLoad = "en";
            isLanguageSet = false;
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        baseContext.getResources().updateConfiguration(config,
                baseContext.getResources().getDisplayMetrics());
        return isLanguageSet;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }



}
