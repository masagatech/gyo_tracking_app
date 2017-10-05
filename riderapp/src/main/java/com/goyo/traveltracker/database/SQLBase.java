package com.goyo.traveltracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.goyo.traveltracker.model.modal_leave;
import com.goyo.traveltracker.model.model_expense;
import com.goyo.traveltracker.model.model_expense_all;
import com.goyo.traveltracker.model.model_tag;
import com.goyo.traveltracker.model.model_task;
import com.goyo.traveltracker.model.model_tasks_pending;
import com.goyo.traveltracker.utils.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mTech on 03-Mar-2017.
 */
public class SQLBase  {
    private static final String DATABASE_NAME = "goyoriderv1.db";
    private static final int DATABASE_VERSION = 1;
    private static Context context;
    public static SQLiteDatabase sqLiteDB;
    private SQLiteStatement insertStmt;
    DataBaseHelper openHelper;

    public SQLBase(Context context) {
        SQLBase.context = context;
        try{
            this.openHelper = new DataBaseHelper(SQLBase.context);
            SQLBase.sqLiteDB = openHelper.getWritableDatabase();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*Diver Info*/

    public void DRIVER_INFO_INSERT(String arthinm,String mob1,String mob2,String adharno , String ownrship , String vehno
            , String yrsold , String btchno , String howmny , String driving , String alruseing, String goyointr ,
                                   String doyohv   , String prefloc  , String createon , String createdby ,
                                   String sendToServer , String vehtype , String remarks , String lat , String lon,
                                   String profpic ,String profpic_upload ,
                                   String doc1 , String doc1_upload , String doc2 , String doc2_upload , String doc3 , String doc3_upload
            ,String doc4,String doc4_upload
    ){
        //insertStmt = sqLiteDB.compileStatement(Procedures.tbl_driver_info.INSERT);
        ContentValues values = new ContentValues();
        values.put(Tables.tbl_driver_info.mibuid , "" + System.currentTimeMillis());
        values.put(Tables.tbl_driver_info.sarthinm, arthinm);
        values.put(Tables.tbl_driver_info.mob1, mob1);
        values.put(Tables.tbl_driver_info.mob2 , mob2);
        values.put(Tables.tbl_driver_info.adharno, adharno);
        values.put(Tables.tbl_driver_info.ownrship , ownrship);
        values.put(Tables.tbl_driver_info.vehno , vehno);
        values.put(Tables.tbl_driver_info.yrsold , yrsold);
        values.put(Tables.tbl_driver_info.btchno , btchno);
        values.put(Tables.tbl_driver_info.howmny , howmny);
        values.put(Tables.tbl_driver_info.driving , driving);
        values.put(Tables.tbl_driver_info.alruseing, alruseing);
        values.put(Tables.tbl_driver_info.goyointr, goyointr);
        values.put(Tables.tbl_driver_info.doyohv , doyohv);
        values.put(Tables.tbl_driver_info.prefloc , prefloc);
        values.put(Tables.tbl_driver_info.createon , createon);
        values.put(Tables.tbl_driver_info.createdby , createdby);
        values.put(Tables.tbl_driver_info.sentToserver, sendToServer);
        values.put(Tables.tbl_driver_info.vehtype , vehtype);
        values.put(Tables.tbl_driver_info.remarks , remarks);
        values.put(Tables.tbl_driver_info.lat , lat);
        values.put(Tables.tbl_driver_info.lon , lon);
        values.put(Tables.tbl_driver_info.profpic , profpic);
        values.put(Tables.tbl_driver_info.profpic_upload , profpic_upload);
        values.put(Tables.tbl_driver_info.doc1 , doc1);
        values.put(Tables.tbl_driver_info.doc1_upload , doc1_upload);
        values.put(Tables.tbl_driver_info.doc2 , doc2);
        values.put(Tables.tbl_driver_info.doc1_upload , doc2_upload);
        values.put(Tables.tbl_driver_info.doc3 , doc3);
        values.put(Tables.tbl_driver_info.doc3_upload , doc3_upload);
        values.put(Tables.tbl_driver_info.doc4, doc4);
        values.put(Tables.tbl_driver_info.doc4_upload, doc4_upload);
        sqLiteDB.insert(Tables.tbl_driver_info.name,null,values);
        sqLiteDB.close();
    }

    //update
    public void DRIVER_INFO_UPDATE(String autoid,String arthinm,String mob1,String mob2,String adharno  , String ownrship , String vehno
            , String yrsold   , String btchno   , String howmny   , String driving  , String alruseing, String goyointr ,
                                   String doyohv   , String prefloc  , String createon , String createdby,
                                   String sendToServer , String vehtype, String remarks, String lat, String lon,
                                   String profpic,String profpic_upload,
                                   String doc1,String doc1_upload,String doc2,String doc2_upload,String doc3,String doc3_upload
            ,String doc4,String doc4_upload){

        ContentValues values = new ContentValues();
        values.put(Tables.tbl_driver_info.sarthinm, arthinm);
        values.put(Tables.tbl_driver_info.mob1, mob1);
        values.put(Tables.tbl_driver_info.mob2 , mob2);
        values.put(Tables.tbl_driver_info.adharno, adharno);
        values.put(Tables.tbl_driver_info.ownrship , ownrship);
        values.put(Tables.tbl_driver_info.vehno , vehno);
        values.put(Tables.tbl_driver_info.yrsold , yrsold);
        values.put(Tables.tbl_driver_info.btchno , btchno);
        values.put(Tables.tbl_driver_info.howmny , howmny);
        values.put(Tables.tbl_driver_info.driving , driving);
        values.put(Tables.tbl_driver_info.alruseing, alruseing);
        values.put(Tables.tbl_driver_info.goyointr, goyointr);
        values.put(Tables.tbl_driver_info.doyohv , doyohv);
        values.put(Tables.tbl_driver_info.prefloc , prefloc);
        values.put(Tables.tbl_driver_info.sentToserver, sendToServer);
        values.put(Tables.tbl_driver_info.vehtype , vehtype);
        values.put(Tables.tbl_driver_info.remarks , remarks);
        values.put(Tables.tbl_driver_info.profpic , profpic);
        values.put(Tables.tbl_driver_info.profpic_upload , profpic_upload);
        values.put(Tables.tbl_driver_info.doc1 , doc1);
        values.put(Tables.tbl_driver_info.doc1_upload , doc1_upload);
        values.put(Tables.tbl_driver_info.doc2 , doc2);
        values.put(Tables.tbl_driver_info.doc1_upload , doc2_upload);
        values.put(Tables.tbl_driver_info.doc3 , doc3);
        values.put(Tables.tbl_driver_info.doc3_upload , doc3_upload);
        values.put(Tables.tbl_driver_info.doc4, doc4);
        values.put(Tables.tbl_driver_info.doc4_upload, doc4_upload);

        sqLiteDB.update(Tables.tbl_driver_info.name, values, Tables.tbl_driver_info.autoid + " = ?",
                new String[] { String.valueOf(autoid) });
    }

    public List<HashMap<String, String>> DRIVER_INFO_GET(String Query){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbl_driver_info.autoid
                +"," + Tables.tbl_driver_info.mibuid +","
                + Tables.tbl_driver_info.sarthinm +","
                + Tables.tbl_driver_info.mob1 +","
                + Tables.tbl_driver_info.mob2
                +","+ Tables.tbl_driver_info.sentToserver
                +","+  Tables.tbl_driver_info.ownrship
                +","+ Tables.tbl_driver_info.vehno
                +","+ Tables.tbl_driver_info.yrsold +","
                + Tables.tbl_driver_info.btchno
                +","+ Tables.tbl_driver_info.howmny
                +","+ Tables.tbl_driver_info.driving +","
                + Tables.tbl_driver_info.alruseing
                +","+ Tables.tbl_driver_info.goyointr
                +","+ Tables.tbl_driver_info.doyohv +","
                + Tables.tbl_driver_info.prefloc
                +","+ Tables.tbl_driver_info.createon
                +","+ Tables.tbl_driver_info.createdby
                +","+ Tables.tbl_driver_info.vehtype+","
                + Tables.tbl_driver_info.remarks+","
                + Tables.tbl_driver_info.lat+","
                + Tables.tbl_driver_info.lon+","
                + Tables.tbl_driver_info.profpic+","
                + Tables.tbl_driver_info.profpic_upload
                +"," + Tables.tbl_driver_info.doc1
                +"," + Tables.tbl_driver_info.doc1_upload
                +"," + Tables.tbl_driver_info.doc2
                +"," + Tables.tbl_driver_info.doc2_upload
                +"," + Tables.tbl_driver_info.doc3
                +"," + Tables.tbl_driver_info.doc3_upload
                +"," + Tables.tbl_driver_info.doc4
                +"," + Tables.tbl_driver_info.doc4_upload
                +","+ Tables.tbl_driver_info.adharno

                + " FROM " + Tables.tbl_driver_info.name + " " + Query;
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbl_driver_info.autoid, cursor.getString(0));
                map.put(Tables.tbl_driver_info.mibuid, cursor.getString(1));
                map.put(Tables.tbl_driver_info.sarthinm, cursor.getString(2));
                map.put(Tables.tbl_driver_info.mob1, cursor.getString(3));
                map.put(Tables.tbl_driver_info.mob2, cursor.getString(4));
                map.put(Tables.tbl_driver_info.sentToserver, cursor.getString(5));
                map.put(Tables.tbl_driver_info.ownrship, cursor.getString(6));
                map.put(Tables.tbl_driver_info.vehno, cursor.getString(7));
                map.put(Tables.tbl_driver_info.yrsold, cursor.getString(8));
                map.put(Tables.tbl_driver_info.btchno, cursor.getString(9));
                map.put(Tables.tbl_driver_info.howmny, cursor.getString(10));
                map.put(Tables.tbl_driver_info.driving, cursor.getString(11));
                map.put(Tables.tbl_driver_info.alruseing, cursor.getString(12));
                map.put(Tables.tbl_driver_info.goyointr, cursor.getString(13));
                map.put(Tables.tbl_driver_info.doyohv, cursor.getString(14));
                map.put(Tables.tbl_driver_info.prefloc, cursor.getString(15));
                map.put(Tables.tbl_driver_info.createon, cursor.getString(16));
                map.put(Tables.tbl_driver_info.createdby, cursor.getString(17));
                map.put(Tables.tbl_driver_info.vehtype, cursor.getString(18));
                map.put(Tables.tbl_driver_info.remarks, cursor.getString(19));
                map.put(Tables.tbl_driver_info.lat, cursor.getString(20));
                map.put(Tables.tbl_driver_info.lon, cursor.getString(21));
                map.put(Tables.tbl_driver_info.profpic, cursor.getString(22));
                map.put(Tables.tbl_driver_info.profpic_upload, cursor.getString(23));
                map.put(Tables.tbl_driver_info.doc1, cursor.getString(24));
                map.put(Tables.tbl_driver_info.doc1_upload, cursor.getString(25));
                map.put(Tables.tbl_driver_info.doc2, cursor.getString(26));
                map.put(Tables.tbl_driver_info.doc2_upload, cursor.getString(27));
                map.put(Tables.tbl_driver_info.doc3, cursor.getString(28));
                map.put(Tables.tbl_driver_info.doc3_upload, cursor.getString(29));
                map.put(Tables.tbl_driver_info.doc4, cursor.getString(30));
                map.put(Tables.tbl_driver_info.doc4_upload, cursor.getString(31));
                map.put(Tables.tbl_driver_info.adharno, cursor.getString(32));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }


    public List<HashMap<String, String>> DRIVER_INFO_GET_GRID(String Query){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbl_driver_info.autoid
                + ","  + Tables.tbl_driver_info.mibuid
                + ","  + Tables.tbl_driver_info.sarthinm
                + ","  + Tables.tbl_driver_info.mob1
                + "," + Tables.tbl_driver_info.createon
                + ","  + Tables.tbl_driver_info.sentToserver
                + ","  +  Tables.tbl_driver_info.profpic
                + " FROM " + Tables.tbl_driver_info.name + " " + Query;
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbl_driver_info.autoid, cursor.getString(0));
                map.put(Tables.tbl_driver_info.mibuid, cursor.getString(1));
                map.put(Tables.tbl_driver_info.sarthinm, cursor.getString(2));
                map.put(Tables.tbl_driver_info.mob1, cursor.getString(3));
                map.put(Tables.tbl_driver_info.createon, cursor.getString(4));
                map.put(Tables.tbl_driver_info.sentToserver, cursor.getString(5));
                map.put(Tables.tbl_driver_info.profpic, cursor.getString(6));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }


