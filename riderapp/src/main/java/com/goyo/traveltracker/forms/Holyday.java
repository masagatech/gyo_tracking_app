package com.goyo.traveltracker.forms;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Holyday extends AppCompatActivity {
  private ArrayList<String> FromDates;
    private ArrayList<String> ToDates;
    private  List<String> DateNames;
    CaldroidFragment caldroidFragment;
    private TextView DateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holyday);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Holidays");

        DateName=(TextView)findViewById(R.id.DateName);


        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.call, caldroidFragment);
        t.commit();

        GetHolyDays();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String Date=df.format(date);
                if(FromDates!=null){
                    for(int i=0;i<FromDates.size();i++) {
                        if(FromDates.get(i).equals(Date)){
                            if(DateNames!=null) {
                                DateName.setText(DateNames.get(i));
                            }
                        }
                    }
                }

            }
        };
        caldroidFragment.setCaldroidListener(listener);


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

    private void SetHolyday (Date  Dates){
        ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.orange_light));
            caldroidFragment.setBackgroundDrawableForDate(red, Dates);
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

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
