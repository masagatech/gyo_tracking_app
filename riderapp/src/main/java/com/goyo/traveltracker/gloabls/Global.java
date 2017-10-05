package com.goyo.traveltracker.gloabls;

import android.app.ProgressDialog;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.model.model_appsettings;
import com.goyo.traveltracker.model.model_config;
import com.goyo.traveltracker.model.model_crewdata;
import com.goyo.traveltracker.model.model_loginusr;

import java.io.File;
import java.lang.reflect.Type;

/**
 * Created by mTech on 04-Mar-2017.
 */
public class Global {

    public final static String URL = "http://traveltrack.goyo.in:8080";
    public final static String REST_URL = URL + "/goyoapi";
    public static final String SOCKET_URL = "http://traveltrack.goyo.in:8080/";

//    public final static String REST_URL = "http://192.168.1.101:8092/goyoapi";
//    public static final String SOCKET_URL = "http://192.168.1.101:8091/";

//    public final static String REST_URL = "http://192.168.43.10:8092/goyoapi";
//    public static final String SOCKET_URL = "http://192.168.43.10:8083/";

//    public final static String REST_URL = "http://192.168.43.17:8082/goyoapi";
//    public static final String SOCKET_URL = "http://192.168.43.17:8083/";


    private final static String APIName = "/track";


    public static File ExternalPath = Environment.getExternalStorageDirectory();
    public final static String Image_Path = "/goyo_images";


    public enum urls {
        testurl("testurl", REST_URL),
        uploadimage("", Global.REST_URL + "/uploads"),
        getlogin("getlogin", REST_URL + "/getLogin"),
        getlogout("getlogout", REST_URL + "/getLogout"),
        savedriverinfo("savedriverinfo", REST_URL + "/savedriverinfo"),
        getmytrips("getmytrips", REST_URL + "/tripapi"),
        starttrip("starttrip", REST_URL + "/tripapi/start04"),
        stoptrip("stoptrip", REST_URL + "/tripapi/stop"),
        picdropcrew("picdropcrew", REST_URL + "/tripapi/picdropcrew"),
        storetripdelta("storetripdelta", REST_URL + "/tripapi/storedelta"),
        getmytripscrew("getmytripscrew", REST_URL + "/tripapi/crews"),
        getmykids("getmykids", REST_URL + "/cust/getmykids"),
        getlastknownloc("getlastknownloc", REST_URL + "/tripapi/getdelta"),
        sendreachingalert("sendreachingalert", REST_URL + "/tripapi/sendreachingalert"),
        getOrderDetails("getorderdetails", REST_URL + APIName + "/getOrderDetails"),
        getOrderDash("getorderdash", REST_URL + APIName + "/getOrderDash"),
        saveLiveBeat("saveLiveBeat", REST_URL + APIName + "/saveLiveBeat"),
        getOrders("getOrders", REST_URL + APIName + "/getOrders"),
        getStatus("getStatus", REST_URL + APIName + "/getStatus"),
        setStatus("setStatus", REST_URL + APIName + "/setStatus"),
        setTripAction("setTripAction", REST_URL + APIName + "/setTripAction"),
        getNotify("getNotify", REST_URL + APIName + "/getNotify"),
        getAvailRider("getNotify", REST_URL + APIName + "/getAvailRider"),
        getMOM("getMOM", REST_URL + "/getMOM"),
        getOrdersCount("getOrdersCount", REST_URL + APIName + "/getOrdersCount"),
        getTaskAllocate("getTaskAllocate", REST_URL + "/getTaskAllocate"),
        starttripswitch("starttrip", REST_URL + "/tripapi/start"),
        stoptripswitch("stoptrip", REST_URL + "/tripapi/stop"),
        getEmpStatus("getEmpStatus", REST_URL + "/getEmpStatus"),
        saveTripStops("saveTripStops", REST_URL + "/saveTripStops"),
        getTripStops("getTripStops", REST_URL + "/getTripStops"),
        getMOM2("getMOM", REST_URL + "/getMOM"),
        saveTaskNature("saveTaskNature", REST_URL + "/saveTaskNature"),
        getTripReports("getTripReports", REST_URL + "/getTripReports"),
        getNatureTask("getTaskNature", REST_URL + "/getTaskNature"),
        saveTagInfo("saveTagInfo", REST_URL + "/saveTagInfo"),
        getTagDetails("getTagDetails", REST_URL + "/getTagDetails"),
        getTagEmployeeMap("getTagEmployeeMap", REST_URL + "/getTagEmployeeMap"),
        livebeats("livebeats", REST_URL + "/livebeats"),
        mobileupload("mobileupload", REST_URL + "/mobileupload"),
        getExpenseDetails("getExpenseDetails", REST_URL + "/getExpenseDetails"),
        saveExpenseDetails("saveExpenseDetails", REST_URL + "/saveExpenseDetails"),
        saveEmployeeLeave("saveEmployeeLeave", REST_URL + "/saveEmployeeLeave"),
        getEmployeeLeave("getEmployeeLeave", REST_URL + "/getEmployeeLeave"),
        getHoliday("getHoliday", REST_URL + "/getHoliday"),
        getNotification("getNotification", REST_URL + "/getNotification"),
        gettrackboard("gettrackboard", REST_URL + "/tripapi/gettrackboard"),
        getdelta("getdelta", REST_URL + "/tripapi/getdelta"),
        getVoucherDetails("getVoucherDetails", REST_URL + "/getVoucherDetails"),
        getEmployeeDetails("getEmployeeDetails", REST_URL + "/getEmployeeDetails"),
        saveContactUs("saveContactUs", REST_URL + "/saveContactUs");


        public String key;
        public String value;

        private urls(String toKey, String toValue) {
            key = toKey;
            value = toValue;
        }

    }

    public final static String start = "1";
    public final static String done = "2";
    public final static String pause = "pause";
    public final static String cancel = "3";
    public final static String pending = "0";
    public static Integer RedAlert = 10;
    public final static String pickedupdrop = "1";
    public final static String absent = "2";

    public static Boolean isOnline = false;
    public static model_crewdata crewDetails;

    public static model_loginusr loginusr;
    private static model_config config;

    public static model_appsettings appsettings;
    public static ProgressDialog prgdialog;

    public static void showProgress(ProgressDialog prd) {
        prd.setCancelable(false);
        if (!prd.isShowing()) prd.show();
    }

    public static void loadConfig(String config) {

        Gson gson = new Gson();
        Type listType = new TypeToken<model_config>() {
        }.getType();
        Global.config = (model_config) gson.fromJson(config, listType);
//        Global.RedAlert =  Global.config.notifymin;
    }

    public static void hideProgress(ProgressDialog prd) {
        prd.dismiss();
    }
}
