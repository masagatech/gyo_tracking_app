package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.ComplatedOrderAdapter;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.model.model_completed;
import com.goyo.traveltracker.model.model_task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class complated_order extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private com.goyo.traveltracker.adapters.ComplatedOrderAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<model_task> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complated_order);

        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setLogo(R.drawable.rider_del);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle(getResources().getString(R.string.Complated_Order));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);

       //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //api call
                DataFromServer();
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items and get data from server
                DataFromServer();
            }
        });

    }


    private void DataFromServer(){
//
//            loader = new ProgressDialog(this);
//            loader.setCancelable(false);
//            loader.setMessage(getResources().getString(R.string.wait_msg));
//            loader.show();


        data= populateList();

        if (data.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new ComplatedOrderAdapter(data, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);

//        JsonObject json = new JsonObject();
//        json.addProperty("uid", Global.loginusr.getDriverid());
//        json.addProperty("flag", "uid");
//        Ion.with(this)
//                .load(getTripStops.value)
//                .setJsonObjectBody(json)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        // do stuff with the result or error
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            // JSONObject jsnobject = new JSONObject(jsond);
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_completed>>() {
//                            }.getType();
//                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
//                            bindCurrentTrips(events);
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
////                        if(flag==1){
////                            loader.hide();
////                        }else {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                        }

                    }
//                });

    private ArrayList<model_task> populateList(){
        SQLBase db = new SQLBase(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        ArrayList<model_task> data = new ArrayList<model_task>();
        List<HashMap<String,String>> d = db.Get_Today_Stops(formattedDate);
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_task(d.get(i).get(Tables.tblofflinetask.Task_Title),d.get(i).get(Tables.tblofflinetask.Task_Body),d.get(i).get(Tables.tblofflinetask.Task_Lat),d.get(i).get(Tables.tblofflinetask.Task_Lon),d.get(i).get(Tables.tblofflinetask.Task_Tags),d.get(i).get(Tables.tblofflinetask.Task_Creat_On),d.get(i).get(Tables.tblofflinetask.Is_Server_Send),d.get(i).get(Tables.tblofflinetask.Task_Time),d.get(i).get(Tables.tblofflinetask.Task_Images_Paths),d.get(i).get(Tables.tblofflinetask.EXP_ID),d.get(i).get(Tables.tblofflinetask.EXP_Type),d.get(i).get(Tables.tblofflinetask.EXP_Value),d.get(i).get(Tables.tblofflinetask.EXP_Disc),d.get(i).get(Tables.tblofflinetask.IN_TIME),d.get(i).get(Tables.tblofflinetask.OUT_TIME),d.get(i).get(Tables.tblofflinetask.STOP_ID)));
            }
        }

        return data;
    }

//        Ion.with(this)
//                .load("GET", getOrders.value)
//                .addQuery("flag", "completed")
//                .addQuery("subflag", "smry")
//                .addQuery("rdid", Global.loginusr.getDriverid() + "")
//                .addQuery("stat","1")
//
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_completed>>() {
//                            }.getType();
//                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
//                            bindCurrentTrips(events);
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
////                        if(flag==1){
////                            loader.hide();
////                        }else {
//                            mSwipeRefreshLayout.setRefreshing(false);
////                        }
//
//                    }
//                });
//    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }
    private void bindCurrentTrips(List<model_completed> lst) {
//        if (lst.size() > 0) {
//            mRecyclerView.setVisibility(View.VISIBLE);
//            findViewById(R.id.txtNodata).setVisibility(View.GONE);
//            mTimeLineAdapter = new ComplatedOrderAdapter(lst, mOrientation, mWithLinePadding);
//            mRecyclerView.setAdapter(mTimeLineAdapter);
//            mTimeLineAdapter.notifyDataSetChanged();
//        } else {
//            mRecyclerView.setVisibility(View.INVISIBLE);
//            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
//        }
    }



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
