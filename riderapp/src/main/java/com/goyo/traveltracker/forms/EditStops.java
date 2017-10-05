package com.goyo.traveltracker.forms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_tag_db;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pchmn.materialchips.ChipsInput;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.reactivex.functions.Consumer;
import me.shaohui.advancedluban.Luban;

import static com.goyo.traveltracker.forms.dashboard.TripId;
import static com.goyo.traveltracker.gloabls.Global.urls.mobileupload;

public class EditStops extends AppCompatActivity {

    private ImageView map;
    private EditText remark, remark_title, InTime, OutTime;
    private LocationManager locationManager2;
    public Criteria criteria;
    private Location location;
    public String bestProvider, currentDateTimeString, Empl_Id,_intime,_outtime,_stop_id,is_server,_exp_id,_exp_disc,_exp_type,_exp_value_,_tags,_image_paths;
    String Body, Title, Lat, Lon, time,Datess;
    Calendar In_time,Out_ime;
    private static final int REQUEST_CODE_PICKER = 20;
    private static final int RC_CAMERA = 3000;
    private Button Btn_Add_Task;
    private ImageView Count_Expense;
    private TextView Count_Image;
    private FrameLayout ChooseImage, Expense;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private ChipsInput chipsInput;
    ArrayList<String> Image = new ArrayList<>();
    private String[] arrFilePaths = new String[4];
    ArrayList<File> CompressedImage = new ArrayList<>();
    List<String> Exp;
    List<String> Exp_Id;
    private Spinner Expense_Type;
    String Selected_Exp="", Selected_Value="", Selected_Disc="", Selected_EXP_Type="";
    private ProgressDialog loader;
    private ImageView Reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stops);



        //getting Datas
        Intent intent = getIntent();
        Datess=  intent.getExtras().getString("date");
        time=  intent.getExtras().getString("time");
        Title=  intent.getExtras().getString("title");
        Body=  intent.getExtras().getString("body");
        _intime=  intent.getExtras().getString("InTime");
        _outtime=  intent.getExtras().getString("OutTime");
        _stop_id=  intent.getExtras().getString("unique_id");
        is_server=  intent.getExtras().getString("is_server");
        _exp_id=  intent.getExtras().getString("Exp_id");
        _exp_disc=  intent.getExtras().getString("Exp_disc");
        _exp_type=  intent.getExtras().getString("Exp_type");
        _exp_value_=  intent.getExtras().getString("Exp_value");
        _tags=  intent.getExtras().getString("Tags");
        _image_paths=  intent.getExtras().getString("image paths");
        Lat=  intent.getExtras().getString("lat");
        Lon=  intent.getExtras().getString("lon");


        SQLBase db = new SQLBase(EditStops.this);

        final List<model_tag_db> data = new ArrayList<model_tag_db>();
        List<HashMap<String, String>> d = db.Get_Tags();
        if (d.size() > 0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_tag_db(d.get(i).get(Tables.tbltags.Tag_Id), d.get(i).get(Tables.tbltags.Tag_Title), d.get(i).get(Tables.tbltags.Tag_remark_1), d.get(i).get(Tables.tbltags.Tag_remark_2), d.get(i).get(Tables.tbltags.Tag_remark_3), d.get(i).get(Tables.tbltags.Tag_Creat_On), d.get(i).get(Tables.tbltags.Is_Server_Send)));
            }
        }

        chipsInput = (ChipsInput) findViewById(R.id.chip);
        chipsInput.setFilterableList(data);

        //map
        map = (ImageView) findViewById(R.id.map);




        Reminder = (ImageView) findViewById(R.id.reminder);
        remark = (EditText) findViewById(R.id.Task_Body);
        remark_title = (EditText) findViewById(R.id.Task_Title);

        //image and expense
        ChooseImage = (FrameLayout) findViewById(R.id.ChooseImage);
        Expense = (FrameLayout) findViewById(R.id.Expense);
        Count_Expense = (ImageView) findViewById(R.id.Count_Expense);
        Count_Image = (TextView) findViewById(R.id.Count_Image);
        InTime = (EditText) findViewById(R.id.inTime);
        OutTime = (EditText) findViewById(R.id.outTime);


        InTime.setText(_intime);
        OutTime.setText(_outtime);

        remark_title.setText(Title);
        remark.setText(Body);




        InTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                In_time = Calendar.getInstance();
                int hour = In_time.get(Calendar.HOUR);
                int minute = In_time.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditStops.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        InTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
