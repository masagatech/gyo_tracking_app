package com.goyo.traveltracker.forms;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_tag_db;
import com.goyo.traveltracker.model.model_task;
import com.goyo.traveltracker.utils.common;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pchmn.materialchips.ChipsInput;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.reactivex.functions.Consumer;
import me.shaohui.advancedluban.Luban;

import static com.goyo.traveltracker.Service.NetworkStateReceiver.IsMobailConnected;
import static com.goyo.traveltracker.Service.RiderStatus.Rider_Lat;
import static com.goyo.traveltracker.Service.RiderStatus.Rider_Long;
import static com.goyo.traveltracker.forms.dashboard.TripId;
import static com.goyo.traveltracker.gloabls.Global.urls.mobileupload;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewStops extends AAH_FabulousFragment {

    private ImageView map;
    private EditText remark, remark_title, InTime, OutTime;
    private LocationManager locationManager2;
    public Criteria criteria;
    private Location location;
    public String bestProvider, currentDateTimeString, Empl_Id,_intime,_outtime,_stop_id;
    String Body, Title, Lat, Lon, time;
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
    String Selected_Exp, Selected_Value, Selected_Disc, Selected_EXP_Type;
    private ProgressDialog loader;
    private ImageView Reminder;


    public static NewStops newInstance() {
        NewStops f = new NewStops();
        return f;
        // Required empty public constructor
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {

//        settingsrequest();
        View contentView = View.inflate(getContext(), R.layout.activity_task__add, null);
        LinearLayout rl_content = (LinearLayout) contentView.findViewById(R.id.rl_content);


        SQLBase db = new SQLBase(getActivity());

        List<model_tag_db> data = new ArrayList<model_tag_db>();
        List<HashMap<String, String>> d = db.Get_Tags();
        if (d.size() > 0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_tag_db(d.get(i).get(Tables.tbltags.Tag_Id), d.get(i).get(Tables.tbltags.Tag_Title), d.get(i).get(Tables.tbltags.Tag_remark_1), d.get(i).get(Tables.tbltags.Tag_remark_2), d.get(i).get(Tables.tbltags.Tag_remark_3), d.get(i).get(Tables.tbltags.Tag_Creat_On), d.get(i).get(Tables.tbltags.Is_Server_Send)));
            }
        }

        chipsInput = (ChipsInput) contentView.findViewById(R.id.chip);
        chipsInput.setFilterableList(data);

        //map
        map = (ImageView) contentView.findViewById(R.id.map);
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
//
//        mapFragment.getMapAsync(getActivity());

        //getting current location
//        GetLocation();
//        PhotoDecodeRunnable photoDecodeRunnable=new PhotoDecodeRunnable();
//        photoDecodeRunnable.run();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////        setting map based on location
//
//            }
//        }, 2000);



        Reminder = (ImageView) contentView.findViewById(R.id.reminder);
        remark = (EditText) contentView.findViewById(R.id.Task_Body);
        remark_title = (EditText) contentView.findViewById(R.id.Task_Title);

        //image and expense
        ChooseImage = (FrameLayout) contentView.findViewById(R.id.ChooseImage);
        Expense = (FrameLayout) contentView.findViewById(R.id.Expense);
        Count_Expense = (ImageView) contentView.findViewById(R.id.Count_Expense);
        Count_Image = (TextView) contentView.findViewById(R.id.Count_Image);
        InTime = (EditText) contentView.findViewById(R.id.inTime);
        OutTime = (EditText) contentView.findViewById(R.id.outTime);


        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
       String times = dateFormat.format(new Date()).toString();
        InTime.setText(times);
        OutTime.setText(times);

        InTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                In_time = Calendar.getInstance();
                int hour = In_time.get(Calendar.HOUR);
                int minute = In_time.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                pickImages();
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


        Btn_Add_Task = (Button) contentView.findViewById(R.id.send_form);
        Btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!IsConnected){

                currentDateTimeString = DateFormat.getDateInstance().format(new Date());
                Empl_Id = String.valueOf(Global.loginusr.getDriverid());
                Title = remark_title.getText().toString();
                Body = remark.getText().toString();
                Lat = String.valueOf(Rider_Lat);
                Lon = String.valueOf(Rider_Long);


                //time
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                time = dateFormat.format(new Date()).toString();

                //date
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                String TimenDate = formattedDate + ", " + time;

                _intime=formattedDate+" "+InTime.getText().toString();
                _outtime=formattedDate+" "+OutTime.getText().toString();

                _stop_id= common.GetUniqueID("s");


                if (Title.equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Info!", Toast.LENGTH_SHORT).show();
                } else {

                    String zipPath = getActivity().getApplicationInfo().dataDir + "/" + Global.loginusr.getEnttid() + "_" + Empl_Id + "_" + Title + "_" + TimenDate + ".zip";

                    //getting selected tags
                    List<model_tag_db> contactsSelected = (List<model_tag_db>) chipsInput.getSelectedChipList();
                    List<String> Tags = new ArrayList<String>();
                    if (contactsSelected.size() > 0) {
                        for (int i = 0; i <= contactsSelected.size() - 1; i++) {
                            Tags.add(contactsSelected.get(i).getLabel());
                        }
                    }


                    //checking if stops alrerady exists
                    SQLBase db2 = new SQLBase(getActivity());
                    if (!db2.ISTASK_ALREDY_EXIST(Title)) {


                        if (arrFilePaths[0] == null) {
                            zipPath = getActivity().getApplicationInfo().dataDir + "/default_image.PNG";
                            //sending info
                            SendToServer(Empl_Id, Title, Body, Lat, Lon, TimenDate, Tags, zipPath,_intime,_outtime,_stop_id);
                        } else {
                            loader = new ProgressDialog(getActivity());
                            loader.setCancelable(false);
                            loader.setMessage("Uploading..");
                            loader.show();
                            //creating zip file of all selected images
                            zip(arrFilePaths, zipPath);
                            //sending info
                            SendToServer(Empl_Id, Title, Body, Lat, Lon, TimenDate, Tags, zipPath,_intime,_outtime,_stop_id);
                        }

                    }

                    //save to db


                    SQLBase db = new SQLBase(getActivity());
                    Gson gson = new Gson();
                    String TagString = gson.toJson(Tags);

                    //checking if there is internet
                    if (IsMobailConnected) {
                        if (!db.ISTASK_ALREDY_EXIST(Title)) {
                            db.OFFLINE_TASK_ADDTASK(new model_task(Title, Body, Lat, Lon, TagString, formattedDate, "0", time, zipPath, Selected_Exp, Selected_EXP_Type, Selected_Value, Selected_Disc,InTime.getText().toString(),OutTime.getText().toString(),_stop_id));
                        } else {
                            Toast.makeText(getActivity(), "This Stops Already Exist!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!db.ISTASK_ALREDY_EXIST(Title)) {
                            db.OFFLINE_TASK_ADDTASK(new model_task(Title, Body, Lat, Lon, TagString, formattedDate, "1", time, zipPath, Selected_Exp, Selected_EXP_Type, Selected_Value, Selected_Disc,InTime.getText().toString(),OutTime.getText().toString(),_stop_id));
                            closeFilter("closed");
                            Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "This Stops Already Exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }
        });

        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setViewgroupStatic(Btn_Add_Task); // optional; layout to stick at bottom on slide
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

//    public void to_reminder(View view)
//    {
//        Intent intent=new Intent(this,Notification_morning.class);
//        AlarmManager manager=(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
//        PendingIntent pendingIntent=PendingIntent.getService(this,
//                0,intent, 0);
//        Calendar cal=Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, timepicker.getCurrentHour());
//        cal.set(Calendar.MINUTE,timepicker.getCurrentMinute());
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
//
//    }

//    public class PhotoDecodeRunnable implements Runnable {
//        @Override
//        public void run() {
//
//            setMap();
//        }
//    }

    public void ExpenseClicked(View view) {

        View alertLayout = LayoutInflater.from(getActivity()).inflate(R.layout.popup_exp, null);
        Expense_Type = (Spinner) alertLayout.findViewById(R.id.expense_name);

        //getting spinner data if any
        GetfromDb();


        final EditText Expense_Value = (EditText) alertLayout.findViewById(R.id.exp_value);
        final EditText Expense_Disc = (EditText) alertLayout.findViewById(R.id.exp_disc);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
        SQLBase db = new SQLBase(getActivity());

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

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Expense);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Expense_Type.setAdapter(dataAdapter);
        }
    }


    private void SendToServer(String Empl_Id, String Title, String Body, String Lat, String Lon, String currentDateTimeString, List<String> Tags, String ZipFile,String In_time,String Out_time,String _stop_id) {

        String tag = Joiner.on(",").join(Tags);

        Ion.with(getActivity())
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
                            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                            closeFilter("closed");
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


// private void SendToServer(String Empl_Id,String Title,String Body,String Lat,String Lon,String currentDateTimeString,List<String> Tags) {
//
//    JsonObject json = new JsonObject();
//    json.addProperty("stpnm", Title);
//    json.addProperty("stpdesc", Body);
//    json.addProperty("lat", Lat);
//    json.addProperty("lng", Lon);
//    json.addProperty("trpid", TripId);
//    json.addProperty("uid", Empl_Id);
//    json.addProperty("cuid", currentDateTimeString);
//    json.addProperty("enttid", Global.loginusr.getEnttid()+"");
//    json.addProperty("tag","{'" + tag + "'}");
//
//
//    Ion.with(this)
//            .load(Global.urls.saveTripStops.value)
//            .setJsonObjectBody(json)
//            .asJsonObject()
//            .setCallback(new FutureCallback<JsonObject>() {
//                @Override
//                public void onCompleted(Exception e, JsonObject result) {
//                    // do stuff with the result or error
//                    try {
//                        JsonObject o= result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funsave_tripstops").getAsJsonObject();
//                        Toast.makeText(getActivity(), o.get("msg").toString(), Toast.LENGTH_SHORT).show();
//
//
//                    } catch (Exception ea) {
//                        ea.printStackTrace();
//                    }
//
//
//                }
//            });
//}

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
            loader = new ProgressDialog(getActivity());
            loader.setCancelable(false);
            loader.setMessage("Compressing..");
            loader.show();
            //getting selected images
            for (int i = 0; i < images.size(); i++) {
                CompressedImage.add(new File(images.get(i).path));
            }


            //compress selected image
            Luban.compress(getActivity(), CompressedImage)
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
            Toast.makeText(getActivity(), "There was an Error", Toast.LENGTH_SHORT).show();

            //User canceled the pick activity
            Count_Image.setVisibility(View.GONE);
        }
    }


    private void pickImages() {
        //You can change many settings in builder like limit , Pick mode and colors
        new Picker.Builder(getActivity(), new MyPickListener(), R.style.MIP_theme)
                .setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
                .setLimit(4)
                .build()
                .startActivity();
    }

    private void setMap() {
        final String STATIC_MAP_API_ENDPOINT = "http://maps.google.com/maps/api/staticmap?center=" + Rider_Lat + "," + Rider_Long + "&zoom=13&size=640x400&markers=color:red%7C" + Rider_Lat + "," + Rider_Long;

        AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bmp = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT);

                InputStream in = null;
                try {
                    in = httpclient.execute(request).getEntity().getContent();
                    bmp = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            protected void onPostExecute(Bitmap bmp) {
                if (bmp != null) {
                    map.setImageBitmap(bmp);
                }
            }
        };
        setImageFromUrl.execute();
    }

//    private void GetLocation() {
//
//        locationManager2 = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        if(IsMobailConnected) {
//          location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        }else {
//           location = locationManager2.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        }
//        criteria = new Criteria();
//        bestProvider = String.valueOf(locationManager2.getBestProvider(criteria, true)).toString();
//        if ((location == null)) {
//            locationManager2.requestLocationUpdates(bestProvider, 1000, 0, this);
//        }
//
//        if (location != null) {
//            lat = location.getLatitude();
//            lon = location.getLongitude();
//
//            locationManager2.removeUpdates(this);
//        }
//    }

//    public void settingsrequest() {
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true); //this is the key ingredient
//
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                final LocationSettingsStates state = result.getLocationSettingsStates();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can initialize location
//                        // requests here.
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the user
//                        // a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        break;
//                }
//            }
//        });
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            // Check for the integer request code originally supplied to startResolutionForResult().
//            case REQUEST_CHECK_SETTINGS:
//                switch (resultCode) {
//                    case RESULT_OK:
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        settingsrequest();//keep asking if imp or do whatever
//                        break;
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }

}