    //update offline
    public void DRIVER_INFO_UPDATE_OFFLINE(String _uniqueid, String status, String servid){
        sqLiteDB.execSQL("UPDATE " + Tables.tbl_driver_info.name + " SET "
                +  Tables.tbl_driver_info.sentToserver + "=" + "'true'"+ ","
                +  Tables.tbl_driver_info.servrid + "=" + "'" + servid  + "'"

                + " WHERE " + Tables.tbl_driver_info.autoid + "=" + _uniqueid);
    }

    //update offline
    public void DRIVER_INFO_UPDATE_File_OFFLINE(String _uniqueid){
        sqLiteDB.execSQL("UPDATE " + Tables.tbl_driver_info.name + " SET "
                +  Tables.tbl_driver_info.profpic_upload + "=" + "'true'"
                + " WHERE " + Tables.tbl_driver_info.autoid + "=" + _uniqueid);
    }


    //############################################################################################################
    // notifications

    public void NOTIFICATION_INSERT(String data, Integer exptm
    ){
        //insertStmt = sqLiteDB.compileStatement(Procedures.tbl_driver_info.INSERT);
        ContentValues values = new ContentValues();


        values.put(Tables.tblnotification.createon , "" + common.dateandtime(context));
        values.put(Tables.tblnotification.data, data);
        values.put(Tables.tblnotification.exp, exptm + "");
        sqLiteDB.insert(Tables.tblnotification.name,null,values);
        //sqLiteDB.close();
    }

    public void NOTIFICATION_DELETE(String autoid
    ){
        //insertStmt = sqLiteDB.compileStatement(Procedures.tbl_driver_info.INSERT);
        sqLiteDB.execSQL("DELETE FROM " + Tables.tblnotification.name
                + " WHERE " + Tables.tbl_driver_info.autoid + "=" + autoid);
        //sqLiteDB.close();
    }




    public List<HashMap<String, String>> NOTIFICATION_GET(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblnotification.autoid
                +"," + Tables.tblnotification.createon +","
                + Tables.tblnotification.data+","
                + Tables.tblnotification.exp

                + " FROM " + Tables.tblnotification.name ;
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblnotification.autoid, cursor.getString(0));
                map.put(Tables.tblnotification.createon, cursor.getString(1));
                map.put(Tables.tblnotification.data, cursor.getString(2));
                map.put(Tables.tblnotification.exp, cursor.getString(3));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }

    //############################################################################################################################
    //######################################Offline Tasks#########################################################################
    //############################################################################################################################
        // Adding stops
        public void OFFLINE_TASK_ADDTASK(model_task Task) {

            ContentValues values = new ContentValues();
            values.put(Tables.tblofflinetask.Task_Title, Task.get_title());
            values.put(Tables.tblofflinetask.Task_Body, Task.get_body());
            values.put(Tables.tblofflinetask.Task_Lat, Task.get_lat());
            values.put(Tables.tblofflinetask.Task_Lon, Task.get_lon());
            values.put(Tables.tblofflinetask.Task_Tags, Task.get_tags());
            values.put(Tables.tblofflinetask.Task_Creat_On, Task.get_creat_on());
            values.put(Tables.tblofflinetask.Is_Server_Send, Task.get_is_server_send());
            values.put(Tables.tblofflinetask.Task_Time, Task.get_time());
            values.put(Tables.tblofflinetask.Task_Images_Paths, Task.get_image_paths());
            values.put(Tables.tblofflinetask.EXP_ID, Task.get_exp_id());
            values.put(Tables.tblofflinetask.EXP_Type, Task.get_exp_type());
            values.put(Tables.tblofflinetask.EXP_Value, Task.get_exp_value());
            values.put(Tables.tblofflinetask.EXP_Disc, Task.get_exp_disc());
            values.put(Tables.tblofflinetask.IN_TIME, Task.get_in_time());
            values.put(Tables.tblofflinetask.OUT_TIME, Task.get_out_time());
            values.put(Tables.tblofflinetask.STOP_ID, Task.get_stop_id());

            // Inserting Row
            sqLiteDB.insert(Tables.tblofflinetask.name, null, values);
//            sqLiteDB.close(); // Closing database connection
        }



        public List<HashMap<String, String>> Get_TASK(){
            List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//            String selectEvents = "SELECT "
//                    + Tables.tblofflinetask.Task_Id
//                    + Tables.tblofflinetask.Task_Title +","
//                    + Tables.tblofflinetask.Task_Body +","
//                    + Tables.tblofflinetask.Task_Lat +","
//                    + Tables.tblofflinetask.Task_Lon +","
//                    + Tables.tblofflinetask.Task_Tags +","
//                    + Tables.tblofflinetask.Task_Creat_On+","
//                    + Tables.tblofflinetask.Is_Server_Send+","
//                    + Tables.tblofflinetask.Task_Time
//                    + " FROM " + Tables.tblnotification.name +" WHERE "
//                    + Tables.tblofflinetask.Is_Server_Send + " = '0'";
            Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tblofflinetask.name, null);