//                        InTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select InTime");
                mTimePicker.show();

            }
        });

        OutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Out_ime = Calendar.getInstance();
                int hour = Out_ime.get(Calendar.HOUR);
                int minute = Out_ime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditStops.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        OutTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
//                        OutTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select OutTime");
                mTimePicker.show();
            }
        });

        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pickImages();
            }
        });


        Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseClicked(v);
            }
        });


        Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Btn_Add_Task = (Button) findViewById(R.id.send_form);
        Btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!IsConnected){

                currentDateTimeString = Datess+" "+time;
                Empl_Id = String.valueOf(Global.loginusr.getDriverid());
                Title = remark_title.getText().toString();
                Body = remark.getText().toString();




                if (Title.equals("")) {
                    Toast.makeText(EditStops.this, "Please Enter Info!", Toast.LENGTH_SHORT).show();
                } else {


                    //getting selected tags
                    List<model_tag_db> contactsSelected = (List<model_tag_db>) chipsInput.getSelectedChipList();
                    List<String> Tags = new ArrayList<String>();
                    if (contactsSelected.size() > 0) {
                        for (int i = 0; i <= contactsSelected.size() - 1; i++) {
                            Tags.add(contactsSelected.get(i).getLabel());
                        }
                    }

                    SQLBase db = new SQLBase(EditStops.this);
                    Gson gson = new Gson();
                    String TagString = gson.toJson(Tags);
                    if(TagString.equals("[]")){
                        TagString=_tags;
                    }
                    if(Selected_Exp.equals("")){
                        Selected_Exp=_exp_id;
                    }
                    if(Selected_EXP_Type.equals("")){
                        Selected_EXP_Type=_exp_type;
                    }
                    if(Selected_Value.equals("")){
                        Selected_Value=_exp_value_;
                    }
                    if(Selected_Disc.equals("")){
                        Selected_Disc=_exp_disc;
                    }

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());

                    if(InTime.getText().equals(_intime)){
                        _intime=data+" "+InTime.getText().toString();
                    }else {
                        _intime = formattedDate + " " + InTime.getText().toString();
                    }
                    if(OutTime.getText().equals(_outtime)){
                        _outtime=data+" "+OutTime.getText().toString();
                    }else {
                        _outtime=formattedDate+" "+OutTime.getText().toString();
                    }



                    db.EDIT_STOPS(Title,Body,Lat,Lon,TagString,Datess,is_server,time,_image_paths,Selected_Exp,Selected_EXP_Type,Selected_Value,Selected_Disc,InTime.getText().toString(),OutTime.getText().toString(),_stop_id);

                            loader = new ProgressDialog(EditStops.this);
                            loader.setCancelable(false);
                            loader.setMessage("Uploading..");
                            loader.show();
                            //creating zip file of all selected images
//                            zip(arrFilePaths, zipPath);
                            //sending info
                            SendToServer(Empl_Id, Title, Body, Lat, Lon, currentDateTimeString, Tags, _image_paths,_intime,_outtime,_stop_id);


                    //save to db




                }


            }
        });

    }

    public void ExpenseClicked(View view) {

        View alertLayout = LayoutInflater.from(EditStops.this).inflate(R.layout.popup_exp, null);
        Expense_Type = (Spinner) alertLayout.findViewById(R.id.expense_name);

        //getting spinner data if any
        GetfromDb();


        final EditText Expense_Value = (EditText) alertLayout.findViewById(R.id.exp_value);
        final EditText Expense_Disc = (EditText) alertLayout.findViewById(R.id.exp_disc);

        AlertDialog.Builder alert = new AlertDialog.Builder(EditStops.this);
        alert.setTitle("Expense");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //icon
                Count_Expense.setVisibility(View.GONE);
            }
        });

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int Pos = Expense_Type.getSelectedItemPosition();
                if (Exp_Id.size() > 0) {
                    Selected_Exp = Exp_Id.get(Pos);
                }
                Selected_EXP_Type = Expense_Type.getSelectedItem().toString();
                Selected_Value = Expense_Value.getText().toString();
                Selected_Disc = Expense_Disc.getText().toString();

                //icon
                Count_Expense.setVisibility(View.VISIBLE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    private void GetfromDb() {
        //getting expense name from db and setting in spinner
        SQLBase db = new SQLBase(EditStops.this);

        Exp = new ArrayList<String>();
        Exp_Id = new ArrayList<String>();
        List<HashMap<String, String>> d = db.Get_Expenses_Display();
        if (d.size() > 0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                Exp.add(d.get(i).get(Tables.tblexpense.Expense_Name));
                Exp_Id.add(d.get(i).get(Tables.tblexpense.Exp_ID));
            }
            bindCurrentTrips3(Exp);
        }
    }


    private void bindCurrentTrips3(List<String> Expense) {
        if (Expense.size() > 0) {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditStops.this, android.R.layout.simple_spinner_item, Expense);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Expense_Type.setAdapter(dataAdapter);
        }
    }


    private void SendToServer(String Empl_Id, String Title, String Body, String Lat, String Lon, String currentDateTimeString, List<String> Tags, String ZipFile,String In_time,String Out_time,String _stop_id) {


        String tag = Joiner.on(",").join(Tags);
        if(tag.equals("")){
            tag=_tags;
        }

        Ion.with(EditStops.this)
                .load(mobileupload.value)
                .setMultipartParameter("enttid", Global.loginusr.getEnttid() + "")
                .setMultipartParameter("uid", Empl_Id)
                .setMultipartParameter("stpnm", Title)
                .setMultipartParameter("stpdesc", Body)
                .setMultipartParameter("intime", In_time)
                .setMultipartParameter("outtime", Out_time)
                .setMultipartParameter("lat", Lat)
                .setMultipartParameter("lng", Lon)
                .setMultipartParameter("trpid", TripId)
                .setMultipartParameter("cuid", Global.loginusr.getUcode() + "")
                .setMultipartParameter("mob_createdon", currentDateTimeString)
                .setMultipartParameter("tag", tag.replace('[','{').replace(']','}'))
                .setMultipartParameter("path", "")
                .setMultipartParameter("expid", Selected_Exp)
                .setMultipartParameter("expval", Selected_Value)
                .setMultipartParameter("mobstpid", _stop_id)
                .setMultipartParameter("stpid", "0")
                .setMultipartParameter("expdesc", Selected_Disc)
                .setMultipartFile("uploadimg", new File(ZipFile))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            Toast.makeText(EditStops.this, "Success!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(EditStops.this,complated_order.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
//                                ((dashboard)getActivity()).refreshMyData();
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        if (loader != null) {
                            loader.hide();
                        }

                    }
                });
    }


    public void zip(String[] _files, String zipFileName) {
        int BUFFER = 80000;
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _files.length - 1; i++) {
                if (_files[i] == null) break;
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class MyPickListener implements Picker.PickListener {

        @Override
        public void onPickedSuccessfully(final ArrayList<ImageEntry> images) {
            loader = new ProgressDialog(EditStops.this);
            loader.setCancelable(false);
            loader.setMessage("Compressing..");
            loader.show();
            //getting selected images
            for (int i = 0; i < images.size(); i++) {
                CompressedImage.add(new File(images.get(i).path));
            }


            //compress selected image
            Luban.compress(EditStops.this, CompressedImage)
                    .putGear(Luban.CUSTOM_GEAR)
                    .asListObservable()
                    .subscribe(new Consumer<List<File>>() {
                        @Override
                        public void accept(List<File> files) throws Exception {
                            int size = files.size();
                            while (size-- > 0) {
                                arrFilePaths[size] = files.get(size).toString();
                            }
                            loader.hide();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });

            int count = CompressedImage.size();
            //showing custem image icon
            if (count > 0) {
                Count_Image.setVisibility(View.VISIBLE);
                Count_Image.setText(count + "");
            }


        }

        @Override
        public void onCancel() {
            Toast.makeText(EditStops.this, "There was an Error", Toast.LENGTH_SHORT).show();

            //User canceled the pick activity
            Count_Image.setVisibility(View.GONE);
        }
    }


    private void pickImages() {
        //You can change many settings in builder like limit , Pick mode and colors
        new Picker.Builder(EditStops.this, new MyPickListener(), R.style.MIP_theme)
                .setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
                .setLimit(4)
                .build()
                .startActivity();
    }

}
