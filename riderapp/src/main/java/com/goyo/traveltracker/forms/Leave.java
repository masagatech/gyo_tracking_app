package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.modal_leave;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.roomorama.caldroid.CaldroidFragment;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.goyo.traveltracker.database.Tables.tblleave.Leave_From;
import static com.goyo.traveltracker.database.Tables.tblleave.Leave_Status;
import static com.goyo.traveltracker.database.Tables.tblleave.Leave_To;

public class Leave extends AppCompatActivity {

    private  List<Date> Dates;
    private ProgressDialog loader;
    CaldroidFragment caldroidFragment;
    private ArrayList<String> FromDates;
    private ArrayList<String> ToDates;
    private  List<String> DateNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);



        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.Rejected_Order));


       caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.cal, caldroidFragment);
        t.commit();

        SetDates();
        GetHolyDays();



    }

    private void SetDates(){
        SQLBase db = new SQLBase(this);

        ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue_light));
        ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.red_light));
        ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green_light));

        ArrayList<modal_leave> data = new ArrayList<modal_leave>();
        List<HashMap<String, String>> d = db.Get_Leave();
        if (d.size() > 0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new modal_leave(d.get(i).get(Leave_From), d.get(i).get(Leave_To), d.get(i).get(Tables.tblleave.Leave_Type), d.get(i).get(Tables.tblleave.Leave_Details), d.get(i).get(Tables.tblleave.Leave_Created_By), d.get(i).get(Tables.tblleave.Leave_Server), d.get(i).get(Leave_Status)));

                if(d.get(i).get(Leave_Status).equals("Pending")) {

                    Dates = getDates(d.get(i).get(Leave_From), d.get(i).get(Leave_To));
                    for (int j = 0; j <= Dates.size() - 1; j++) {
                        caldroidFragment.setBackgroundDrawableForDate(blue, Dates.get(j));

                    }
                }else if(d.get(i).get(Leave_Status).equals("Accpeted")) {

                    Dates = getDates(d.get(i).get(Leave_From), d.get(i).get(Leave_To));
                    for (int j = 0; j <= Dates.size() - 1; j++) {
                        caldroidFragment.setBackgroundDrawableForDate(green, Dates.get(j));

                    }
                }else if(d.get(i).get(Leave_Status).equals("Rejected")){

                    Dates = getDates(d.get(i).get(Leave_From), d.get(i).get(Leave_To));
                    for (int j = 0; j <= Dates.size() - 1; j++) {
                        caldroidFragment.setBackgroundDrawableForDate(red, Dates.get(j));

                    }
                }
            }
            caldroidFragment.refreshView();
        }
    }
    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


    private void GetStatus(){

        loader = new ProgressDialog(this);
        loader.setCancelable(false);
        loader.setMessage(this.getString(R.string.wait_msg));
        loader.show();

       JsonObject json = new JsonObject();
        json.addProperty("empid",Global.loginusr.getDriverid()+"");
        json.addProperty("flag", "approved");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(this)
                .load(Global.urls.getEmployeeLeave.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            // JSONObject jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<modal_leave>>() {
                            }.getType();
                            List<modal_leave> events = (List<modal_leave>) gson.fromJson(result.get("data"), listType);
                            SavetoDb(events);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        SetDates();
                        caldroidFragment.refreshView();
                        loader.hide();


                    }
                });

    }

    private void SavetoDb(List<modal_leave> lst) {
        SQLBase db = new SQLBase(this);
        if (lst.size() > 0) {
            for (int i = 0; i <= lst.size() - 1; i++) {

                if (db.ISLeave_ALREDY_EXIST((lst.get(i).mob_createdon))) {
                    db.Leave_UPDATE(lst.get(i).statusdesc,lst.get(i).mob_createdon);
                }
            }
        }

    }

    private void GetHolyDays(){
        JsonObject json = new JsonObject();

        json.addProperty("empid",Global.loginusr.getDriverid()+"");
        json.addProperty("flag", "byemp");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(this)
                .load(Global.urls.getHoliday.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            FromDates =new ArrayList<String>();
                            ToDates =new ArrayList<String>();
                            DateNames=new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                FromDates.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("frmdt").getAsString());
                                ToDates.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("todt").getAsString());
                                DateNames.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("hldnm").getAsString());
                            }

                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++) {
                                List<Date> Date=getDates(FromDates.get(i),ToDates.get(i));
                                for (int j=0;j<Date.size();j++) {
                                    SetHolyday(Date.get(j));
                                }
//                                Dates.addAll(Date);
                            }
                            caldroidFragment.refreshView();
//                            SetHolyday(Dates);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });

    }

    private void SetHolyday (Date  Dates){
        ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.orange_light));
        caldroidFragment.setBackgroundDrawableForDate(red, Dates);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_info_view_activity, menu);
        return true;
    }


    //action bar menu button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_driver_info_view_add:
                Intent intent=new Intent(this,AddLeave.class);
                startActivity(intent);
                return true;

            case R.id.Sync:
                GetStatus();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