//            Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(Tables.tblofflinetask.Task_Id, cursor.getString(0));
                    map.put(Tables.tblofflinetask.Task_Title, cursor.getString(1));
                    map.put(Tables.tblofflinetask.Task_Body, cursor.getString(2));
                    map.put(Tables.tblofflinetask.Task_Lat, cursor.getString(3));
                    map.put(Tables.tblofflinetask.Task_Lon, cursor.getString(4));
                    map.put(Tables.tblofflinetask.Task_Tags, cursor.getString(5));
                    map.put(Tables.tblofflinetask.Task_Creat_On, cursor.getString(6));
                    map.put(Tables.tblofflinetask.Is_Server_Send, cursor.getString(7));
                    map.put(Tables.tblofflinetask.Task_Time, cursor.getString(8));
                    map.put(Tables.tblofflinetask.Task_Images_Paths, cursor.getString(9));
                    map.put(Tables.tblofflinetask.EXP_ID, cursor.getString(10));
                    map.put(Tables.tblofflinetask.EXP_Type, cursor.getString(11));
                    map.put(Tables.tblofflinetask.EXP_Value, cursor.getString(12));
                    map.put(Tables.tblofflinetask.EXP_Disc, cursor.getString(13));
                    data.add(map);
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
            return data;
        }

    public Boolean ISTASK_ALREDY_EXIST(String Tag_Name){

        String Query = "SELECT "
                + Tables.tblofflinetask.Task_Id+","
                +Tables.tblofflinetask.Task_Title+","
                + Tables.tblofflinetask.Task_Body +","
                + Tables.tblofflinetask.Task_Lat +","
                + Tables.tblofflinetask.Task_Lon +","
                + Tables.tblofflinetask.Task_Tags +","
                + Tables.tblofflinetask.Task_Creat_On +","
                + Tables.tblofflinetask.Is_Server_Send+","
                + Tables.tblofflinetask.Task_Time+","
                + Tables.tblofflinetask.Task_Images_Paths+","
                + Tables.tblofflinetask.EXP_ID+","
                + Tables.tblofflinetask.EXP_Type+","
                + Tables.tblofflinetask.EXP_Value+","
                + Tables.tblofflinetask.EXP_Disc+","
                + Tables.tblofflinetask.IN_TIME+","
                + Tables.tblofflinetask.OUT_TIME+","
                + Tables.tblofflinetask.STOP_ID
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Task_Title + " = '" + Tag_Name+ "'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public List<HashMap<String, String>> Get_Tasks_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblofflinetask.Task_Id+","
                +Tables.tblofflinetask.Task_Title+","
                + Tables.tblofflinetask.Task_Body +","
                + Tables.tblofflinetask.Task_Lat +","
                + Tables.tblofflinetask.Task_Lon +","
                + Tables.tblofflinetask.Task_Tags +","
                + Tables.tblofflinetask.Task_Creat_On +","
                + Tables.tblofflinetask.Is_Server_Send+","
                + Tables.tblofflinetask.Task_Time+","
                + Tables.tblofflinetask.Task_Images_Paths+","
                + Tables.tblofflinetask.EXP_ID+","
                + Tables.tblofflinetask.EXP_Type+","
                + Tables.tblofflinetask.EXP_Value+","
                + Tables.tblofflinetask.EXP_Disc+","
                + Tables.tblofflinetask.IN_TIME+","
                + Tables.tblofflinetask.OUT_TIME+","
                + Tables.tblofflinetask.STOP_ID
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Is_Server_Send+ " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblofflinetask.Task_Id, cursor.getString(0));
                map.put(Tables.tblofflinetask.Task_Title, cursor.getString(1));
                map.put(Tables.tblofflinetask.Task_Body, cursor.getString(2));
                map.put(Tables.tblofflinetask.Task_Lat, cursor.getString(3));
                map.put(Tables.tblofflinetask.Task_Lon, cursor.getString(4));
                map.put(Tables.tblofflinetask.Task_Tags, cursor.getString(5));
                map.put(Tables.tblofflinetask.Task_Creat_On, cursor.getString(6));
                map.put(Tables.tblofflinetask.Is_Server_Send, cursor.getString(7));
                map.put(Tables.tblofflinetask.Task_Time, cursor.getString(8));
                map.put(Tables.tblofflinetask.Task_Images_Paths, cursor.getString(9));
                map.put(Tables.tblofflinetask.EXP_ID, cursor.getString(10));
                map.put(Tables.tblofflinetask.EXP_Type, cursor.getString(11));
                map.put(Tables.tblofflinetask.EXP_Value, cursor.getString(12));
                map.put(Tables.tblofflinetask.EXP_Disc, cursor.getString(13));
                map.put(Tables.tblofflinetask.IN_TIME, cursor.getString(14));
                map.put(Tables.tblofflinetask.OUT_TIME, cursor.getString(15));
                map.put(Tables.tblofflinetask.STOP_ID, cursor.getString(16));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_Today_Stops(String date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblofflinetask.Task_Id+","
                +Tables.tblofflinetask.Task_Title+","
                + Tables.tblofflinetask.Task_Body +","
                + Tables.tblofflinetask.Task_Lat +","
                + Tables.tblofflinetask.Task_Lon +","
                + Tables.tblofflinetask.Task_Tags +","
                + Tables.tblofflinetask.Task_Creat_On +","
                + Tables.tblofflinetask.Is_Server_Send+","
                + Tables.tblofflinetask.Task_Time+","
                + Tables.tblofflinetask.Task_Images_Paths+","
                + Tables.tblofflinetask.EXP_ID+","
                + Tables.tblofflinetask.EXP_Type+","
                + Tables.tblofflinetask.EXP_Value+","
                + Tables.tblofflinetask.EXP_Disc+","
                + Tables.tblofflinetask.IN_TIME+","
                + Tables.tblofflinetask.OUT_TIME+","
                + Tables.tblofflinetask.STOP_ID
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Task_Creat_On+ " = '"+date+"'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblofflinetask.Task_Id, cursor.getString(0));
                map.put(Tables.tblofflinetask.Task_Title, cursor.getString(1));
                map.put(Tables.tblofflinetask.Task_Body, cursor.getString(2));
                map.put(Tables.tblofflinetask.Task_Lat, cursor.getString(3));
                map.put(Tables.tblofflinetask.Task_Lon, cursor.getString(4));
                map.put(Tables.tblofflinetask.Task_Tags, cursor.getString(5));
                map.put(Tables.tblofflinetask.Task_Creat_On, cursor.getString(6));
                map.put(Tables.tblofflinetask.Is_Server_Send, cursor.getString(7));
                map.put(Tables.tblofflinetask.Task_Time, cursor.getString(8));
                map.put(Tables.tblofflinetask.Task_Images_Paths, cursor.getString(9));
                map.put(Tables.tblofflinetask.EXP_ID, cursor.getString(10));
                map.put(Tables.tblofflinetask.EXP_Type, cursor.getString(11));
                map.put(Tables.tblofflinetask.EXP_Value, cursor.getString(12));
                map.put(Tables.tblofflinetask.EXP_Disc, cursor.getString(13));
                map.put(Tables.tblofflinetask.IN_TIME, cursor.getString(14));
                map.put(Tables.tblofflinetask.OUT_TIME, cursor.getString(15));
                map.put(Tables.tblofflinetask.STOP_ID, cursor.getString(16));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    //update offline
    public void OFFLINE_TASK_UPDATE(String _taskName, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tblofflinetask.name + " SET "
                +  Tables.tblofflinetask.Is_Server_Send + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tblofflinetask.Task_Title + " = '" + _taskName+ "'");
    }

    public void EDIT_STOPS(String get_title, String get_body,String get_lat,String get_lon,String get_tags,String get_date,String get_is_server_send,
                           String get_time,String get_image_paths,String get_exp_id,String get_exp_type,String get_exp_value,String get_exp_disc,
                           String get_in_time,String get_out_time,String uneq_id){

        ContentValues values = new ContentValues();
        values.put(Tables.tblofflinetask.Task_Title,get_title);
        values.put(Tables.tblofflinetask.Task_Body,get_body);
        values.put(Tables.tblofflinetask.Task_Lat, get_lat);
        values.put(Tables.tblofflinetask.Task_Lon, get_lon);
        values.put(Tables.tblofflinetask.Task_Tags, get_tags);
        values.put(Tables.tblofflinetask.Task_Creat_On, get_date);
        values.put(Tables.tblofflinetask.Is_Server_Send, get_is_server_send);
        values.put(Tables.tblofflinetask.Task_Time, get_time);
        values.put(Tables.tblofflinetask.Task_Images_Paths, get_image_paths);
        values.put(Tables.tblofflinetask.EXP_ID, get_exp_id);
        values.put(Tables.tblofflinetask.EXP_Type, get_exp_type);
        values.put(Tables.tblofflinetask.EXP_Value, get_exp_value);
        values.put(Tables.tblofflinetask.EXP_Disc, get_exp_disc);
        values.put(Tables.tblofflinetask.IN_TIME, get_in_time);
        values.put(Tables.tblofflinetask.OUT_TIME, get_out_time);

        sqLiteDB.update(Tables.tblofflinetask.name, values, Tables.tblofflinetask.STOP_ID+"= '"+uneq_id+ "'", null);

//        sqLiteDB.insert(Tables.tblofflinetask.name, null, values);
//        sqLiteDB.execSQL("UPDATE " + Tables.tblofflinetask.name + " SET "
//                +  Tables.tblofflinetask.Is_Server_Send + "='" + sendtoserver+ "'"
//                + " WHERE " + Tables.tblofflinetask.Task_Title + " = '" + _taskName+ "'");
    }

    public long getProfilesCount() {
        sqLiteDB = openHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(sqLiteDB, Tables.tblofflinetask.name);
        return cnt;
    }

    //update offline
    public void OFFLINE_TASK_DELETE(String _uniqueid){
        sqLiteDB.execSQL("DELETE  FROM " + Tables.tblofflinetask.name
                + " WHERE " + Tables.tblofflinetask.Task_Id + "=" + _uniqueid);
    }


    //############################################################################################################################

    // Adding tasks
    public void TASK_ADDTASK(model_tasks_pending Task) {

        ContentValues values = new ContentValues();
        values.put(Tables.tbltasks.Tks_id, Task.get_tsk_id());
        values.put(Tables.tbltasks.Task_Nature, Task.get_Nature_task());
        values.put(Tables.tbltasks.Task_Value, Task.get_value());
        values.put(Tables.tbltasks.Task_Remark, Task.get_remark());
        values.put(Tables.tbltasks.Task_Status, Task.get_status_task());
        values.put(Tables.tbltasks.Task_Tags, Task.get_tags());
        values.put(Tables.tbltasks.Task_Creat_On, Task.get_creat_on());
        values.put(Tables.tbltasks.Is_Server_Send, Task.get_is_server_send());
        values.put(Tables.tbltasks.EXP_ID, Task.get_exp_id());
        values.put(Tables.tbltasks.EXP_Type, Task.get_exp_type());
        values.put(Tables.tbltasks.EXP_Value, Task.get_exp_value());
        values.put(Tables.tbltasks.EXP_Disc, Task.get_exp_disc());
        values.put(Tables.tbltasks.LAT, Task.get_lat());
        values.put(Tables.tbltasks.LON, Task.get_lon());
        values.put(Tables.tbltasks.TIME, Task.get_time());

        // Inserting Row
        sqLiteDB.insert(Tables.tbltasks.name, null, values);
//            sqLiteDB.close(); // Closing database connection
    }

    public long getTaskCount() {
        sqLiteDB = openHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(sqLiteDB, Tables.tbltasks.name);
        return cnt;
    }

    public List<HashMap<String, String>> Get_TASK_Pending(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//            String selectEvents = "SELECT "
//                    + Tables.tblofflinetask.Task_Id
//                    + Tables.tblofflinetask.Task_Title +","
//                    + Tables.tblofflinetask.Task_Body +","
//                    + Tables.tblofflinetask.Task_Lat +","
//                    + Tables.tblofflinetask.Task_Lon +","
//                    + Tables.tblofflinetask.Task_Tags +","
//                    + Tables.tblofflinetask.Task_Creat_On+","
//                    + Tables.tblofflinetask.Is_Server_Send+","
//                    + Tables.tblofflinetask.Task_Time
//                    + " FROM " + Tables.tblnotification.name +" WHERE "
//                    + Tables.tblofflinetask.Is_Server_Send + " = '0'";
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltasks.name, null);
//            Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltasks.Task_Id, cursor.getString(0));
                map.put(Tables.tbltasks.Tks_id, cursor.getString(1));
                map.put(Tables.tbltasks.Task_Nature, cursor.getString(2));
                map.put(Tables.tbltasks.Task_Value, cursor.getString(3));
                map.put(Tables.tbltasks.Task_Remark, cursor.getString(4));
                map.put(Tables.tbltasks.Task_Status, cursor.getString(5));
                map.put(Tables.tbltasks.Task_Tags, cursor.getString(6));
                map.put(Tables.tbltasks.Task_Creat_On, cursor.getString(7));
                map.put(Tables.tbltasks.Is_Server_Send, cursor.getString(8));
                map.put(Tables.tbltasks.EXP_ID, cursor.getString(9));
                map.put(Tables.tbltasks.EXP_Value, cursor.getString(10));
                map.put(Tables.tbltasks.EXP_Disc, cursor.getString(11));
                map.put(Tables.tbltasks.LAT, cursor.getString(12));
                map.put(Tables.tbltasks.LON, cursor.getString(13));
                map.put(Tables.tbltasks.TIME, cursor.getString(14));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }

    public Boolean ISTASK_EXIST(String Tag_id){

        String Query = "SELECT "
                + Tables.tbltasks.Task_Id+","
                +Tables.tbltasks.Tks_id+","
                + Tables.tbltasks.Task_Nature +","
                + Tables.tbltasks.Task_Value +","
                + Tables.tbltasks.Task_Remark +","
                + Tables.tbltasks.Task_Status +","
                + Tables.tbltasks.Task_Tags +","
                + Tables.tbltasks.Task_Creat_On+","
                + Tables.tbltasks.Is_Server_Send+","
                + Tables.tbltasks.EXP_ID+","
                + Tables.tbltasks.EXP_Value+","
                + Tables.tbltasks.EXP_Disc+","
                + Tables.tbltasks.LAT+","
                + Tables.tbltasks.LON+","
                + Tables.tbltasks.TIME
                + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Task_Id + " = '" + Tag_id+ "'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public List<HashMap<String, String>> Get_Tasks_Offline_Pending(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbltasks.Task_Id+","
                +Tables.tbltasks.Tks_id+","
                + Tables.tbltasks.Task_Nature +","
                + Tables.tbltasks.Task_Value +","
                + Tables.tbltasks.Task_Remark +","
                + Tables.tbltasks.Task_Status +","
                + Tables.tbltasks.Task_Tags +","
                + Tables.tbltasks.Task_Creat_On+","
                + Tables.tbltasks.Is_Server_Send+","
                + Tables.tbltasks.EXP_ID+","
                + Tables.tbltasks.EXP_Type+","
                + Tables.tbltasks.EXP_Value+","
                + Tables.tbltasks.EXP_Disc+","
                + Tables.tbltasks.LAT+","
                + Tables.tbltasks.LON+","
                + Tables.tbltasks.TIME
                + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Is_Server_Send+ " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltasks.Task_Id, cursor.getString(0));
                map.put(Tables.tbltasks.Tks_id, cursor.getString(1));
                map.put(Tables.tbltasks.Task_Nature, cursor.getString(2));
                map.put(Tables.tbltasks.Task_Value, cursor.getString(3));
                map.put(Tables.tbltasks.Task_Remark, cursor.getString(4));
                map.put(Tables.tbltasks.Task_Status, cursor.getString(5));
                map.put(Tables.tbltasks.Task_Tags, cursor.getString(6));
                map.put(Tables.tbltasks.Task_Creat_On, cursor.getString(7));
                map.put(Tables.tbltasks.Is_Server_Send, cursor.getString(8));
                map.put(Tables.tbltasks.EXP_ID, cursor.getString(9));
                map.put(Tables.tbltasks.EXP_Type, cursor.getString(10));
                map.put(Tables.tbltasks.EXP_Value, cursor.getString(11));
                map.put(Tables.tbltasks.EXP_Disc, cursor.getString(12));
                map.put(Tables.tbltasks.LAT, cursor.getString(13));
                map.put(Tables.tbltasks.LON, cursor.getString(14));
                map.put(Tables.tbltasks.TIME, cursor.getString(15));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    public List<HashMap<String, String>> Get_Today_Task(String date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbltasks.Task_Id+","
                +Tables.tbltasks.Tks_id+","
                + Tables.tbltasks.Task_Nature +","
                + Tables.tbltasks.Task_Value +","
                + Tables.tbltasks.Task_Remark +","
                + Tables.tbltasks.Task_Status +","
                + Tables.tbltasks.Task_Tags +","
                + Tables.tbltasks.Task_Creat_On+","
                + Tables.tbltasks.Is_Server_Send+","
                + Tables.tbltasks.EXP_ID+","
                + Tables.tbltasks.EXP_Type+","
                + Tables.tbltasks.EXP_Value+","
                + Tables.tbltasks.EXP_Disc+","
                + Tables.tbltasks.LAT+","
                + Tables.tbltasks.LON+","
                + Tables.tbltasks.TIME
                + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Task_Creat_On+ " = '"+date+"'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltasks.Task_Id, cursor.getString(0));
                map.put(Tables.tbltasks.Tks_id, cursor.getString(1));
                map.put(Tables.tbltasks.Task_Nature, cursor.getString(2));
                map.put(Tables.tbltasks.Task_Value, cursor.getString(3));
                map.put(Tables.tbltasks.Task_Remark, cursor.getString(4));
                map.put(Tables.tbltasks.Task_Status, cursor.getString(5));
                map.put(Tables.tbltasks.Task_Tags, cursor.getString(6));
                map.put(Tables.tbltasks.Task_Creat_On, cursor.getString(7));
                map.put(Tables.tbltasks.Is_Server_Send, cursor.getString(8));
                map.put(Tables.tbltasks.EXP_ID, cursor.getString(9));
                map.put(Tables.tbltasks.EXP_Type, cursor.getString(10));
                map.put(Tables.tbltasks.EXP_Value, cursor.getString(11));
                map.put(Tables.tbltasks.EXP_Disc, cursor.getString(12));
                map.put(Tables.tbltasks.LAT, cursor.getString(13));
                map.put(Tables.tbltasks.LON, cursor.getString(14));
                map.put(Tables.tbltasks.TIME, cursor.getString(15));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    public List<HashMap<String, String>> Get_CombinedVisit(String Date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "select * from (SELECT "
                + Tables.tbltasks.Task_Creat_On+","
                +  Tables.tbltasks.TIME+" as time,"
                + Tables.tbltasks.LAT+","
                + Tables.tbltasks.LON+","
                + Tables.tbltasks.Task_Nature+","
                + "'task'"
                 + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Task_Creat_On+ " = '"+Date+"'"

                + " Union All "

                + " SELECT "
                + Tables.tblofflinetask.Task_Creat_On+","
                + Tables.tblofflinetask.Task_Time+"  as time,"
                + Tables.tblofflinetask.Task_Lat+","
                + Tables.tblofflinetask.Task_Lon+","
                + Tables.tblofflinetask.Task_Title+","
                + "'stop'"
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Task_Creat_On+ " = '"+Date+"'"

                + ")k order by time"
                ;


        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                   map.put(Tables.tblActivitySumm.Creat_On, cursor.getString(0));
                map.put(Tables.tblActivitySumm.TIME, cursor.getString(1));
                 map.put(Tables.tblActivitySumm.LAT, cursor.getString(2));
                map.put(Tables.tblActivitySumm.LON, cursor.getString(3));
                map.put(Tables.tblActivitySumm.TITLE, cursor.getString(4));
                map.put(Tables.tblActivitySumm.TYPE, cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_Todays_Task(String Date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbltasks.Task_Id+","
                +Tables.tbltasks.Tks_id+","
                + Tables.tbltasks.Task_Nature +","
                + Tables.tbltasks.Task_Value +","
                + Tables.tbltasks.Task_Remark +","
                + Tables.tbltasks.Task_Status +","
                + Tables.tbltasks.Task_Tags +","
                + Tables.tbltasks.Task_Creat_On+","
                + Tables.tbltasks.Is_Server_Send+","
                + Tables.tbltasks.EXP_ID+","
                + Tables.tbltasks.EXP_Value+","
                + Tables.tbltasks.EXP_Disc+","
                + Tables.tbltasks.LAT+","
                + Tables.tbltasks.LON+","
                + Tables.tbltasks.TIME
                + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Task_Creat_On+ " = '"+Date+"'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltasks.Task_Id, cursor.getString(0));
                map.put(Tables.tbltasks.Tks_id, cursor.getString(1));
                map.put(Tables.tbltasks.Task_Nature, cursor.getString(2));
                map.put(Tables.tbltasks.Task_Value, cursor.getString(3));
                map.put(Tables.tbltasks.Task_Remark, cursor.getString(4));
                map.put(Tables.tbltasks.Task_Status, cursor.getString(5));
                map.put(Tables.tbltasks.Task_Tags, cursor.getString(6));
                map.put(Tables.tbltasks.Task_Creat_On, cursor.getString(7));
                map.put(Tables.tbltasks.Is_Server_Send, cursor.getString(8));
                map.put(Tables.tbltasks.EXP_ID, cursor.getString(9));
                map.put(Tables.tbltasks.EXP_Value, cursor.getString(10));
                map.put(Tables.tbltasks.EXP_Disc, cursor.getString(11));
                map.put(Tables.tbltasks.LAT, cursor.getString(12));
                map.put(Tables.tbltasks.LON, cursor.getString(13));
                map.put(Tables.tbltasks.TIME, cursor.getString(14));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


//    public void Task_UPDATE(String autoid,String arthinm,String mob1,String mob2,String adharno  , String ownrship , String vehno
//            , String yrsold   , String btchno   , String howmny   , String driving  , String alruseing, String goyointr ,
//                                   String doyohv   , String prefloc  , String createon , String createdby,
//                                   String sendToServer , String vehtype, String remarks, String lat, String lon,
//                                   String profpic,String profpic_upload,
//                                   String doc1,String doc1_upload,String doc2,String doc2_upload,String doc3,String doc3_upload
//            ,String doc4,String doc4_upload){
//
//        ContentValues values = new ContentValues();
//        values.put(Tables.tbltasks.Task_Nature, arthinm);
//        values.put(Tables.tbltasks.Task_Value, mob1);
//        values.put(Tables.tbltasks.Task_Remark , mob2);
//        values.put(Tables.tbltasks.Task_Status, adharno);
//        values.put(Tables.tbltasks.Task_Tags , ownrship);
//        values.put(Tables.tbltasks. , vehno);
//        values.put(Tables.tbltasks.yrsold , yrsold);
//        values.put(Tables.tbltasks.btchno , btchno);
//        values.put(Tables.tbltasks.howmny , howmny);
//        values.put(Tables.tbltasks.driving , driving);
//        values.put(Tables.tbltasks.alruseing, alruseing);
//
//
//        sqLiteDB.update(Tables.tbl_driver_info.name, values, Tables.tbl_driver_info.autoid + "='"+id+"'", null);
//    }

    //update offline
    public void TASK_UPDATE(String _id, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tbltasks.name + " SET "
                +  Tables.tbltasks.Is_Server_Send + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tbltasks.Tks_id + " = '" + _id+ "'");
    }

//    public long getProfilesCount() {
//        sqLiteDB = openHelper.getReadableDatabase();
//        long cnt  = DatabaseUtils.queryNumEntries(sqLiteDB, Tables.tblofflinetask.name);
//        return cnt;
//    }

    //update offline
    public void TASK_DELETE(String _uniqueid){
        sqLiteDB.execSQL("DELETE  FROM " + Tables.tbltasks.name
                + " WHERE " + Tables.tbltasks.Task_Id + " = '" + _uniqueid+ "'");
    }


    //adding Tags
    //############################################################################################################################
    public void TAG_ADDTAG(model_tag Tag) {

        ContentValues values = new ContentValues();
        values.put(Tables.tbltags.Tag_Title, Tag.get_title());
        values.put(Tables.tbltags.Tag_remark_1, Tag.get_rem_1());
        values.put(Tables.tbltags.Tag_remark_2, Tag.get_rem_2());
        values.put(Tables.tbltags.Tag_remark_3, Tag.get_rem_3());
        values.put(Tables.tbltags.Tag_Empl_Id, Tag.get_empl_id());
        values.put(Tables.tbltags.Tag_Creat_On, Tag.get_creat_on());
        values.put(Tables.tbltags.Is_Server_Send, Tag.get_is_server_send());

        // Inserting Row
        sqLiteDB.insert(Tables.tbltags.name, null, values);
//        sqLiteDB.close(); // Closing database connection
    }

   public Boolean ISTAG_ALREDY_EXIST(String Tag_Name){

       String Query = "SELECT "
               + Tables.tbltags.Tag_Id+","
               + Tables.tbltags.Tag_Title+","
               + Tables.tbltags.Tag_remark_1 +","
               + Tables.tbltags.Tag_remark_2 +","
               + Tables.tbltags.Tag_remark_3 +","
               + Tables.tbltags.Tag_Empl_Id +","
               + Tables.tbltags.Tag_Creat_On +","
               + Tables.tbltags.Is_Server_Send
               + " FROM " + Tables.tbltags.name +" WHERE "
               + Tables.tbltags.Tag_Title + " = '" + Tag_Name+ "'";
           Cursor cursor = sqLiteDB.rawQuery(Query, null);
           if(cursor.getCount() <= 0){
               cursor.close();
               return false;
           }
           cursor.close();
           return true;
    }

    public List<HashMap<String, String>> Get_Tags(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//        String selectEvents = "SELECT "
//                + Tables.tbltags.Tag_Id
//                + Tables.tbltags.Tag_Title+","
//                + Tables.tbltags.Tag_remark_1 +","
//                + Tables.tbltags.Tag_remark_2 +","
//                + Tables.tbltags.Tag_remark_3 +","
//                + Tables.tbltags.Tag_Creat_On +","
//                + Tables.tbltags.Is_Server_Send
//                + " FROM " + Tables.tbltags.name +" WHERE "
//                + Tables.tbltags.Is_Server_Send + " = '0'";
//        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltags.Tag_Id, cursor.getString(0));
                map.put(Tables.tbltags.Tag_Title, cursor.getString(1));
                map.put(Tables.tbltags.Tag_remark_1, cursor.getString(2));
                map.put(Tables.tbltags.Tag_remark_2, cursor.getString(3));
                map.put(Tables.tbltags.Tag_remark_3, cursor.getString(4));
                map.put(Tables.tbltags.Tag_Empl_Id, cursor.getString(5));
                map.put(Tables.tbltags.Tag_Creat_On, cursor.getString(6));
                map.put(Tables.tbltags.Is_Server_Send, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_Tags_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbltags.Tag_Id+","
                + Tables.tbltags.Tag_Title+","
                + Tables.tbltags.Tag_remark_1 +","
                + Tables.tbltags.Tag_remark_2 +","
                + Tables.tbltags.Tag_remark_3 +","
                + Tables.tbltags.Tag_Empl_Id +","
                + Tables.tbltags.Tag_Creat_On +","
                + Tables.tbltags.Is_Server_Send
                + " FROM " + Tables.tbltags.name +" WHERE "
                + Tables.tbltags.Is_Server_Send + " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltags.Tag_Id, cursor.getString(0));
                map.put(Tables.tbltags.Tag_Title, cursor.getString(1));
                map.put(Tables.tbltags.Tag_remark_1, cursor.getString(2));
                map.put(Tables.tbltags.Tag_remark_2, cursor.getString(3));
                map.put(Tables.tbltags.Tag_remark_3, cursor.getString(4));
                map.put(Tables.tbltags.Tag_Empl_Id, cursor.getString(5));
                map.put(Tables.tbltags.Tag_Creat_On, cursor.getString(6));
                map.put(Tables.tbltags.Is_Server_Send, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    //update offline
    public void TAG_UPDATE(String _tagName, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tbltags.name + " SET "
                +  Tables.tbltags.Is_Server_Send + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tbltags.Tag_Title + " = '" + _tagName+ "'");
    }


    //update offline
    public void TAG_DELETE(String _uniqueid){
        sqLiteDB.execSQL("DELETE FROM " + Tables.tbltags.name
                + " WHERE " + Tables.tbltags.Tag_Id + "=" + _uniqueid);
    }




    //adding expense
    //############################################################################################################################
    public void ADDEXPENSE(model_expense Expense) {

        ContentValues values = new ContentValues();
        values.put(Tables.tblexpense.Exp_ID, Expense.get_exp_id());
        values.put(Tables.tblexpense.Expense_Name, Expense.get_name());
        values.put(Tables.tblexpense.Expense_Disc, Expense.get_disc());
        values.put(Tables.tblexpense.Expense_Value, Expense.get_value());
        values.put(Tables.tblexpense.Expense_Code, Expense.get_code());
        values.put(Tables.tblexpense.Expense_Is_Active, Expense.get_is_active());
        values.put(Tables.tblexpense.Expense_Server,Expense.get_is_server());
        values.put(Tables.tblexpense.Approval_Amount,Expense.get_appr_amt());


        // Inserting Row
        sqLiteDB.insert(Tables.tblexpense.name, null, values);
//        sqLiteDB.close(); // Closing database connection
    }

    public Boolean ISEXPENSE_ALREDY_EXIST(String Tag_Name){

        String Query = "SELECT "
                + Tables.tblexpense.Expense_Id+","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Name + " = '" + Tag_Name+ "'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public Boolean ISEXPENSE_APPRO_ALREDY_EXIST(String CreatedBy,String Time){

        String Query = "SELECT "
                + Tables.tblexpense.Expense_Id+","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Code + " = '" + CreatedBy+ "'"
                + " AND "
                + Tables.tblexpense.Exp_ID + " = '"+Time+"'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //update Approval amount
    public void EXPENSE_UPDATE_APPR(String amount, String date,String time){
        sqLiteDB.execSQL("UPDATE " + Tables.tblexpense.name + " SET "
                +  Tables.tblexpense.Approval_Amount + "='" + amount+ "'"
                +" WHERE "
                + Tables.tblexpense.Expense_Code + " = '" + date+ "'"
                + " AND "
                + Tables.tblexpense.Exp_ID + " = '"+time+"'");
    }

    public List<HashMap<String, String>> Get_Expense(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//        String selectEvents = "SELECT "
//                + Tables.tbltags.Tag_Id
//                + Tables.tbltags.Tag_Title+","
//                + Tables.tbltags.Tag_remark_1 +","
//                + Tables.tbltags.Tag_remark_2 +","
//                + Tables.tbltags.Tag_remark_3 +","
//                + Tables.tbltags.Tag_Creat_On +","
//                + Tables.tbltags.Is_Server_Send
//                + " FROM " + Tables.tbltags.name +" WHERE "
//                + Tables.tbltags.Is_Server_Send + " = '0'";
//        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tblexpense.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Id, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(6));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    public List<HashMap<String, String>> Get_Expenses_Display(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense.Expense_Id+","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Server + " = '0'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Id, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(6));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(7));
                map.put(Tables.tblexpense.Approval_Amount, cursor.getString(8));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_Expenses_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense.Expense_Id+","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Server + " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Id, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(6));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(7));
                map.put(Tables.tblexpense.Approval_Amount, cursor.getString(8));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }



    public List<HashMap<String, String>> Get_CombinedTasksOnly(String Date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Server + " = '1' "
                + " OR "
                + Tables.tblexpense.Expense_Server + " = '2'"
                + " AND "
                + Tables.tblexpense.Expense_Code + " = '"+Date+"'"

                + " Union All "

                + " SELECT "
                + Tables.tbltasks.Task_Creat_On+","
                +  Tables.tbltasks.TIME+","
                + Tables.tbltasks.EXP_Type+","
                + Tables.tbltasks.EXP_Disc+","
                + Tables.tbltasks.EXP_Value+","
                + Tables.tbltasks.Task_Tags+","
                + "'task',"
                + "'Pending'"
                + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Task_Creat_On+ " = '"+Date+"'"
                + " AND "
                + Tables.tbltasks.EXP_Type + "<>''"

//                + ")k order by time"
                ;


        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(6));
                map.put(Tables.tblexpense.Approval_Amount, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    public List<HashMap<String, String>> Get_CombinedStop_Only(String Date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Server + " = '1' "
                + " OR "
                + Tables.tblexpense.Expense_Server + " = '2'"
                + " AND "
                + Tables.tblexpense.Expense_Code + " = '"+Date+"'"

                + " Union All "

                + " SELECT "
                + Tables.tblofflinetask.Task_Creat_On+","
                + Tables.tblofflinetask.Task_Time+","
                + Tables.tblofflinetask.EXP_Type+","
                + Tables.tblofflinetask.EXP_Disc+","
                + Tables.tblofflinetask.EXP_Value+","
                + Tables.tblofflinetask.Task_Tags+","
                + "'stop',"
                + "'Pending'"
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Task_Creat_On+ " = '"+Date+"'"
                + " AND "
                + Tables.tblofflinetask.EXP_Type + "<>''"

//                + ")k order by time"
                ;


        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(6));
                map.put(Tables.tblexpense.Approval_Amount, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }




    public List<HashMap<String, String>> Get_CombinedExpense(String Date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Server + " = '1' "
                + " OR "
                + Tables.tblexpense.Expense_Server + " = '2'"
                + " AND "
                + Tables.tblexpense.Expense_Code + " = '"+Date+"'"

                + " Union All "

                + " SELECT "
                + Tables.tbltasks.Task_Creat_On+","
                +  Tables.tbltasks.TIME+","
                + Tables.tbltasks.EXP_Type+","
                + Tables.tbltasks.EXP_Disc+","
                + Tables.tbltasks.EXP_Value+","
                + Tables.tbltasks.Task_Tags+","
                + "'task',"
                + "'Pending'"
                + " FROM " + Tables.tbltasks.name +" WHERE "
                + Tables.tbltasks.Task_Creat_On+ " = '"+Date+"'"
                + " AND "
                + Tables.tbltasks.EXP_Type + "<>''"


                + " Union All "

                + " SELECT "
                + Tables.tblofflinetask.Task_Creat_On+","
                + Tables.tblofflinetask.Task_Time+","
                + Tables.tblofflinetask.EXP_Type+","
                + Tables.tblofflinetask.EXP_Disc+","
                + Tables.tblofflinetask.EXP_Value+","
                + Tables.tblofflinetask.Task_Tags+","
                + "'stop',"
                + "'Pending'"
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Task_Creat_On+ " = '"+Date+"'"
                + " AND "
                + Tables.tblofflinetask.EXP_Type + "<>''"

//                + ")k order by time"
                ;


        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(6));
                map.put(Tables.tblexpense.Approval_Amount, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_Expenses_ALL(String date){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense.Expense_Code +","
                + Tables.tblexpense.Exp_ID+","
                + Tables.tblexpense.Expense_Name+","
                + Tables.tblexpense.Expense_Disc +","
                + Tables.tblexpense.Expense_Value +","
                + Tables.tblexpense.Expense_Is_Active +","
                + Tables.tblexpense.Expense_Server +","
                + Tables.tblexpense.Approval_Amount
                + " FROM " + Tables.tblexpense.name +" WHERE "
                + Tables.tblexpense.Expense_Server + " = '1' "
                + " OR "
                 + Tables.tblexpense.Expense_Server + " = '2'"
                + " AND "
                + Tables.tblexpense.Expense_Code + " = '"+date+"'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense.Expense_Code, cursor.getString(0));
                map.put(Tables.tblexpense.Exp_ID, cursor.getString(1));
                map.put(Tables.tblexpense.Expense_Name, cursor.getString(2));
                map.put(Tables.tblexpense.Expense_Disc, cursor.getString(3));
                map.put(Tables.tblexpense.Expense_Value, cursor.getString(4));
                map.put(Tables.tblexpense.Expense_Is_Active, cursor.getString(5));
                map.put(Tables.tblexpense.Expense_Server, cursor.getString(6));
                map.put(Tables.tblexpense.Approval_Amount, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public boolean tableExists(String tableName)
    {
        if (tableName == null || sqLiteDB == null || !sqLiteDB.isOpen())
        {
            return false;
        }
        Cursor cursor = sqLiteDB.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }


    //update offline
    public void EXPENSE_UPDATE(String _Exp_Id, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tblexpense.name + " SET "
                +  Tables.tblexpense.Expense_Server + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tblexpense.Expense_Id+ " = '" + _Exp_Id+ "'");
    }




//    //update offline
//    public void TAG_DELETE(String _uniqueid){
//        sqLiteDB.execSQL("DELETE FROM " + Tables.tbltags.name
//                + " WHERE " + Tables.tbltags.Tag_Id + "=" + _uniqueid);
//    }




    //adding leave
    //############################################################################################################################
    public void ADDLeave(modal_leave Leave) {

        ContentValues values = new ContentValues();
        values.put(Tables.tblleave.Leave_From, Leave.get_leave_from());
        values.put(Tables.tblleave.Leave_To, Leave.get_leave_to());
        values.put(Tables.tblleave.Leave_Type, Leave.get_leave_type());
        values.put(Tables.tblleave.Leave_Details, Leave.get_leave_details());
        values.put(Tables.tblleave.Leave_Created_By, Leave.get_leave_created());
        values.put(Tables.tblleave.Leave_Status, Leave.get_leave_status());
        values.put(Tables.tblleave.Leave_Server, Leave.get_is_server_send());

        // Inserting Row
        sqLiteDB.insert(Tables.tblleave.name, null, values);
//        sqLiteDB.close(); // Closing database connection
    }

    public Boolean ISLeave_ALREDY_EXIST(String created_by){

        String Query = "SELECT "
                + Tables.tblleave.Leave_Id+","
                + Tables.tblleave.Leave_From+","
                + Tables.tblleave.Leave_To+","
                + Tables.tblleave.Leave_Type +","
                + Tables.tblleave.Leave_Details +","
                + Tables.tblleave.Leave_Created_By +","
                + Tables.tblleave.Leave_Status +","
                + Tables.tblleave.Leave_Server
                + " FROM " + Tables.tblleave.name +" WHERE "
                + Tables.tblleave.Leave_Created_By + " = '" + created_by+ "'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<HashMap<String, String>> Get_Leave(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//        String selectEvents = "SELECT "
//                + Tables.tbltags.Tag_Id
//                + Tables.tbltags.Tag_Title+","
//                + Tables.tbltags.Tag_remark_1 +","
//                + Tables.tbltags.Tag_remark_2 +","
//                + Tables.tbltags.Tag_remark_3 +","
//                + Tables.tbltags.Tag_Creat_On +","
//                + Tables.tbltags.Is_Server_Send
//                + " FROM " + Tables.tbltags.name +" WHERE "
//                + Tables.tbltags.Is_Server_Send + " = '0'";
//        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tblleave.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblleave.Leave_Id, cursor.getString(0));
                map.put(Tables.tblleave.Leave_From, cursor.getString(1));
                map.put(Tables.tblleave.Leave_To, cursor.getString(2));
                map.put(Tables.tblleave.Leave_Type, cursor.getString(3));
                map.put(Tables.tblleave.Leave_Details, cursor.getString(4));
                map.put(Tables.tblleave.Leave_Created_By, cursor.getString(5));
                map.put(Tables.tblleave.Leave_Status, cursor.getString(6));
                map.put(Tables.tblleave.Leave_Server, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    public List<HashMap<String, String>> Get_Leave_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblleave.Leave_Id+","
                + Tables.tblleave.Leave_From+","
                + Tables.tblleave.Leave_To+","
                + Tables.tblleave.Leave_Type +","
                + Tables.tblleave.Leave_Details +","
                + Tables.tblleave.Leave_Created_By +","
                + Tables.tblleave.Leave_Status +","
                + Tables.tblleave.Leave_Server
                + " FROM " + Tables.tblleave.name +" WHERE "
                + Tables.tblleave.Leave_Server + " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblleave.Leave_Id, cursor.getString(0));
                map.put(Tables.tblleave.Leave_From, cursor.getString(1));
                map.put(Tables.tblleave.Leave_To, cursor.getString(2));
                map.put(Tables.tblleave.Leave_Type, cursor.getString(3));
                map.put(Tables.tblleave.Leave_Details, cursor.getString(4));
                map.put(Tables.tblleave.Leave_Created_By, cursor.getString(5));
                map.put(Tables.tblleave.Leave_Status, cursor.getString(6));
                map.put(Tables.tblleave.Leave_Server, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }



    //update offline
    public void Leave_UPDATE(String Status, String createdOn){
        sqLiteDB.execSQL("UPDATE " + Tables.tblleave.name + " SET "
                +  Tables.tblleave.Leave_Status + "='" + Status+ "'"
                + " WHERE " + Tables.tblleave.Leave_Created_By+ " = '" + createdOn+ "'");
    }

public void Leave_UPDATE_Status(String _created_by, String status){
        sqLiteDB.execSQL("UPDATE " + Tables.tblleave.name + " SET "
                +  Tables.tblleave.Leave_Status + "='" + status+ "'"
                + " WHERE " + Tables.tblleave.Leave_Created_By+ " = '" + _created_by+ "'");
    }



//    //update offline
//    public void TAG_DELETE(String _uniqueid){
//        sqLiteDB.execSQL("DELETE FROM " + Tables.tbltags.name
//                + " WHERE " + Tables.tbltags.Tag_Id + "=" + _uniqueid);
//    }




    //adding all expense
    //############################################################################################################################
    public void TAG_ADDEXP_ALL(model_expense_all exp) {

        ContentValues values = new ContentValues();
        values.put(Tables.tblexpense_all.Expense_Type, exp.get_type());
        values.put(Tables.tblexpense_all.Expense_Value, exp.get_value());
        values.put(Tables.tblexpense_all.Expense_Tags, exp.get_tags());
        values.put(Tables.tblexpense_all.Expense_Disc, exp.get_disc());
        values.put(Tables.tblexpense_all.Exp_Created_By, exp.get_created_by());
        values.put(Tables.tblexpense_all.Expense_Server, exp.get_is_server());

        // Inserting Row
        sqLiteDB.insert(Tables.tblexpense_all.name, null, values);
//        sqLiteDB.close(); // Closing database connection
    }

    public Boolean ISEXPENSE_ALL_ALREDY_EXIST(String Tag_Name){

        String Query = "SELECT "
                + Tables.tblexpense_all.Expense_Id+","
                + Tables.tblexpense_all.Expense_Type+","
                + Tables.tblexpense_all.Expense_Value +","
                + Tables.tblexpense_all.Expense_Tags +","
                + Tables.tblexpense_all.Expense_Disc +","
                + Tables.tblexpense_all.Exp_Created_By +","
                + Tables.tblexpense_all.Expense_Server
                + " FROM " + Tables.tblexpense_all.name +" WHERE "
                + Tables.tblexpense_all.Exp_Created_By + " = '" + Tag_Name+ "'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<HashMap<String, String>> Get_EXPENSE_ALL(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//        String selectEvents = "SELECT "
//                + Tables.tbltags.Tag_Id
//                + Tables.tbltags.Tag_Title+","
//                + Tables.tbltags.Tag_remark_1 +","
//                + Tables.tbltags.Tag_remark_2 +","
//                + Tables.tbltags.Tag_remark_3 +","
//                + Tables.tbltags.Tag_Creat_On +","
//                + Tables.tbltags.Is_Server_Send
//                + " FROM " + Tables.tbltags.name +" WHERE "
//                + Tables.tbltags.Is_Server_Send + " = '0'";
//        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tblexpense_all.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense_all.Expense_Id, cursor.getString(0));
                map.put(Tables.tblexpense_all.Expense_Type, cursor.getString(1));
                map.put(Tables.tblexpense_all.Expense_Value, cursor.getString(2));
                map.put(Tables.tblexpense_all.Expense_Tags, cursor.getString(3));
                map.put(Tables.tblexpense_all.Expense_Disc, cursor.getString(4));
                map.put(Tables.tblexpense_all.Exp_Created_By, cursor.getString(5));
                map.put(Tables.tblexpense_all.Expense_Server, cursor.getString(6));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_EXPENSE_ALL_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblexpense_all.Expense_Id+","
                + Tables.tblexpense_all.Expense_Type+","
                + Tables.tblexpense_all.Expense_Value +","
                + Tables.tblexpense_all.Expense_Tags +","
                + Tables.tblexpense_all.Expense_Disc +","
                + Tables.tblexpense_all.Exp_Created_By +","
                + Tables.tblexpense_all.Expense_Server
                + " FROM " + Tables.tblexpense_all.name +" WHERE "
                + Tables.tblexpense_all.Expense_Server + " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblexpense_all.Expense_Id, cursor.getString(0));
                map.put(Tables.tblexpense_all.Expense_Type, cursor.getString(1));
                map.put(Tables.tblexpense_all.Expense_Value, cursor.getString(2));
                map.put(Tables.tblexpense_all.Expense_Tags, cursor.getString(3));
                map.put(Tables.tblexpense_all.Expense_Disc, cursor.getString(4));
                map.put(Tables.tblexpense_all.Exp_Created_By, cursor.getString(5));
                map.put(Tables.tblexpense_all.Expense_Server, cursor.getString(6));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    //update offline
    public void EXPENSE_ALL_UPDATE(String _tagName, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tblexpense_all.name + " SET "
                +  Tables.tblexpense_all.Expense_Server + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tblexpense_all.Exp_Created_By + " = '" + _tagName+ "'");
    }


    //############################################################################################################################
    class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(Procedures.tbl_driver_info.CREATE);
            db.execSQL(Procedures.tblnotification.CREATE);
            db.execSQL(Procedures.tbltag.CREATE);
            db.execSQL(Procedures.tblofflinetask.CREATE);
            db.execSQL(Procedures.tbltask.CREATE);
            db.execSQL(Procedures.tblexpense.CREATE);
            db.execSQL(Procedures.tblleave.CREATE);
//            db.execSQL(Procedures.tblexpense_all.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
            switch (oldVersion)
            {
                case 1://upgrade from version 1 to 2

                  break;
                case 2://upgrade from version 2 to 3

                    break;

            }
        }
    }

    public void close(){
        try{
        //openHelper.close();
        //sqLiteDB.close();
            }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
