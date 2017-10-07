package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.RejectedOrderAdapter;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_expense;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class rejected_order extends AppCompatActivity {
    private com.goyo.traveltracker.adapters.RejectedOrderAdapter mTimeLineAdapter;
    private RecyclerView mRecyclerView;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private ArrayList<model_expense> data;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatePickerTimeline timeline;
   private String SelectedDate;
    private  ArrayList<model_expense> datas;
    TextView Exp,Stops,Tasks,Totals;
    private CheckBox Check_Task,Check_stops;
    private ImageButton Back,Add_Expense;
    String Status="Both_Selected";
    Boolean StopChecked=true,TaskChecked=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.exp_actionbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);

//        if(getSupportActionBar()!=null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

//        setTitle("Expense");


        //Action Bar

        Check_stops=(CheckBox) findViewById(R.id.check_stops) ;
        Check_Task=(CheckBox) findViewById(R.id.check_tasks) ;
        Back=(ImageButton)findViewById(R.id.Back) ;
        Add_Expense=(ImageButton)findViewById(R.id.AddExpense) ;

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Add_Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rejected_order.this,expense.class);
                startActivity(intent);
            }
        });

        Check_stops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    StopChecked=true;
                    if(TaskChecked){
                        Status="Both_Selected";
                    }else {
                        Status = "Stop_Selected";
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    DataFromServer(Status);

                }else {
                    StopChecked=false;
                    if(TaskChecked){
                        Status = "Task_Selected";
                    }else {
                        Status = "None_Selected";
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    DataFromServer(Status);
                }

            }
        });

        Check_Task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    TaskChecked=true;
                    if(StopChecked){
                        Status="Both_Selected";
                    }else {
                        Status = "Task_Selected";
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    DataFromServer(Status);
                }else {
                    TaskChecked=false;
                    if(StopChecked){
                        Status="Stop_Selected";
                    }else {
                        Status="None_Selected";
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    DataFromServer(Status);
                }

            }
        });


        Exp=(TextView)findViewById(R.id.exp_value);
        Stops=(TextView)findViewById(R.id.stop_value);
        Tasks=(TextView)findViewById(R.id.task_value);
        Totals=(TextView)findViewById(R.id.total_value);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);

        timeline = (DatePickerTimeline)findViewById(R.id.DatePicker);
        timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {

                mSwipeRefreshLayout.setRefreshing(true);
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                SelectedDate = df.format(cal.getTime());
                DataFromServer(Status);
            }
        });

        //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //api call
                DataFromServer(Status);
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items and get data from server

                DataFromServer(Status);
            }
        });

    }

    private void DataFromServer(String Status) {

        data= populateList(Status);
        GetApprovedAmount(data);

    }

    private void Data(ArrayList<model_expense> data){

        int Exp_total=0,Stop_total=0,Task_total=0,Total=0;
        if(data.size()>0) {

            for (int i = 0; i <= data.size() - 1; i++) {
                if(data.get(i)._is_server.equals("task")) {
                    Task_total = Task_total + Integer.parseInt(data.get(i)._value);
                }else if((data.get(i)._is_server.equals("stop"))){
                    Stop_total = Stop_total + Integer.parseInt(data.get(i)._value);
                } else if((data.get(i)._is_server.equals("1"))||(data.get(i)._is_server.equals("2"))){
                    Exp_total = Exp_total + Integer.parseInt(data.get(i)._value);
                }
            }
        }
        Total=Exp_total+Stop_total+Task_total;

        Exp.setText("₹ "+Exp_total+"");
        Stops.setText("₹ "+Stop_total+"");
        Tasks.setText("₹ "+Task_total+"");
        Totals.setText(" = ₹ "+Total+"");


        if (data.size() > 0) {
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTimeLineAdapter = new RejectedOrderAdapter(data, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }


    private void GetApprovedAmount(ArrayList<model_expense> data){
        datas=new ArrayList<>();
        datas=data;
        JsonObject json = new JsonObject();
        json.addProperty("empid",Global.loginusr.getDriverid()+"");
        json.addProperty("flag", "approved");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(this)
                .load(Global.urls.getVoucherDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            // JSONObject jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_expense>>() {
                            }.getType();
                            List<model_expense> events = (List<model_expense>) gson.fromJson(result.get("data"), listType);
                            datas= SavetoDb(events,datas);
                            Data(datas);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });

    }

    private ArrayList<model_expense> SavetoDb(List<model_expense> lst,ArrayList<model_expense> data) {
        SQLBase db = new SQLBase(this);
        if (lst.size() > 0) {
            for (int i = 0; i < lst.size() - 1; i++) {
                if(lst.get(i).mob_createdon!=null) {
                    String[] twoStringArray = lst.get(i).mob_createdon.split(", ", 2); //the main line
                    String Date = twoStringArray[0];
                    String Time = twoStringArray[1];
                    //checking if expense alredy exist
//                    if (db.ISEXPENSE_APPRO_ALREDY_EXIST(Date, Time)) {
                        if (lst.get(i).appramt != null) {
                            if(data.size()>0) {
                                for (int j = 0; j <= data.size() - 1; j++) {

                                    if (data.get(j)._code.equals(Date) && data.get(j)._exp_id.equals(Time)) {
                                        data.get(j)._appr_amt = (lst.get(i).appramt);
                                    }
                                }
                            }
//                            db.EXPENSE_UPDATE_APPR(lst.get(i).appramt, Date, Time);
//                        }
                    }
                }
            }
        }
        return data;

    }



    private ArrayList<model_expense> populateList(String Status){

        if(SelectedDate==null){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            SelectedDate = df.format(c.getTime());
        }
        SQLBase db = new SQLBase(this);
        ArrayList<model_expense> data = new ArrayList<model_expense>();
        if(Status.equals("Task_Selected")){
            List<HashMap<String, String>> d = db.Get_CombinedTasksOnly(SelectedDate);
            if (d.size() > 0) {
                for (int i = 0; i <= d.size() - 1; i++) {
                    data.add(new model_expense(d.get(i).get(Tables.tblexpense.Exp_ID), d.get(i).get(Tables.tblexpense.Expense_Name), d.get(i).get(Tables.tblexpense.Expense_Disc), d.get(i).get(Tables.tblexpense.Expense_Value), d.get(i).get(Tables.tblexpense.Expense_Code), d.get(i).get(Tables.tblexpense.Expense_Is_Active), d.get(i).get(Tables.tblexpense.Expense_Server), d.get(i).get(Tables.tblexpense.Approval_Amount)));
                }
            }
        }else if (Status.equals("Stop_Selected")){
            List<HashMap<String, String>> d = db.Get_CombinedStop_Only(SelectedDate);
            if (d.size() > 0) {
                for (int i = 0; i <= d.size() - 1; i++) {
                    data.add(new model_expense(d.get(i).get(Tables.tblexpense.Exp_ID), d.get(i).get(Tables.tblexpense.Expense_Name), d.get(i).get(Tables.tblexpense.Expense_Disc), d.get(i).get(Tables.tblexpense.Expense_Value), d.get(i).get(Tables.tblexpense.Expense_Code), d.get(i).get(Tables.tblexpense.Expense_Is_Active), d.get(i).get(Tables.tblexpense.Expense_Server), d.get(i).get(Tables.tblexpense.Approval_Amount)));
                }
            }
        }else if (Status.equals("Both_Selected")) {
            List<HashMap<String, String>> d = db.Get_CombinedExpense(SelectedDate);
            if (d.size() > 0) {
                for (int i = 0; i <= d.size() - 1; i++) {
                    data.add(new model_expense(d.get(i).get(Tables.tblexpense.Exp_ID), d.get(i).get(Tables.tblexpense.Expense_Name), d.get(i).get(Tables.tblexpense.Expense_Disc), d.get(i).get(Tables.tblexpense.Expense_Value), d.get(i).get(Tables.tblexpense.Expense_Code), d.get(i).get(Tables.tblexpense.Expense_Is_Active), d.get(i).get(Tables.tblexpense.Expense_Server), d.get(i).get(Tables.tblexpense.Approval_Amount)));
                }
            }
        }else if (Status.equals("None_Selected")) {
            List<HashMap<String, String>> d = db.Get_Expenses_ALL(SelectedDate);
            if (d.size() > 0) {
                for (int i = 0; i <= d.size() - 1; i++) {
                    data.add(new model_expense(d.get(i).get(Tables.tblexpense.Exp_ID), d.get(i).get(Tables.tblexpense.Expense_Name), d.get(i).get(Tables.tblexpense.Expense_Disc), d.get(i).get(Tables.tblexpense.Expense_Value), d.get(i).get(Tables.tblexpense.Expense_Code), d.get(i).get(Tables.tblexpense.Expense_Is_Active), d.get(i).get(Tables.tblexpense.Expense_Server), d.get(i).get(Tables.tblexpense.Approval_Amount)));
                }
            }
        }
        return data;
    }

//    private void SavetoDb(List<model_completed> lst) {
//        SQLBase db = new SQLBase(this);
//        if (lst.size() > 0) {
//            for (int i = 0; i <= lst.size() - 1; i++) {
//                //checking if leave alredy exist otherwise update status
//                if (!db.ISLeave_ALREDY_EXIST(lst.get(i).frmdt)){
//                    db.ADDLeave(new modal_leave(lst.get(i).frmdt, lst.get(i).todt, lst.get(i).restype, lst.get(i).tagnm, currentDateTimeString, "2"));
//                }else {
//                    db. Leave_UPDATE_Status(lst.get(i).frmdt,lst.get(i).frmdt);
//                }
//            }
//        }
//
//    }
//
//    public void SendOfflineTagstoServer() {
//        SQLBase db = new SQLBase(this);
//        final List<HashMap<String,String>> d = db. Get_Tags_Offline();
//        if(d.size()>0) {
//            for (int i = 0; i <= d.size() - 1; i++) {
//                final int pos=i;
//                JsonObject json = new JsonObject();
//                json.addProperty("tagnm", d.get(i).get(Tables.tbltags.Tag_Title));
//                json.addProperty("remark1", d.get(i).get(Tables.tbltags.Tag_remark_1));
//                json.addProperty("remark2", d.get(i).get(Tables.tbltags.Tag_remark_2));
//                json.addProperty("remark3", d.get(i).get(Tables.tbltags.Tag_remark_3));
//                json.addProperty("cuid", d.get(i).get(Tables.tbltags.Tag_Creat_On));
//                json.addProperty("enttid", Global.loginusr.getEnttid()+"");
//                json.addProperty("tagtype","m");
//                Ion.with(this)
//                        .load(Global.urls.saveTagInfo.value)
//                        .setJsonObjectBody(json)
//                        .asJsonObject()
//                        .setCallback(new FutureCallback<JsonObject>() {
//                            @Override
//                            public void onCompleted(Exception e, JsonObject result) {
//                                // do stuff with the result or error
//                                try {
//                                    SQLBase db = new SQLBase(.this);
//                                    db. TAG_UPDATE(d.get(pos).get(Tables.tbltags.Tag_Title),"0");
//
//                                } catch (Exception ea) {
//                                    ea.printStackTrace();
//                                }
//
//
//                            }
//                        });
//            }
//        }
//
////        return data;
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_expense, menu);
//        return true;

//        // Restore the check state e.g. if the device has been rotated.
//         MenuItem logItem = menu.findItem(R.id.menu_action_logging);
//        setActionBarCheckboxChecked(logItem,true);
//
//
//        CheckBox cb = (CheckBox)logItem.getActionView().findViewById(R.id.action_item_checkbox);
//        if (cb != null)
//        {
//            // Set the text to match the item.
//            cb.setText(logItem.getTitle());
//            // Add the onClickListener because the CheckBox doesn't automatically trigger onOptionsItemSelected.
//            cb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onOptionsItemSelected(logItem);
//                }
//            });
//        }
//    }


    //action bar menu button click
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
//        switch (item.getItemId()) {
//            //When home is clicked
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            case R.id.menu_driver_info_view_add:
//                Intent intent=new Intent(this,expense.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.menu_action_logging:
//                // Toggle the checkbox.
////                setActionBarCheckboxChecked(item, !item.isChecked());
////
////                // Do whatever you want to do when the checkbox is changed.
////                 item.isChecked();
////                return true;
//
//            default:
//        }
//        return super.onOptionsItemSelected(item);
//    }

}